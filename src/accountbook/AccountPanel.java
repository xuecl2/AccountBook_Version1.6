package accountbook;
/**
 * 生成自定义的界面
 * @author Xuecl
 */

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jdbc.AccountJDBC;

public class AccountPanel  {
	protected AccountJDBC account;
	protected JPanel panel = new JPanel();
	protected JButton button ;
	protected JLabel label ;
	protected String iconDir = "pics/";
	protected String title;
	
	public AccountPanel(String title,AccountJDBC account) {
		this.account = account;
		this.title = title;
		panel.setBorder(BorderFactory.createEtchedBorder());
		Icon icon = new ImageIcon(GUIUtil.getScaledImage(new ImageIcon(iconDir+title+".png").getImage(), 60, 58));
		button = new JButton(null, icon);
		button.setPreferredSize(new Dimension(60,55));
		button.setBorder(null); 
		button.setFocusPainted(false);
//		button.setVerticalTextPosition(AbstractButton.BOTTOM);
//		button.setHorizontalTextPosition(AbstractButton.CENTER);
		
		label = new JLabel(title);
		label.setPreferredSize(new Dimension(60,20));
		label.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.TOP);
	}
	public void refresh() {
		
	}

	public JPanel getPanel() {
		return panel;
	}
	
	public JButton getButton() {
		return button;
	}
	public JLabel getLabel() {
		return label;
	}
}
