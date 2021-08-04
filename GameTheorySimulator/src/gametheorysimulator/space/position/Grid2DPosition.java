package gametheorysimulator.space.position;

public class Grid2DPosition implements SpacePosition {
	private int x, y;
	
	//Constructors
	public Grid2DPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int[] getPosition() {
		int[] position = {x, y};
		return position;
	}
	
}
