package gametheorysimulator.strategy;

import gametheorysimulator.players.Player;

public interface GameStrategy {
	//true-> cooperate; false-> defect
	public boolean decide(Player player);
}
