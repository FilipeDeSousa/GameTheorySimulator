package gametheorysimulator.strategy;

import java.util.List;

import gametheorysimulator.players.Player;

public class TitForTat implements GameStrategy {
	Player self;
	RandomBehaviour initialBehaviour;
	boolean hasInteracted = false; //Has interacted in the previous one
	
	//Constructor
	public TitForTat(Player player){
		self = player;
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
		
		return false;
	}

}
