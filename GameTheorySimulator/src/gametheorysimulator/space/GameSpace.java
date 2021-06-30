package gametheorysimulator.space;

import java.util.List;

import gametheorysimulator.players.Player;

public interface GameSpace {
	public List<Player> populate(int n);
}
