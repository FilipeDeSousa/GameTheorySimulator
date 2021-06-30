package gametheorysimulator.players;

import gametheorysimulator.space.position.SpacePosition;

public class Player {
	private SpacePosition position;
	
	public Player(SpacePosition position) {
		this.position = position;
	}
	
	public SpacePosition getPosition(){
		return position;
	}
}
