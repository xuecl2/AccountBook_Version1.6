package jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * ���˱����ݺ���Ӧ�����ݿ������װ���������
 */
public class AccountJDBC implements Serializable{	
	private int currentMonth;
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> recordList = new ArrayList<>();	
	private String dbPath = "jdbc:mysql://127.0.0.1:3306/accoutbook?characterEncoding = utf -8";
	transient Connection c;
	
	public AccountJDBC() {
		this("jdbc:mysql://127.0.0.1:3306/accoutbook?characterEncoding = utf -8");
	}
	
	public AccountJDBC(String dbPath) {
		this.dbPath = dbPath;
		currentMonth = new GregorianCalendar().get(Calendar.MONTH)+1;
		System.out.println("AccountJDBC Constructor: currentMonth = " + currentMonth);
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			System.out.println("���ݿ��������سɹ���");
			c = DriverManager
					.getConnection(dbPath,"root","1234");
			System.out.println("���ݿ����ӳɹ�");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Ŀǰֻ֧��100�����ڵķ���
	 * ��Category���飬�����õ��˹̶����ֶ�����category
	 * ֻ���ҵ��µ����ݣ������õ��˹̶����ֶ�����date 
	 */
	public Object[][] groupByCategory(){
		String sql = "select category , count(*) as count from accout where Month(date) = " 
				+ currentMonth +" group by category" ;
		try(PreparedStatement ps = c.prepareStatement(sql);) {	
			int size = 0;
			Object[][] object = new Object[100][2];
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				object[size][0] = rs.getObject(1);
				object[size][1] = rs.getObject(2);						
				size++;
			}
			object = Arrays.copyOf(object, size);
			return object;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	/*
	 * Ŀǰֻ֧��100�����ڵķ����1w������
	 * ��date���������õ��˹̶����ֶ�����date 
	 * ���ﻹ���˹̶��ֶ��� category 
	 * ���˹̶��ı����accout
	 */
	public Object[][] queryByCategory(String cg) {
		int size = 0;	
		String sql = "select * from accout where category = ? and Month(date) = "+ currentMonth+ " order by date";
		try(PreparedStatement ps = c.prepareStatement(sql);) {			
			ps.setString(1,cg);
			ResultSet rs = ps.executeQuery();
			Object object[][] = new Object[10000][100];
			while (rs.next()) {
				for(int i = 1 ; i<=JDBCUtil.FIEDLE_NAME.length; i++) {					
					/*if(fieldName[i]!="date")
						object[size][i-1] = rs.getObject(i);
					else
						object[size][i-1] = new JXDatePicker((Date)rs.getObject(i));*/
					object[size][i-1] = rs.getObject(i);
				}				
				size++;
			}
			object = Arrays.copyOf(object, size);
			return object;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;		
	}
	

	/*
	 * �������˹̶��ֶ��� id
	 * ���˹̶��ı��� accout
	 */
	public void delete(int id) {
		String sql = "delete from accout where id = ?";
		try(PreparedStatement ps = c.prepareStatement(sql);) {			
			ps.setInt(1,id);
			ps.execute();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(Object... values) {
		if (values.length!=JDBCUtil.FIEDLE_NAME.length-1) {
			System.out.println("����ʧ�ܣ�����Ĳ���������ƥ�䣡");
			return;
		}
		String sql = "insert into accout values(null,?,?,?,?);";		
		try(PreparedStatement ps = c.prepareStatement(sql)) {
			for(int i = 0; i<values.length; i++) {
				ps.setObject(i+1, values[i]);
			}
			System.out.println("AccoutJDBC insert: sql=");
			System.out.println(values[0]+" "+
					values[1]+" "+
					values[2]+" "+
					values[3]
					);
			ps.execute();
			System.out.println("AccoutJDBC insert: ����ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * ����id�������ݣ��������˹̶��ֶ��� id
	 * ����Ĵ���Ҫ��idΪ��һ������������˳���FIEDLE_NAME��˳��һ��
	 */
	public void update(	Object... values) {
		if (values.length!=JDBCUtil.FIEDLE_NAME.length) {
			System.out.println("����ʧ�ܣ�����Ĳ���������ƥ�䣡");
			return;
		}
		String sql = "update accout set "+ JDBCUtil.FIEDLE_NAME[1] +" = " + values[1]
				+ ", "+ JDBCUtil.FIEDLE_NAME[2] +" = " + "'"+values[2]+"'"
				+ ", "+ JDBCUtil.FIEDLE_NAME[3] +" = " + "'"+values[3]+"'"
				+ ", "+ JDBCUtil.FIEDLE_NAME[4] +" = " + "'"+values[4]+"'"
				+"  where id = ?;";
		System.out.println(sql);
		try(PreparedStatement ps = c.prepareStatement(sql)) {			
			ps.setObject(1, values[0]);
			ps.execute();
			System.out.println("AccoutJDBC update: ���³ɹ���");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void backUp(String backUpPath) {
		selectAll();
		System.out.println("AccoutJDBC backUP: the recordlist is:");
		System.out.println(recordList);
		try(FileOutputStream fos = new FileOutputStream(backUpPath,false);
				ObjectOutputStream oos = new ObjectOutputStream(fos);){	
			oos.writeObject(this);			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void restore2DB(File restoreFile) {
		AccountJDBC book = restore2AccountJDBC(restoreFile);
		List<Map<String, Object>> list = book.getRecordList();
		Statement s = null;
		try {
			s = c.createStatement();
			c.setAutoCommit(false);
			s.execute("delete from accout");				
			for(int i = 0 ; i<list.size() ; i++) {
				String sql = "insert into accout values(" +
						 list.get(i).get(JDBCUtil.FIEDLE_NAME[0]) +"," +
						 list.get(i).get(JDBCUtil.FIEDLE_NAME[1]) +"," +"'" +
						 list.get(i).get(JDBCUtil.FIEDLE_NAME[2]) + "'" +"," + "'" +
						 list.get(i).get(JDBCUtil.FIEDLE_NAME[3])+ "'" + "," + "'" +
						 list.get(i).get(JDBCUtil.FIEDLE_NAME[4])+ "'"+")";
				
				System.out.println("AccoutJDBC restore2DB: the inser sql is: ");
				System.out.println(sql);
				s.addBatch(sql);						
			}
			s.executeBatch();
			c.commit();
			c.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(s!=null) {
				try {
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}		
	}
	
	public void selectAll() {
		recordList.clear();
		String sql = "select * from accout;";
		try(Statement ps = c.createStatement();) {			
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Map<String, Object> recordSingle = new HashMap<>();
				for(int i = 0 ; i<JDBCUtil.FIEDLE_NAME.length ; i++) {
					recordSingle.put(JDBCUtil.FIEDLE_NAME[i], rs.getObject(JDBCUtil.FIEDLE_NAME[i]));
				}				
				recordList.add(recordSingle);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AccountJDBC restore2AccountJDBC(File restoreFile) {
		try(FileInputStream fis = new FileInputStream(restoreFile);
				ObjectInputStream ois = new ObjectInputStream(fis);) 
		{			
			return (AccountJDBC)ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int budgetConsumed() {		
		double budget = budget();
		double expend = totalExpend();
		if(expend!=0) {
			return (int)(expend/budget*100);
		}else {
			return 0;
		}	
	}
	
	/*
	 * �õ��˹̶����ֶ����ƣ�cost
	 * �õ��˹̶����ֶ����ƣ�date
	 */
	public double totalExpend() {
		String sql = "select sum(cost) from accout where Month(date) = "+currentMonth;
		try(Statement ps = c.createStatement();) {			
			ResultSet rs = ps.executeQuery(sql);
			rs.next();
			return rs.getDouble(1);				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public double budget() {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("protities.txt"), "utf-8"));){		
			double budget = Double.parseDouble(reader.readLine());
			return budget;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}
	
	/*
	 * �õ��˹̶����ֶ����ƣ�cost , date
	 */
	public double totalTodayExpend() {		
		return ExpendOfDate(new GregorianCalendar().get(Calendar.DATE));
	}
	
	public double ExpendOfDate(int date) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, date);
		String sql = "select sum(cost) from accout where date = "
				 +"'" + new java.sql.Date(calendar.getTimeInMillis())+ "'";
		try(Statement ps = c.createStatement();) {			
			ResultSet rs = ps.executeQuery(sql);
			rs.next();
			return rs.getDouble(1);				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public List<Map<String, Object>> getRecordList() {
		return recordList;
	}

	public String getDbPath() {
		return dbPath;
	}
	
	public String[] getFieldName() {
		return JDBCUtil.FIEDLE_NAME;
	}
		
	public static void main(String[] args) {
		AccountJDBC accountBook = new AccountJDBC();
		accountBook.insert(6000, "ס", 1123, "����");
		accountBook.insert(20, "��", 1123, "����");
		accountBook.insert(50, "ʳ", 1123, "����");
		accountBook.insert(50, "ʳ", 1123, "����");
		accountBook.insert(30, "ʳ", 1123, "��ʳ");
	}
}

