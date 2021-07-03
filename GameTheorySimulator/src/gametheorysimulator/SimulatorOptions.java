package gametheorysimulator;

import gametheorysimulator.space.GameSpace;
import gametheorysimulator.space.Grid2D;

class SimulatorOptions {
	enum SpaceOptions {
		GRID_2D
	}
	enum Reach {
		IMMEDIATE_NEIGHBORS
	}
	
	private int numberPlayers;
	private GameSpace space;
	
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
					}
					break;
			}
	}

	public GameSpace getSpace() {
		return space;
	}

	public int getNumberPlayers(){
		return numberPlayers;
	}

}
