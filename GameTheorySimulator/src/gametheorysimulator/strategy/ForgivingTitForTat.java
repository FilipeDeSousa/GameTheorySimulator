package gametheorysimulator.strategy;

import java.util.List;

import gametheorysimulator.players.Player;

public class ForgivingTitForTat extends TitForTat {
	RandomBehaviour forgivingBehaviour;
	
	//Constructor
	public ForgivingTitForTat(Player player) {
		super(player);
		forgivingBehaviour = new RandomBehaviour();
	}

	@Override
	public boolean decide() {
		List<Player> neighbours = self.getReachablePlayers();
		if(neighbours.size() == 0) {
			hasInteracted = false;
			return false;
		}
		if(!hasInteracted) {
			hasInteracted = true;
			return initialBehaviour.decide();
		}
		
		//Imitates neighbours' behaviour
		for(Player neighbour: neighbours)
			if(neighbour.getDecision())
				return true;
		
		return forgivingBehaviour.decide();
	}
}
