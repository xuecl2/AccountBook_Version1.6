package accountbook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Font;
import com.objectplanet.chart.BarChart;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIUtil {
	private GUIUtil() {
		
	}
	public static void ringDraw(Graphics g,Color ringColor , Color backgroudColor , 
			int daimeterBig , int daimeterSmall,
			int panelWidth , int panelHeigth,
			int horizonOffset , int VerticalOffset,
			int degree) {
		g.setColor(ringColor);
		g.fillArc((panelWidth-daimeterBig-horizonOffset)/2, (panelHeigth-daimeterBig-VerticalOffset)/2+VerticalOffset, 
				daimeterBig, daimeterBig,90,-degree);
		g.setColor(backgroudColor);
		g.fillArc((panelWidth-daimeterSmall-horizonOffset)/2, (panelHeigth-daimeterSmall-VerticalOffset)/2+VerticalOffset, 
				daimeterSmall, daimeterSmall,90,-degree);
	}
	
	public static void ringStateBar(int angle , Color ringBackgroudColor , Color ringColor , JComponent panel , Graphics g ) {
		ringDraw(g,ringBackgroudColor, panel.getBackground(), 200, 160, panel.getWidth(), panel.getHeight(), 50, 40,360);
		ringDraw(g,ringColor, panel.getBackground(), 200, 160, panel.getWidth(), panel.getHeight(), 50, 40,angle);
	}
	
	public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
	
	public static BarChart getBarChart(double[] values, String[] barLabels, Rectangle rect){
		BarChart chart = new BarChart();
	      
	      chart.setSampleCount(values.length);
	      chart.setSampleValues(0, values); 
	      chart.setRange(0, 1.2*maxValue(values));
	      
	      chart.setLegendPosition(BarChart.LEFT);
	      chart.setLegendOn(true);
	      chart.setLegendLabels(new String[] {"月消费报表"});
	      chart.setFont("legendFont", new Font("Dialog", Font.PLAIN, 12));	      
	      
	      chart.setBarLabels(barLabels);
	      chart.setFont("barLabelFont", new Font("Dialog", Font.PLAIN, 12));
	      chart.setBarLabelsOn(true);
	      chart.setAutoLabelSpacingOn(true);
	      
	      chart.setChartBackground(Color.GRAY);
		  chart.setValueLinesOn(true);
		  chart.setValueLabelColor(0, Color.white);	
		  chart.setBounds(rect);
		  return chart;
    }
	
	public static double maxValue(double[] data) {
		double max = data[0];
		for(double x:data) {
			if (x>max) {
				max = x;
			}
		}
		return max;
	}
}
