package gametheorysimulator.game;

import java.util.List;
import java.util.Map;

import gametheorysimulator.players.Player;

public class PGG implements Game {
	private float synergeticFactor = 1;
	private float cost = 1;
	private int numberPlayers;

	//Constructor
	public PGG() {
		Player.setGame(this);
	}
	
	@Override
	public double computePayoff(Player player) {
		List<Player> reachablePlayers = player.getReachablePlayers();
		float degreeSelf = reachablePlayers.size();
		Map<Player, Boolean> selfCooperations = player.getDecisions(true);
		float selfCooperationDegree = selfCooperations.size();
		double payoff = synergeticFactor / (degreeSelf + 1.0);
		double sum = 0;
		//Neighbors contributions
		for(Player player2: reachablePlayers) {
			boolean player2Cooperates = player2.getDecision(player);
			if(!player2Cooperates)
				continue;
			Map<Player, Boolean> player2Cooperations = player2.getDecisions(true);
			float cooperationDegreePlayer2 = player2Cooperations.size();
			sum += cost/(cooperationDegreePlayer2+1.0);
			if(player.getDecision(player2))
				sum -= cost / (selfCooperationDegree+1);
		}
		//Self contribution
		//sum += ;
		payoff *= sum;
		
		return payoff;
	}

	@Override
	public void setNumberPlayers(int n) {
		numberPlayers = n;
	}
}
