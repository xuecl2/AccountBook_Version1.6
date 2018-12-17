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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.AccountJDBC;

public class AccountPanel5 extends AccountPanel {
	
	private JTextField textField2 ;
	
	public AccountPanel5(AccountJDBC account) {	
		super("设置",account);
		JPanel contentPanel1 = new JPanel();
		contentPanel1.setBounds(0,0,500,230);
		contentPanel1.setLayout(new GridLayout(4, 1,0 ,30));
		contentPanel1.setBorder(BorderFactory.createEmptyBorder(25, 50, 0, 80));
		
		JLabel label1 = new JLabel("本月预算(￥)");
		label1.setForeground(Color.GRAY);
		JTextField textField1 = new JTextField();
		JLabel label2 = new JLabel("数据保存路径");
		label2.setForeground(Color.GRAY);
		textField2 = new JTextField(); 
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("protities.txt"), "utf-8"));){		
			textField1.setText(reader.readLine());
			textField2.setText(reader.readLine());
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
		
		
		contentPanel1.add(label1);
		contentPanel1.add(textField1);
		contentPanel1.add(label2);
		contentPanel1.add(textField2);
		
		JPanel contentPanel2 = new JPanel();
		contentPanel2.setBounds(0,230,500,50);
		contentPanel2.setLayout(new FlowLayout());
		contentPanel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 50));
		JButton button1 = new JButton("更新");
		button1.setForeground(new Color(125, 125, 250));
		contentPanel2.add(button1);
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try(PrintWriter pw = new PrintWriter("protities.txt")) {					
					pw.println(textField1.getText());
					pw.println(textField2.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(panel, "更新成功！");
				
			}
		});

		panel.setLayout(null);
		panel.add(contentPanel1);
		panel.add(contentPanel2);
	}
	
	public void addFileChooserButton() {
		JButton button2 = new JButton("...");
		System.out.println(textField2.getLocation());		
		button2.setBounds(textField2.getX()+textField2.getWidth()+10, textField2.getY()
				, textField2.getHeight(), textField2.getHeight());
		panel.add(button2);
		
		JFileChooser fileChooser = new JFileChooser(textField2.getText());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		button2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileChooser.showOpenDialog(panel);				
				textField2.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
	}

}
