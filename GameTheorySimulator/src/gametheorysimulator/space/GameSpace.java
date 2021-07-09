package gametheorysimulator.space;

import java.util.List;

import gametheorysimulator.game.Game;
import gametheorysimulator.players.Player;

public interface GameSpace {
	enum Reach{
		IMMEDIATE_NEIGHBORS
	}
	
	public List<Player> populate(int n, Game game);
	public List<Player> reachablePlayers(Player player);
	public void setReach(Reach reach);
}
