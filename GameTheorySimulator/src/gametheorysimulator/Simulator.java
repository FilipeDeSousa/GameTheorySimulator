package gametheorysimulator;

import java.util.List;
import java.util.Map;

import gametheorysimulator.file.OutputFileInfo;
import gametheorysimulator.players.Player;
import gametheorysimulator.space.DynamicGameSpace;
import gametheorysimulator.space.GameSpace;
import gametheorysimulator.space.Grid2D;

public class Simulator {
	private static SimulatorOptions options;
	private static GameSpace space;
	private static List<Player> players;
	private static OutputFileInfo out = OutputFileInfo.getInstance();
	
	public static void main(String[] args) {
		//Initial setups
		options = new SimulatorOptions(args);
		space = options.getSpace();
		players = space.populate(options.getNumberPlayers());
		setPlayersStrategies();
		
		//Run iterations
		for(int i=0; i<options.getIterations(); i++) {
			if(space.hasMovement())
				for(Player player: players)
					player.move();
			for(Player player: players)
				player.setReachablePlayers();
			for(Player player: players)
				player.decide();
			for(Player player: players)
				player.computePayoff();
			System.out.println("Iteration "+(i+1)+" :");
			if(DynamicGameSpace.class.isInstance(space) && Grid2D.class.isInstance(space))
				((Grid2D) space).printGridOccupation();
			//printInfo();
			out.printIteration(players);
		}
		
		//Last instructions
		out.close();
	}

	private static void setPlayersStrategies() {
		Map<String, Integer> strategies = options.getStrategies();
		
		//Validate number of strategies
		int numberStrategies = 0;
		for(Map.Entry<String, Integer> strategy: strategies.entrySet())
			numberStrategies += strategy.getValue();
		int numberPlayers = options.getNumberPlayers();
		if(numberStrategies != numberPlayers) {
			System.out.println("ERROR: Number of strategies "+numberStrategies+" and players "+numberPlayers+" differ.");
			System.exit(0);
		}
		
		int i = 0;
		for(Map.Entry<String, Integer> strategy: strategies.entrySet()){
			for(int j=0; j<strategy.getValue(); j++) {
				players.get(i).setStrategy(strategy.getKey());
				i++;
			}
		}
	}

	/*private static void printInfo() {
		double sum = 0;
		int numberCooperators = 0, numberDefectors = 0;
		for(Player player: players) {
			System.out.println(player.getLastIterationInfo());
			sum += player.getPayoff();
			if(player.getDecision())
				numberCooperators++;
			else
				numberDefectors++;
		}
		System.out.println("Payoff Sum = "+sum+"; Number of Cooperators = "+numberCooperators+"; Number of Defectors = "+numberDefectors);
	}*/

}
