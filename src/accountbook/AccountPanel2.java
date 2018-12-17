package accountbook;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;
import jdbc.AccountJDBC;
import jdbc.JDBCUtil;


public class AccountPanel2 extends AccountPanel {
	
	
	public AccountPanel2(AccountJDBC account) {	
		super("记一笔", account);
		JPanel  contentPanel1 = new JPanel();
		contentPanel1.setBounds(0,0,500,230);
		contentPanel1.setLayout(new GridLayout(4, 2,50,35));
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(30, 70, 0, 70));
		
		JComponent[] components = new JComponent[4];
		components[0] = new JTextField();
		components[1] = new JComboBox<String>(JDBCUtil.costCatogary);
		components[2] = new JTextField();
		components[3] = new JXDatePicker(new Date());
		
		
		String[] contentStrings1 = {"花费" , "分类" , "备注" ,"日期"};
		JLabel[] labels1 = new JLabel[4];		
		for(int i = 0 ; i<labels1.length ; i++) {
			labels1[i] = new JLabel(contentStrings1[i]);			
			labels1[i].setForeground(Color.GRAY);
			contentPanel1.add(labels1[i]);
			contentPanel1.add(components[i]);
		}
		
		
		JPanel contentPanel2 = new JPanel();
		contentPanel2.setBounds(0,230,500,50);
		//contentPanel2.setLayout(new FlowLayout());
		contentPanel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 50));
		JButton button = new JButton("记一笔");
		button.setForeground(new Color(125, 125, 250));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double cost = Double.parseDouble(((JTextField)(components[0])).getText());
				String category =(String)(((JComboBox<String>)(components[1])).getSelectedItem());
				String remark = ((JTextField)(components[2])).getText();
				Date date = ((JXDatePicker)(components[3])).getDate();
				account.insert(cost,category,date,remark);
				JOptionPane.showMessageDialog(panel, "记录成功");
			}
		});
		contentPanel2.add(button);
		
		panel.setLayout(null);
		panel.add(contentPanel1);
		panel.add(contentPanel2);
	}
	
}
