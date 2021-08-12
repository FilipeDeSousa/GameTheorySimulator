package gametheorysimulator.space;

import java.util.List;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.position.SpacePosition;

public interface DynamicGameSpace extends GameSpace {
	enum Movement{
		RANDOM
	}
	
	public void setMovement(Movement movement);
	public List<SpacePosition> getPossibleMovePositions(SpacePosition position, int step);
	public void resetOccupation();
	public void resetOccupation(Player player, int[] oldCoordenates);
}
