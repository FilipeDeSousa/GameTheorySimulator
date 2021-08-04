package gametheorysimulator.game;

import java.util.ArrayList;
import java.util.List;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.SharedMedium;

public class PGG implements Game {
	private double synergeticFactor = 1;
	private double cost = 1;

	//Constructor
	public PGG() {
		Player.setGame(this);
	}
	
	@Override
	public double computePayoff(Player player) {
		//Simplified Shared Medium case
		if(player.getSpace() instanceof SharedMedium)
			return computePayoffSharedMediumCase(player);
		
		//Second variant
		double payoff = 0;
		List<Player> neighbours = player.getReachablePlayers();
		
		List<Player> neighboursUnionPlayer = new ArrayList<Player>();
		neighboursUnionPlayer.addAll(neighbours);
		neighboursUnionPlayer.add(player);
		
		for(Player player2: neighboursUnionPlayer)
			payoff += computeNodeCenterPGGPayoff(player, player2);
		
		return payoff;
	}

	private double computeNodeCenterPGGPayoff(Player player, Player player2) {
		boolean playerCooperates = player.getDecision();
		List<Player> player2Neighbours = player2.getReachablePlayers();
		int playerDegree = player.getReachablePlayers().size(), player2Degree = player2Neighbours.size();
		double result = synergeticFactor/((double)player2Degree+1.0);
		
		List<Player> neighbours2UnionPlayer2 = new ArrayList<Player>();
		neighbours2UnionPlayer2.addAll(player2Neighbours);
		neighbours2UnionPlayer2.add(player2);
		
		double sum = 0;
		for(Player player3: neighbours2UnionPlayer2)
			if(player3.getDecision()) {
				int player3Degree = player3.getReachablePlayers().size();
				sum += cost/((double)player3Degree+1.0);
			}
		
		result *= sum;
		
		if(playerCooperates)
			result -= cost/((double)playerDegree+1.0);
		
		return result;
	}

	private double computePayoffSharedMediumCase(Player player) {
		List<Player> otherPlayers = player.getReachablePlayers();
		double population = otherPlayers.size() + 1;
		double numberContributors = 0;
		//Neighbours
		for(Player player2: otherPlayers)
			if(player2.getDecision())
				numberContributors++;
		//Self
		boolean playerCooperates = player.getDecision();
		if(playerCooperates)
			numberContributors++;
		double result = synergeticFactor * numberContributors / population * cost;
		if(playerCooperates)
			return result - cost;
		return result;
	}

	@Override
	public void setParameter(String parameter, Double value) {
		switch(parameter) {
			case "synergeticFactor":
				synergeticFactor = value;
				break;
			case "cost":
				cost = value;
				break;
			default:
				System.out.println("ERROR: Parameter "+parameter+" doesn't exist in Game PGG");
				System.exit(0);
		}
	}
}
