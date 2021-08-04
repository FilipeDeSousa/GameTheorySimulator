package gametheorysimulator.game;

import gametheorysimulator.players.Player;

public interface Game {
	double computePayoff(Player player);
	void setParameter(String parameter, Double value);
}
