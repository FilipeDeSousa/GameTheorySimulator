package gametheorysimulator.file;

import java.io.File;
import java.util.Map;

import gametheorysimulator.file.graphs.OutputGraph;

//Singleton class
public class OutputFileGraphs extends OutputFile {
	private static OutputFileGraphs outputFileGraphs = null;
	
	private final String PREFIX_FILENAME = "GraphOut";
	private final String COOPERATION_RATIO_FILENAME = "CooperationRatio.png";
	private final String MEDIAN_PAYOFF_FILENAME = "MedianPayoff.png";
	
	private File cooperationRatioFile, medianPayoffFile;
	private String path;
	private OutputFileInfo outputFileInfo;
		
	//Constructor
	private OutputFileGraphs(OutputFileInfo outputFileInfo){
		this.outputFileInfo = outputFileInfo;
		path = outputFileInfo.getPath();
		cooperationRatioFile = new File(ROOT_DIRECTORY+"/"+path+"/"+PREFIX_FILENAME+COOPERATION_RATIO_FILENAME);
		medianPayoffFile = new File(ROOT_DIRECTORY+"/"+path+"/"+PREFIX_FILENAME+MEDIAN_PAYOFF_FILENAME);
	}
	
	//Static
	static public OutputFileGraphs newInstance(OutputFileInfo outputFileInfo) {
		if(outputFileGraphs == null)
			outputFileGraphs = new OutputFileGraphs(outputFileInfo);
		return outputFileGraphs;
	}
	
	//Non-Static
	public void printGraphs() {
		OutputGraph cooperationRatioGraph = new OutputGraph("Cooperation Ratio", "Run", "Cooperation Ratio");
		Map<Double, Double> values = outputFileInfo.getMapValues("number", "cooperationRatio");
		cooperationRatioGraph.plotGraph(values, cooperationRatioFile);
		
		OutputGraph medianPayoffGraph = new OutputGraph("Median Payoff", "Run", "Median Payoff");
		values = outputFileInfo.getMapValues("number", "medianPayoff");
		medianPayoffGraph.plotGraph(values, medianPayoffFile);
	}
}
