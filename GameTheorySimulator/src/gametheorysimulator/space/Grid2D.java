package gametheorysimulator.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.position.SpacePosition;

public class Grid2D implements GameSpace {
	enum SpaceOptions {
		GRID_2D
	};
	
	private int length, width;
	private List<Player> population;
	private Player[][] grid;
	
	//Constructor
	public Grid2D(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new Player[length][width];
	}

	@Override
	public List<Player> populate(int n) {
		int capability = length*width;
		if(n > capability){
			System.out.println(n+" exceeds space capability of "+capability+". Population set to "+capability);
			n=capability;
		}
		
		population = new ArrayList<Player>();
		for(int i=0; i< n; i++){
			Random random = new Random();
			int x = random.nextInt(length);
			int y = random.nextInt(width);
			SpacePosition position = new Grid2DPosition(x, y);
			
			if(grid[x][y] == null){
				Player player = new Player(position);
				population.add(player);
				grid[x][y] = player;
			}else
				i--;
		}
		
		printGridOccupation();//Test
		
		return population;
	}
	
	public void printGridOccupation(){
		for(Player[] row: grid){
			for(Player cell: row)
				if(cell != null)
					System.out.print("× ");
				else
					System.out.print("· ");
			System.out.println();
		}
	}

}
