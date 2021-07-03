package gametheorysimulator;

import java.util.List;

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
	}

}
