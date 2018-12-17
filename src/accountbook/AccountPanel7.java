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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.AccountJDBC;

public class AccountPanel7 extends AccountPanel {
	public AccountPanel7(AccountJDBC account) {	
		super("»Ö¸´",account);
		this.account = account;
		JPanel contentPanel1 = new JPanel();
		contentPanel1.setBounds(0,230,500,50);
		contentPanel1.setLayout(new FlowLayout());
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 50));
		JButton button1 = new JButton("»Ö¸´");
		button1.setForeground(new Color(125, 125, 250));
		contentPanel1.add(button1);
		panel.setLayout(null);
		panel.add(contentPanel1);
		
		button1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								new FileInputStream("protities.txt"), "utf-8"));
						){		
					reader.readLine();
					String path = reader.readLine();
					JFileChooser fileChooser = new JFileChooser(path);
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.setMultiSelectionEnabled(false);
					int chooserResult = fileChooser.showOpenDialog(panel);	
					if(chooserResult == JFileChooser.APPROVE_OPTION) {
						account.restore2DB(fileChooser.getSelectedFile());
						JOptionPane.showMessageDialog(panel, "»Ö¸´³É¹¦");
					}
										
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
