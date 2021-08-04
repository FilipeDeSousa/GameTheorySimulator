package gametheorysimulator.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.position.Grid2DPosition;
import gametheorysimulator.space.position.Grid3DPosition;
import gametheorysimulator.space.position.SpacePosition;

public class Grid3D implements GameSpace {
	private int length, width, depth;
	private Reach reach;
	private List<Player> population;
	private Player[][][] grid;
	
	//Constructor
	public Grid3D(int length, int width, int depth){
		this.length = length;
		this.width = width;
		this.depth = depth;
	}
	
	//Getters
	@Override
	public Reach getReach() {
		return reach;
	}
	
	//Setters
	@Override
	public void setReach(Reach reach) {
		this.reach = reach;
	}

	@Override
	public List<Player> reachablePlayers(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> populate(int n) {
		Player.setSpace(this);
		int capability = length*width*depth;
		if(n > capability){
			System.out.println(n+" exceeds space capability of "+capability+". Population set to "+capability);
			n=capability;
		}
		
		population = new ArrayList<Player>();
		for(int i=0; i< n; i++){
			Random random = new Random();
			int x = random.nextInt(length);
			int y = random.nextInt(width);
			int z = random.nextInt(depth);
			SpacePosition position = new Grid3DPosition(x, y, z);
			
			if(grid[x][y][z] == null){
				Player player = new Player(position);
				population.add(player);
				grid[x][y][z] = player;
			}else
				i--;
		}

		return population;
	}
}
