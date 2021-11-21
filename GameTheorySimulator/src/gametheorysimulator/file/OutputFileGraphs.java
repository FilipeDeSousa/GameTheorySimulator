package gametheorysimulator.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import gametheorysimulator.file.graphs.OutputGraph;

//Singleton class
public class OutputFileGraphs extends OutputFile {
	private static OutputFileGraphs outputFileGraphs = null;
	
	private final String PREFIX_FILENAME = "GraphOut";
	private final String COOPERATION_RATIO_FILENAME = "CooperationRatio.png";
	private final String AVERAGE_PAYOFF_FILENAME = "AveragePayoff.png";
	
	private String path;
	private OutputFileInfo outputFileInfo;
		
	//Constructor
	private OutputFileGraphs(OutputFileInfo outputFileInfo){
		this.outputFileInfo = outputFileInfo;
		path = outputFileInfo.getPath();
	}
	
	//Static
	static public OutputFileGraphs newInstance(OutputFileInfo outputFileInfo) {
		if(outputFileGraphs == null)
			outputFileGraphs = new OutputFileGraphs(outputFileInfo);
		return outputFileGraphs;
	}
	
	//Non-Static
	public void printGraphs(int runs) {
		for(int i=0; i<runs; i++) {
			String completePath = ROOT_DIRECTORY+"/"+path+"/"+(i+1)+"/";
			Path completePath2 = Paths.get(completePath);
			try {
				Files.createDirectories(completePath2);
				File cooperationRatioFile = new File(completePath+PREFIX_FILENAME+COOPERATION_RATIO_FILENAME);
				File medianPayoffFile = new File(completePath+PREFIX_FILENAME+AVERAGE_PAYOFF_FILENAME);
				
				OutputGraph cooperationRatioGraph = new OutputGraph("Cooperation Ratio", "t", "Cooperation Ratio");
				Map<Double, Double> values = outputFileInfo.getMapValues("iteration", "cooperationRatio", i);
				cooperationRatioGraph.plotGraph(values, cooperationRatioFile);
				
				OutputGraph medianPayoffGraph = new OutputGraph("Average Payoff", "t", "Average Payoff");
				values = outputFileInfo.getMapValues("iteration", "averagePayoff", i);
				medianPayoffGraph.plotGraph(values, medianPayoffFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
