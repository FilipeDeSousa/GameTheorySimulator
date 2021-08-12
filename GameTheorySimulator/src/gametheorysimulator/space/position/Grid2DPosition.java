package gametheorysimulator.space.position;

public class Grid2DPosition implements SpacePosition {
	private int x, y;
	
	//Constructors
	public Grid2DPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	//Non-Static
	@Override
	public String toString() {
		return "("+x+", "+y+")";
	}
	
	//Getters
	@Override
	public int[] getPosition() {
		int[] position = {x, y};
		return position;
	}
	
}
