package gametheorysimulator.space.position;

public class Grid3DPosition implements SpacePosition {
	private int x, y, z;
	
	//Constructors
	public Grid3DPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int[] getPosition() {
		int[] position = {x, y, z};
		return position;
	}

}
