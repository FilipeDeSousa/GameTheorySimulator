package gametheorysimulator.game;

import java.util.List;

import gametheorysimulator.players.Player;

public interface Game {
	double computePayoff(Player player);
	void setParameter(String parameter, Double value);
	void setPopulation(List<Player> players);
	List<Player> getPopulation();
}
