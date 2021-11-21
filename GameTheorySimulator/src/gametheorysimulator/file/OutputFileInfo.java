package gametheorysimulator.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gametheorysimulator.players.Player;

//Singleton class
public class OutputFileInfo extends OutputFile {
	private static OutputFileInfo outputFileInfo = null;
	
	private final String INIT_PATH = "GameSimulator-Out";
	private final String FILENAME = "InfoOut.xml";
	
	private File file;
	private Document document;
	private int numberIteration = 1, numberRun = 1;
	private String path;
	
	//Constructor
	private OutputFileInfo(){
		File dir = new File(ROOT_DIRECTORY);
		String[] filenames = dir.list();
		if(filenames == null)
			path = INIT_PATH;
		else
			outter: for(int i = 1; ; i++) {
				for(String auxFilename: filenames) {
					if(i == 1 && auxFilename.equals(INIT_PATH))
						continue outter;
					if(i != 1 && auxFilename.equals(INIT_PATH+i))
						continue outter;
				}
				if(i == 1)
					path = INIT_PATH;
				else
					path = INIT_PATH+i;
				break;
			}
		try {
			//Create target folder
			Path path2 = Paths.get(ROOT_DIRECTORY+"/"+path);
			Files.createDirectories(path2);
			
			file = new File(ROOT_DIRECTORY+"/"+path+"/"+FILENAME);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.newDocument();
			
			Element simulation = document.createElementNS("urn:game-theory:simulation", "simulation");
			document.appendChild(simulation);
		} catch (ParserConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//Static
	static public OutputFileInfo getInstance() {
		if(outputFileInfo == null)
			outputFileInfo = new OutputFileInfo();
		return outputFileInfo;
	}
	
	//Non-Static
	public void printIteration(List<Player> players) {
		Node simulation = document.getFirstChild();
		Node run = simulation.getLastChild();
		Element iteration = document.createElement("iteration");
		iteration.setAttribute("iteration", numberIteration+"");numberIteration++;
		int numberPlayers = players.size();
		iteration.setAttribute("numberOfPlayers", numberPlayers+"");
		double totalPayoff = 0;
		int numberAlone = 0, numberCooperators = 0, numberDefectors = 0;
		for(Player player: players) {
			Element playerElement = document.createElement("player");
			
			Element playerId = document.createElement("id");
			playerId.appendChild(document.createTextNode(player.getId()+""));
			playerElement.appendChild(playerId);
			
			Element playerPayoffElement = document.createElement("payoff");
			double playerPayoff = player.getPayoff();
			totalPayoff += playerPayoff;
			playerPayoffElement.appendChild(document.createTextNode(playerPayoff+""));
			playerElement.appendChild(playerPayoffElement);
			
			Element playerDecisionElement = document.createElement("decision");
			String playerDecision = "";
			boolean cooperates = player.getDecision();
			boolean isAlone = player.getReachablePlayers().size()==0?true:false;
			playerDecisionElement.appendChild(document.createTextNode(playerDecision));
			if(isAlone) {
				playerDecision = "alone";
				numberAlone ++;
			}else if(cooperates) {
				playerDecision = "cooperates";
				numberCooperators ++;
			}else {
				playerDecision = "defects";
				numberDefectors ++;
			}
			playerElement.appendChild(playerDecisionElement);
			
			iteration.appendChild(playerElement);
		}
		iteration.setAttribute("totalPayoff", totalPayoff+"");
		iteration.setAttribute("averagePayoff", (totalPayoff/numberPlayers) +"");
		iteration.setAttribute("numberAlone", numberAlone +"");
		iteration.setAttribute("numberCooperators", numberCooperators +"");
		iteration.setAttribute("numberDefectors", numberDefectors +"");
		iteration.setAttribute("cooperationRatio", ((double)numberCooperators)/((double)(numberCooperators + numberDefectors)) +"");
		run.appendChild(iteration);
	}
	
	public void printRun() {
		Node simulation = document.getFirstChild();
		Element run = document.createElement("run");
		run.setAttribute("run", numberRun+"");numberRun++;
		simulation.appendChild(run);
	}
	
	private void finalStatistics() {
		//TODO
	}
	
	public void close() {
        finalStatistics();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			
			//Pretty Print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OutputFileGraphs graphs = OutputFileGraphs.newInstance(this);
		graphs.printGraphs(numberRun-1);
		
		outputFileInfo = null;
	}

	protected String getPath() {
		return path;
	}

	public Map<Double, Double> getMapValues(String keyName, String valueName, int run) {
		NodeList runs = document.getFirstChild().getChildNodes();
		NodeList iterations = runs.item(run).getChildNodes();
		Map<Double, Double> result = new HashMap<Double, Double>();
		for(int i=0; i<iterations.getLength(); i++) {
			Node iteration = iterations.item(i);
			NamedNodeMap attributes = iteration.getAttributes();
			double key = Double.parseDouble(attributes.getNamedItem(keyName).getNodeValue());
			double value = Double.parseDouble(attributes.getNamedItem(valueName).getNodeValue());
			result.put(key, value);
		}
		return result;
	}
}
