package gametheorysimulator.strategy;

import java.util.Random;

import gametheorysimulator.players.Player;

public class RandomBehaviour implements GameStrategy {

	@Override
	public boolean decide(Player player) {
		return (new Random().nextBoolean());
	}

}
