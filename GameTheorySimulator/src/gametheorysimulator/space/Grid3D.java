package gametheorysimulator.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gametheorysimulator.players.Player;
import gametheorysimulator.space.position.Grid3DPosition;
import gametheorysimulator.space.position.SpacePosition;

public class Grid3D implements DynamicGameSpace {
	private int length, width, depth;
	private Reach reach;
	private List<Player> population;
	private Player[][][] grid;
	private Movement movement;
	
	//Constructor
	public Grid3D(int length, int width, int depth){
		this.length = length;
		this.width = width;
		this.depth = depth;
	}
	
	//Non-Static
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
	
	@Override
	public boolean hasMovement() {
		if(movement == Movement.RANDOM)
			return true;
		return false;
	}
	
	//Getters
	@Override
	public Reach getReach() {
		return reach;
	}
	
	@Override
	public List<SpacePosition> getPossibleMovePositions(SpacePosition position, int step) {
		List<SpacePosition> moves = new ArrayList<SpacePosition>(); 
		int[] coordenates = position.getPosition();
		
		for(int i=coordenates[0]-step; i<=coordenates[0]+step; i++) {
			if(i<0)
				continue;
			if(i>length-1)
				break;
			for(int j=coordenates[1]-step; j<=coordenates[1]+step; j++) {
				if(j<0)
					continue;
				if(j>width-1)
					break;
				for(int k=coordenates[2]-step; k<=coordenates[2]+step; k++) {
					if(k<0)
						continue;
					if(i == coordenates[0] && j == coordenates[1] && k == coordenates[2])
						continue;
					if(k>depth-1)
						break;
					
					Player player = grid[i][j][k];
					if(player == null)
						moves.add(new Grid3DPosition(i, j, k));
				}
			}
		}
		
		return moves;
	}
	
	@Override
	public void resetOccupation() {
		//Reset to null
		for(int i=0; i<grid.length; i++)
			for(int j=0; j<grid[i].length; j++)
				for(int k=0; j<grid[i][j].length; k++)
					grid[i][j][k] = null;
		
		for(Player player: population) {
			int[] coordenates = player.getPosition().getPosition();
			grid[coordenates[0]][coordenates[1]][coordenates[2]]=player;
		}
	}

	@Override
	public void resetOccupation(Player player, int[] oldCoordenates) {
		grid[oldCoordenates[0]][oldCoordenates[1]][oldCoordenates[2]] = null;
		int[]coordenates = player.getPosition().getPosition();
		grid[coordenates[0]][coordenates[1]][coordenates[2]] = player;
	}
	
	//Setters
	@Override
	public void setReach(Reach reach) {
		this.reach = reach;
	}
	
	@Override
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
}
