package gametheorysimulator.file;

import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartUtilities;

import gametheorysimulator.file.graphs.OutputGraph;

//Singleton class
public class OutputFileGraphs extends OutputFile {
	private static OutputFileGraphs outputFileGraphs = null;
	
	private final String PREFIX_FILENAME = "GraphOut";
	private final String COOPERATION_RATIO_FILENAME = "CooperationRatio.png";
	
	private File cooperationRatioFile;
	private String path;
	private OutputFileInfo outputFileInfo;
	private OutputGraph cooperationRatioGraph;
		
	//Constructor
	private OutputFileGraphs(OutputFileInfo outputFileInfo){
		this.outputFileInfo = outputFileInfo;
		path = outputFileInfo.getPath();
		cooperationRatioFile = new File(ROOT_DIRECTORY+"/"+path+"/"+PREFIX_FILENAME+COOPERATION_RATIO_FILENAME);
	}
	
	//Static
	static public OutputFileGraphs newInstance(OutputFileInfo outputFileInfo) {
		if(outputFileGraphs == null)
			outputFileGraphs = new OutputFileGraphs(outputFileInfo);
		return outputFileGraphs;
	}
	
	//Non-Static
	public void printGraphs() {
		cooperationRatioGraph = new OutputGraph("Cooperation Ratio", "Run", "Cooperation Ratio");
		Map<Double, Double> values = outputFileInfo.getMapValues("number", "cooperationRatio");
		cooperationRatioGraph.plotGraph(values, cooperationRatioFile);
	}
}
