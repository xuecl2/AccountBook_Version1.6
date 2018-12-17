package accountbook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdbc.AccountJDBC;


public class AccountPanel1 extends AccountPanel {
	JPanel contentPanel2;
	JLabel[] labels1;
	JLabel[] labels3;
	public AccountPanel1(AccountJDBC account) {	
		super("消费一览", account);		
		String[] contenStrings1 = {	"本月消费" , 
				"￥"+(int)account.totalExpend() , 
				"今日消费" ,
				"￥"+(int)account.totalTodayExpend()
				};
		JPanel contentPanel1 = new JPanel();
		contentPanel1.setBounds(0,0,150,250);
		contentPanel1.setLayout(new GridLayout(4, 1));
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		labels1 = new JLabel[4];
		for(int i = 0 ; i<labels1.length ; i++) {
			labels1[i] = new JLabel(contenStrings1[i]);
			if(i == 1 || i == 3) {
				labels1[i].setFont(new Font(Font.DIALOG, Font.BOLD, 25));
				labels1[i].setForeground(new Color(100, 100, 250));
				labels1[i].setPreferredSize(new Dimension(50, 100));
			}else {
				labels1[i].setForeground(Color.GRAY);
			}
			contentPanel1.add(labels1[i]);
		}
		
		/*
		 * 这里曾尝试将contenPanel2设置成局部变量，但会报错。
		 * 不理解！
		 */
		contentPanel2 = new JPanel() {
			@Override
			public void paint(Graphics g) {
				int costPercent = account.budgetConsumed();
				if(costPercent<80) {
					GUIUtil.ringStateBar((int)(costPercent/100.0*360.0), Color.blue, Color.green, contentPanel2, g);
					g.setColor(Color.green);
				}
				else if(costPercent<100) {
					GUIUtil.ringStateBar((int)(costPercent/100.0*360.0), Color.blue, Color.red, contentPanel2, g);
					g.setColor(Color.red);
				}
				else {
					GUIUtil.ringStateBar(360, Color.blue, Color.red, contentPanel2, g);
					g.setColor(Color.red);
				}				
				
				g.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
				g.drawString(costPercent + "%", 125, 155);
			}
		};
		contentPanel2.setBounds(150,0,350,250);
		
		
		JPanel contentPanel3 = new JPanel();
		contentPanel3.setBounds(0,250,500,70);
		contentPanel3.setBorder(BorderFactory.createEmptyBorder(0, 50, 30, 50));
		contentPanel3.setLayout(new GridLayout(2, 4, 50, 0));
		JLabel[] labels2 = new JLabel[4];
		String[] contenStrings2 = {	"日均消费" , "本月剩余" , "日均可用" ,"距离月末"};
		for(int i = 0 ; i<labels2.length ; i++) {
			labels2[i] = new JLabel(contenStrings2[i]);	
			labels2[i].setForeground(Color.GRAY);
			contentPanel3.add(labels2[i]);			
		}
		
		labels3 = new JLabel[4];
		int passedDaysInMonth = new GregorianCalendar().get(Calendar.DATE);
		int remainDaysInMonth = new GregorianCalendar().getActualMaximum(Calendar.DATE) - passedDaysInMonth;
		String[] contenStrings3 = {	
				"￥"+ (int)(account.totalExpend()/passedDaysInMonth) , 
				"￥"+ (int)(account.budget() - account.totalExpend()) , 
				"￥"+ (int)((account.budget() - account.totalExpend())/remainDaysInMonth) , 
				""+ (int)(remainDaysInMonth)  
				};
		for(int i = 0 ; i<labels3.length ; i++) {
			labels3[i] = new JLabel(contenStrings3[i]);	
			labels3[i].setForeground(Color.GRAY);
			contentPanel3.add(labels3[i]);
		}
		
		panel.setLayout(null);
		panel.add(contentPanel1);
		panel.add(contentPanel2);
		panel.add(contentPanel3);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub	
		if(!panel.isVisible()) {
			contentPanel1Refresh();
			contentPanel2.repaint();
			contentPanel3Refresh();	
		}
	}
	private void contentPanel1Refresh() {
		String[] contenStrings1 = {	"本月消费" , 
				"￥"+(int)account.totalExpend() , 
				"今日消费" ,
				"￥"+(int)account.totalTodayExpend()
				};
		for(int i = 0 ; i<labels1.length ; i++) {
			labels1[i] .setText(contenStrings1[i]);
		}
	}
	
	private void contentPanel3Refresh() {
		int passedDaysInMonth = new GregorianCalendar().get(Calendar.DATE);
		int remainDaysInMonth = new GregorianCalendar().getActualMaximum(Calendar.DATE) - passedDaysInMonth;
		String[] contenStrings3 = {	
				"￥"+ (int)(account.totalExpend()/passedDaysInMonth) , 
				"￥"+ (int)(account.budget() - account.totalExpend()) , 
				"￥"+ (int)((account.budget() - account.totalExpend())/remainDaysInMonth) , 
				""+ (int)(remainDaysInMonth)  
				};
		for(int i = 0 ; i<labels3.length ; i++) {
			labels3[i].setText((contenStrings3[i]));;
		}
	}
}
