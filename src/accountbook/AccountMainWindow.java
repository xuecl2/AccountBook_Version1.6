package accountbook;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import jdbc.AccountJDBC;

public class AccountMainWindow extends JFrame{	
	
	public AccountMainWindow() {
		
	}
	
	public AccountMainWindow(String title) {
		super(title);
	}
	
	public void add(AccoutTabPanel tabPanel) {
		add(tabPanel.getTabPanel());
		add(tabPanel.getTabLabelPanel());
		for(int i = 0 ; i<tabPanel.getCount() ; i++) {
			System.out.println("i= " + i);
			add(tabPanel.getPanels()[i]);
		}		
	}
	
	public void start() {
		AccountJDBC account = new AccountJDBC();
		
		setBounds(100,100,500,450);
		AccoutTabPanel tabPanel = new AccoutTabPanel(getWidth(), getHeight(), 90, 40);
		AccountPanel1 panel1 = new AccountPanel1(account);
		AccountPanel2 panel2 = new AccountPanel2(account);	
		AccountPanel3 panel3 = new AccountPanel3(account);		
		AccountPanel4 panel4 = new AccountPanel4(account);
		AccountPanel5 panel5 = new AccountPanel5(account);
		AccountPanel6 panel6 = new AccountPanel6(account);
		AccountPanel7 panel7 = new AccountPanel7(account);
		tabPanel.add(panel1);
		tabPanel.add(panel2);
		tabPanel.add(panel3);
		tabPanel.add(panel4);
		tabPanel.add(panel5);
		tabPanel.add(panel6);
		tabPanel.add(panel7);
		
		add(tabPanel);
		tabPanel.activateTab();		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		
		panel5.addFileChooserButton();
	}
	
	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccountMainWindow accoutBook = new AccountMainWindow("Ò»±¾ºýÍ¿ÕË");
		accoutBook.start();		
	}
}
