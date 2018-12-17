package accountbook;

import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.objectplanet.chart.Chart;

import jdbc.AccountJDBC;

public class AccountPanel4 extends AccountPanel {
	Chart costReportChart;
	JPanel contentPanel1;
	public AccountPanel4(AccountJDBC account) {	
		super("月消费报表",account);
		contentPanel1 = new JPanel();
		contentPanel1.setBounds(0,0,500,320);
		contentPanel1.setLayout(null);
		/*contentPanel1.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 50));*/
		costReportChart = getCostReportChart();
		contentPanel1.add(costReportChart);				
		panel.setLayout(null);
		panel.add(contentPanel1);
	}
	
	private Chart getCostReportChart() {
		Calendar calendar = new GregorianCalendar();
		int maxDate = calendar.getActualMaximum(Calendar.DATE);
		double[] values = new double[maxDate];
		String[] barLabels = new String[maxDate];
		for(int i = 0 ; i<maxDate; i++) {
			values[i] = account.ExpendOfDate(i);
			barLabels[i] = i+1 +"日";
		}
		return GUIUtil.getBarChart(values, barLabels,new Rectangle(50, 35, 350, 250));
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		if(!panel.isVisible()) {
			System.out.println("AccountPanel4 refresh 执行");
			contentPanel1.remove(costReportChart);
			costReportChart = getCostReportChart();
			contentPanel1.add(costReportChart);
		}
	}	
}
