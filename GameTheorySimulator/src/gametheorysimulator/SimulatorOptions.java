package gametheorysimulator;

import java.util.HashMap;
import java.util.Map;

import gametheorysimulator.game.Game;
import gametheorysimulator.game.PGG;
import gametheorysimulator.space.GameSpace;
import gametheorysimulator.space.Grid2D;

class SimulatorOptions {
	enum SpaceOptions {
		GRID_2D
	}
	
	private int numberPlayers;
	private GameSpace space;
	private Game game;
	private Map<String, Integer> strategies = new HashMap<String, Integer>();
	private int iterations = 100;//100 per default
	private GameSpace.Reach reach;
	
	public SimulatorOptions(String[] args) {
		for(int i=0; i<args.length; i++)
			switch(args[i]) {
				case "-n":
					numberPlayers = Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-space":
					switch(args[i+1]){
						case "Grid2D":
							String[] dimensions = args[i+2].split(";");
							space = new Grid2D(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
					}
					i++;
					break;
				case "-reach":
					switch(args[i+1]) {
						case "ImmediateNeighbors":
							reach = GameSpace.Reach.IMMEDIATE_NEIGHBORS;
							break;
					}
					break;
				case "-game":
					switch(args[i+1]) {
						case "PGG":
							game = new PGG();
							break;
					}
					break;
				case "-iterations":
					iterations = Integer.parseInt(args[i+1]);
				case "-strategies":
					String[] strategiesArray = args[i+1].split(";");
					for(int j=0; j<strategiesArray.length; j+=2)
						strategies.put(strategiesArray[j], Integer.parseInt(strategiesArray[j+1]));
			}
		setup();
	}

	private void setup() {
		space.setReach(reach);
	}

	public GameSpace getSpace() {
		if(space == null){
			System.out.println("ERROR: Space is not set!");
			System.exit(0);
		}
		
		return space;
	}

	public int getNumberPlayers(){
		return numberPlayers;
	}

	public int getIterations() {
		return iterations;
	}

	public Game getGame() {
		return game;
	}

	public Map<String, Integer> getStrategies() {
		return strategies;
	}

}
