package jdbc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class JDBCUtil {
	public static final String[] FIEDLE_NAME = {"id","cost","category","date","remark"};
	public static final String[] costCatogary = {"衣" , "食" , "住" ,"行","其他"};
	private JDBCUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 解析存放成csv文件形式的数据库数据
	 * 排除第一行
	 * @return 分解成单独字段的二维数组
	 * @param filePath csv文件路径
	 * @param rowOffset 跳过的行数
	 * 
	 */
	public static String[][] csvReader1(String filePath,int rowOffset) {		
		try ( 
				FileInputStream fis = new FileInputStream(filePath);
				InputStreamReader isr = new InputStreamReader(fis,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				){	
			char[] data = {};
			char[] dataTmp = new char[1*1024];
			int len = br.read(dataTmp);
			while (len != -1) {
				dataTmp = Arrays.copyOf(dataTmp, len);
				data = charArrJoint(data,dataTmp);
				len = br.read(dataTmp);
			}
			br.close();
			System.out.println("CSV Read Completed!");
			
			String dataString = new String(data);
			String[] dataStringPostParse1 = dataString.split("\n");
			int rowLength = dataStringPostParse1.length;
			String[][] dataStringPostParse2= new String[rowLength-rowOffset][];
			
			for(int i = rowOffset; i<rowLength ; i++) {
				dataStringPostParse2[i-rowOffset] =
						dataStringPostParse1[i].split(",");
				
			}
			return dataStringPostParse2;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}
	
	public static char[] charArrJoint(char[] char1, char[] char2) {
		char[] char3 = new char[char1.length+char2.length];
		System.arraycopy(char1, 0, char3, 0, char1.length);
		System.arraycopy(char2, 0, char3, char1.length, char2.length);
		return char3;
	}
	
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();		
		String[][] data = csvReader1("C:\\Users\\JSD\\Desktop\\SQL Backup\\shadowverse.csv",0);
		
		System.out.println("CSV Parse Complete！ Total time： "
				+ (System.currentTimeMillis()-t0)/1000.0+ " s!");		
		for( int i = 0 ; i<data.length ;i++) {
			for(int j = 0; j< data[0].length; j++) {				
				System.out.print(data[i][j]+"\t\t");
			}		
			System.out.println();
		}
	}	
}
