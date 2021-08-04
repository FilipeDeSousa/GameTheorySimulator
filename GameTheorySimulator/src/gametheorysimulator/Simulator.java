package gametheorysimulator;

import java.util.List;
import java.util.Map;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.GameSpace;

public class Simulator {
	private static SimulatorOptions options;
	private static GameSpace space;
	private static List<Player> players;
	
	public static void main(String[] args) {
		options = new SimulatorOptions(args);
		space = options.getSpace();
		players = space.populate(options.getNumberPlayers());
		setPlayersStrategies();
		for(int i=0; i<options.getIterations(); i++) {
			for(Player player: players)
				player.decide();
			for(Player player: players)
				player.computePayoff();
			System.out.println("Iteration "+(i+1)+" :");
			printInfo();
		}
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

	private static void printInfo() {
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
	}

}
