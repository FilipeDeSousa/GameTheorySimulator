package gametheorysimulator.players;

import gametheorysimulator.space.position.SpacePosition;

public class Player {
	static int numberPlayers = 0x0;
	
	private SpacePosition position;
	private int id;
	
	public Player(SpacePosition position) {
		this.position = position;
		id = generateId();
	}
	
	private int generateId() {
		int id = numberPlayers;
		++numberPlayers;
		return id;
	}

	public SpacePosition getPosition(){
		return position;
	}

	public int getId() {
		return id;
	}
}
