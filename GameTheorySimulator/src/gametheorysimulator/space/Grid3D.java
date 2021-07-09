package gametheorysimulator.space;

import java.util.ArrayList;
import java.util.List;

import gametheorysimulator.game.Game;
import gametheorysimulator.players.Player;

public class Grid3D implements GameSpace {
	private int length, width, depth;
	
	//Constructor
	public Grid3D(int length, int width, int depth){
		this.length = length;
		this.width = width;
		this.depth = depth;
	}
	
	@Override
	public List<Player> populate(int n, Game game) {
		
		
		ArrayList<Player> population = new ArrayList<Player>();
		return population;
	}

	@Override
	public List<Player> reachablePlayers(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReach(Reach reach) {
		// TODO Auto-generated method stub
		
	}

}
