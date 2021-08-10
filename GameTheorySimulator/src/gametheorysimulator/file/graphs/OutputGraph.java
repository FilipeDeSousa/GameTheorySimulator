package gametheorysimulator.file.graphs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class OutputGraph extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6495575433332875501L;
	
	private String title, xAxis, yAxis;

	public OutputGraph(String title, String x, String y) {
		super();
		this.title = title;
		xAxis = x;
		yAxis = y;
	}		
		
	public void plotGraph(Map<Double, Double> values, File file) {
		XYSeries series = new XYSeries(yAxis);
		
		for(Map.Entry<Double, Double> value : values.entrySet()) {
			series.add(value.getKey(), value.getValue());
		}
		
		XYDataset dataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset);
		try {
			ChartUtilities.saveChartAsPNG(file, chart, 450, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}