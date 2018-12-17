package accountbook;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.incors.plaf.alloy.dd;

import jdbc.AccountJDBC;

public class AccountPanel6 extends AccountPanel {

	public AccountPanel6(AccountJDBC account) {	
		super("备份" , account);
		this.account = account;
		JPanel contentPanel1 = new JPanel();
		contentPanel1.setBounds(0,230,500,50);
		contentPanel1.setLayout(new FlowLayout());
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 50));
		JButton button1 = new JButton("备份");
		button1.setForeground(new Color(125, 125, 250));
		contentPanel1.add(button1);
		panel.setLayout(null);
		panel.add(contentPanel1);
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								new FileInputStream("protities.txt"), "utf-8"));){		
					reader.readLine();
					String path = reader.readLine();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
					String time = dateFormat.format(new Date());
					account.backUp(path + "/ " +time +".obj");	
					JOptionPane.showMessageDialog(panel, "备份成功！");
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
			}
		});
	}
	
}
