package gametheorysimulator.game;

import gametheorysimulator.players.Player;

public interface Game {
	void setNumberPlayers(int n);
	double computePayoff(Player player);

}
