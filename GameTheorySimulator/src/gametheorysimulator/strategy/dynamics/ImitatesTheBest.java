package gametheorysimulator.strategy.dynamics;

import java.util.ArrayList;
import java.util.List;

import gametheorysimulator.players.Player;
import gametheorysimulator.strategy.GameStrategy;

public class ImitatesTheBest extends EvolutionaryGameDynamics{
	enum PlayersConsidered{
		NEIGHBOURS, ALL
	}
	
	//Static Attributes
	PlayersConsidered playersConsidered = PlayersConsidered.NEIGHBOURS;//NEIGHBOURS by default
	
	//Constructor
	public ImitatesTheBest(Player player) {
		self = player;
	}
	
	//Non-static methods
	public void refresh(){
		currentStrategy = getBestStrategy();
		self.setStrategy(currentStrategy);
	}
	
	private GameStrategy getBestStrategy() {
		List<Player> playersConsidered2;
		if(playersConsidered == PlayersConsidered.NEIGHBOURS) {
			List<Player> reachablePlayers = self.getReachablePlayers();
			playersConsidered2 = new ArrayList<Player>();
			playersConsidered2.addAll(reachablePlayers);
			playersConsidered2.add(self);
		}else
			playersConsidered2 = self.getGame().getPopulation();
		
		double bestPayoff = Double.MIN_VALUE;
		GameStrategy best = null;//In the sense of being the one that gave the largest payoff in the last round
		//TODO Mode where memory is utilized instead of just the last round
		//TODO Mode where the strategy with the largest average between all players is utilized
		for(Player player: playersConsidered2) {
			double payoff = player.getPayoff();
			if(payoff > bestPayoff) {
				bestPayoff = payoff;
				best = player.getStrategy();
			}
		}
		
		return best;
	}
}
