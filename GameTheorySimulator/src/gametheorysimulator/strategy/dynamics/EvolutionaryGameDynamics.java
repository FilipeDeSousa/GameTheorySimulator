package gametheorysimulator.strategy.dynamics;

import gametheorysimulator.players.Player;
import gametheorysimulator.strategy.GameStrategy;

public abstract class EvolutionaryGameDynamics {
	//Non-static attributes
	Player self;
	GameStrategy initialStrategy, currentStrategy;
	
	//Abstract methods
	public abstract void refresh();
}
