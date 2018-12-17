package accountbook;
import java.awt.Color;
import java.awt.FlowLayout;
/**
 * 模仿tabPanel创建自定义的tabPanel
 * 用button来代替tab
 * 不能超过10个tab
 * @author Xuecl
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccoutTabPanel {
	private int count = 0;
	private JPanel tabPanel = new JPanel();
	private JPanel tabLabelPanel = new JPanel();
	private JButton[] buttons = new JButton[10];
	private JLabel[] labels = new JLabel[10];
	private JPanel[] panels = new JPanel[10];
	private AccountPanel[] accountPanels = new AccountPanel[10];
	private int panelWidth;
	private int panelHeight;
	private int panelVerticalLocation;
	boolean[] panelVisible = new boolean[10];
	
	public AccoutTabPanel(int frameWidth , int frameHeight , 
		int tabPanelHeight , int tabPanelborad) {
		tabPanel.setBounds(0,0,frameWidth,tabPanelHeight-30);		
		tabPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, tabPanelborad));
		tabPanel.setLayout(new FlowLayout());
		tabPanel.setBackground(Color.red);
		
		tabLabelPanel.setBounds(0,tabPanelHeight-35,frameWidth,30);
		tabLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, tabPanelborad));
		tabLabelPanel.setLayout(new FlowLayout());
		tabLabelPanel.setBackground(Color.blue);
		panelHeight = frameHeight - tabPanelHeight;
		panelWidth = frameWidth;
		panelVerticalLocation = tabPanelHeight;
	}
	
	public void add(AccountPanel panel) {		
		buttons[count] = panel.getButton();
		labels[count] = panel.getLabel();
		panels[count] = panel.getPanel();
		accountPanels[count] = panel;
		tabPanel.add(buttons[count]);	
		tabLabelPanel.add(labels[count]);		
		panels[count].setBounds(0,panelVerticalLocation,panelWidth,panelHeight);
		System.out.println(tabPanel.getHeight()+","+panelHeight+","+panelWidth);
		if(count > 0) {
			panels[count].setVisible(false);
		}
		
		count++;
	}
	
	public void activateTab() {
		for(int i = 0;i<count; i++ ) {		
			int buttonIndex = i;
			buttons[i].addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e ) {
					for(int i = 0; i<count; i++ ) {
						if(i!=buttonIndex) {
							panelVisible[i] = false;
						}else {
							panelVisible[i] = true;
							accountPanels[i].refresh();
						}
						panels[i].setVisible(panelVisible[i]);	
					}
				}
			} );
		}
	}

	public JPanel getTabPanel() {
		return tabPanel;
	}

	public JPanel[] getPanels() {
		return panels;
	}

	public int getCount() {
		return count;
	}
	
	public JPanel getTabLabelPanel() {
		return tabLabelPanel;
	}
}
