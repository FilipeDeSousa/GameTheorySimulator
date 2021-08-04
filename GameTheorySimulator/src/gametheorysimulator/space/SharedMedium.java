package gametheorysimulator.space;

import java.util.ArrayList;
import java.util.List;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.position.SpacePosition;

public class SharedMedium implements GameSpace {
	private List<Player> population;
	private Reach reach;
	
	@Override
	public List<Player> populate(int n) {
		Player.setSpace(this);
		
		population = new ArrayList<Player>();
		for(int i=0; i< n; i++){
			SpacePosition position = null;
			Player player = new Player(position);
			population.add(player);
		}
		
		return population;
	}

	@Override
	public List<Player> reachablePlayers(Player player) {
		return population;
	}

	//Getters
	public Reach getReach() {
		return reach;
	}
	
	//Setters
	@Override
	public void setReach(Reach reach) {
		//Reach is always total on Shared Medium
		this.reach = Reach.TOTAL;
	}
}
