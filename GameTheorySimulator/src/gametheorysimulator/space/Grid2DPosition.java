package gametheorysimulator.space;

import gametheorysimulator.space.position.SpacePosition;

public class Grid2DPosition implements SpacePosition {
	private int x, y;
	
	public Grid2DPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
