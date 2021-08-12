package gametheorysimulator.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.position.Grid2DPosition;
import gametheorysimulator.space.position.SpacePosition;

public class Grid2D implements DynamicGameSpace {
	private int length, width;
	private List<Player> population;
	private Player[][] grid;
	private Reach reach;
	private Movement movement;
	
	//Constructor
	public Grid2D(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new Player[length][width];
	}

	//Non-Static
	@Override
	public List<Player> populate(int n) {
		Player.setSpace(this);
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
					System.out.print(Integer.toHexString(cell.getId())+" ");
				else
					System.out.print("· ");
			System.out.println();
		}
	}

	@Override
	public List<Player> reachablePlayers(Player player) {
		SpacePosition position = player.getPosition();
		int[] coordenates = position.getPosition();
		
		List<Player> reachablePlayers = new ArrayList<Player>();
		switch(reach) {
			case IMMEDIATE_NEIGHBORS:
				for(int i=coordenates[0]-1; i<=coordenates[0]+1; i++) {
					if(i<0)
						continue;
					if(i>length-1)
						break;
					for(int j=coordenates[1]-1; j<=coordenates[1]+1; j++) {
						if(j<0)
							continue;
						if(i == coordenates[0] && j == coordenates[1])
							continue;
						if(j>width-1)
							break;
						Player player2 = grid[i][j];
						if(player2 != null)
							reachablePlayers.add(player2);
					}
				}
				break;
			default:
				System.out.println("Rule to determine reachable players isn't set!");
				System.exit(0);
		}
		
		return reachablePlayers;
	}

	@Override
	public boolean hasMovement() {
		if(movement == Movement.RANDOM)
			return true;
		return false;
	}

	@Override
	public void resetOccupation() {
		//Reset to null
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[i].length; j++)
				grid[i][j] = null;
		
		for(Player player: population) {
			int[] coordenates = player.getPosition().getPosition();
			grid[coordenates[0]][coordenates[1]]=player;
		}
	}
	
	@Override
	public void resetOccupation(Player player, int[] oldCoordenates) {
		grid[oldCoordenates[0]][oldCoordenates[1]] = null;
		int[]coordenates = player.getPosition().getPosition();
		grid[coordenates[0]][coordenates[1]] = player;
	}
	
	//Getters
	@Override
	public Reach getReach() {
		return reach;
	}
	
	@Override
	public List<SpacePosition> getPossibleMovePositions(SpacePosition initialPosition, int step) {
		List<SpacePosition> moves = new ArrayList<SpacePosition>(); 
		int[] coordenates = initialPosition.getPosition();
		
		for(int i=coordenates[0]-step; i<=coordenates[0]+step; i++) {
			if(i<0)
				continue;
			if(i>length-1)
				break;
			for(int j=coordenates[1]-step; j<=coordenates[1]+step; j++) {
				if(j<0)
					continue;
				if(i == coordenates[0] && j == coordenates[1])
					continue;
				if(j>width-1)
					break;
				Player player = grid[i][j];
				if(player == null)
					moves.add(new Grid2DPosition(i, j));
			}
		}
		
		return moves;
	}
	
	//Setters
	@Override
	public void setReach(Reach reach) {
		this.reach = reach;
	}
	
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
}
