package accountbook;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXDatePicker;
import jdbc.AccountJDBC;
import jdbc.JDBCUtil;

public class AccountPanel3 extends AccountPanel {
	JTable table;
	JTable table2;
	String[] title1;
	Object[][] values1;	
	JScrollPane  contentPanel1;
	
	JDialog dialog;
	String[] title2;
	Object[][] values2;
	JButton dialogButton1 = new JButton("保存修改");
	JButton dialogButton2 = new JButton("删除选中");	
	JScrollPane  contentPanel2;
	
	public AccountPanel3(AccountJDBC account) {	
		super("消费分类",account);	
		values1 = account.groupByCategory();	
		title1 = new String[] {"分类名称","消费次数"};
		table = new JTable(new TableModel1(title1,values1));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
//		tcr.setHorizontalAlignment(JLabel.CENTER);
//		table.setDefaultRenderer(Object.class, tcr);

		contentPanel1 = new JScrollPane(table);
		contentPanel1.setBounds(0,0,500,230);
		//contentPanel1.setLayout(new GridLayout(4, 2,50,35));
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(30, 70, 0, 70));
		
		
		JPanel contentPanel2 = new JPanel();
		contentPanel2.setBounds(0,230,500,50);
		contentPanel2.setLayout(new FlowLayout());
		contentPanel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 50));
		//JButton button1 = new JButton("新增");
		JButton button2 = new JButton("修改");
		//JButton button3 = new JButton("删除");
		//button1.setForeground(new Color(125, 125, 250));
		button2.setForeground(new Color(125, 125, 250));
		//button3.setForeground(new Color(125, 125, 250));
		//contentPanel2.add(button1);
		contentPanel2.add(button2);
		//contentPanel2.add(button3);	
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				table2Create();
				JPanel dialogPanel1 = new JPanel();
				dialogPanel1.setBounds(0, 230, 500, 50);
				dialogPanel1.setLayout(new FlowLayout());				
				dialogPanel1.add(dialogButton1);
				dialogPanel1.add(dialogButton2);
				JScrollPane  scrollPane = new JScrollPane(table2);
				scrollPane.setBounds(0, 0, 500, 230);
				
				dialog = new JDialog((JFrame)null, "消费记录", true);
				dialog.setBounds(panel.getX(),panel.getY(),500,320);				
				dialog.setLayout(null);				
				dialog.add(scrollPane);	
				dialog.add(dialogPanel1);
				dialog.setVisible(true);	
			}
		});
		
		dialogButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(table2.getRowCount());
				for(int i=0; i<table2.getRowCount(); i++) {
					int id = (int)table2.getValueAt(i, 0);
					double cost = (double)table2.getValueAt(i, 1);
					String category = (String)table2.getValueAt(i, 2);
					java.sql.Date date = new java.sql.Date(((java.util.Date)(table2.getValueAt(i, 3))).getTime());
					String remark = (String)table2.getValueAt(i, 4);
					account.update(id, cost, category, date, remark);	
					System.out.println("事件添加成功！");
				}
				JOptionPane.showMessageDialog(dialog,"修改成功");
				refresh();				
			}
		});
		
		dialogButton2.addActionListener(new ActionListener() {
			//这里用了固定序号0来表示id的位置
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int x:table2.getSelectedRows()) {
					System.out.println("AccoutPanle3  dialogButtonAction: the selctied row: "+x);
					account.delete((int)table2.getValueAt(x, 0));
				}				
				JOptionPane.showMessageDialog(dialog,"删除成功");
				refresh();
				dialog.dispose();
			}
		});
		
		panel.setLayout(null);
		panel.add(contentPanel1);
		panel.add(contentPanel2);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		panel.remove(contentPanel1);
		Object[][] objects = account.groupByCategory();	
		table1Create(title1, objects);
		panel.validate();
		panel.repaint();		
		System.out.println("AccoutPanel3: refresh 执行");
	}
	
	public void table1Create(String[] title, Object[][] objects) {
		table = new JTable(new TableModel1(title1,objects));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPanel1 = new JScrollPane(table);
		contentPanel1.setBounds(0,0,500,230);
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(30, 70, 0, 70));		
		panel.add(contentPanel1);
	}
	
	//用到了固定的category序号2
	public void table2Create() {		
		getTitleAndValues(account);
		table2 = new JTable(new TableModel2(title2,values2));	
		table2.setDefaultEditor(Date.class, new DateEditor());
		JComboBox<String> comboBox = new JComboBox<>(JDBCUtil.costCatogary);
		TableColumn categoryColumn = table2.getColumnModel().getColumn(2);
		categoryColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
	public String getSelectedColumsFromTable(){
		return (String)table.getValueAt(table.getSelectedRow(), 0);
	}
	
	public void getTitleAndValues(AccountJDBC accout){
		title2 = accout.getFieldName();
		values2 = accout.queryByCategory(getSelectedColumsFromTable());
	}
}

class TableModel1 extends AbstractTableModel {
	String[] title1;
	Object[][] values;	

	public TableModel1(String[] title1, Object[][] values) {
		super();
		this.title1 = title1;
		this.values = values;
	}
	
	@Override
	public String getColumnName(int col) {
        return title1[col];
    }
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return values.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return title1.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return values[rowIndex][columnIndex];
	}
	
	public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.       
	    return false;        
	}
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
		//return String.class;
    }
	public void setValueAt(Object value, int row, int col) {  
		values[row][col] = value;
        fireTableCellUpdated(row, col);
	}
}

class TableModel2 extends TableModel1{	
	
	public TableModel2(String[] title1, Object[][] values) {
		super(title1,values);
	}
	
	public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.  
		if(col<1)
			return false; 
		else	
			return true;
	}
}

class DateEditor extends AbstractCellEditor implements TableCellEditor {
	private Date date;
	private JXDatePicker datepicker;
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		date = datepicker.getDate();
		System.out.println(date);
		return date;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		datepicker = new JXDatePicker((Date)value);
		return datepicker;
	}
}



