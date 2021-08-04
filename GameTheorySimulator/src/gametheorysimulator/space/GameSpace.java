package gametheorysimulator.space;

import java.util.List;

import gametheorysimulator.players.Player;

public interface GameSpace {
	enum Reach{
		IMMEDIATE_NEIGHBORS, TOTAL
	}
	
	public List<Player> populate(int n);
	public List<Player> reachablePlayers(Player player);
	public void setReach(Reach reach);
	public Reach getReach();
}
