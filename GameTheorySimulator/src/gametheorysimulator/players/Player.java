package gametheorysimulator.players;

import java.util.List;
import java.util.Random;

import gametheorysimulator.game.Game;
import gametheorysimulator.space.DynamicGameSpace;
import gametheorysimulator.space.GameSpace;
import gametheorysimulator.space.position.SpacePosition;
import gametheorysimulator.strategy.AltruisticBehaviour;
import gametheorysimulator.strategy.GenerousTitForTat;
import gametheorysimulator.strategy.GameStrategy;
import gametheorysimulator.strategy.RandomBehaviour;
import gametheorysimulator.strategy.RationalBehaviour;
import gametheorysimulator.strategy.TitForTat;

public class Player {
	//Static
	private static int numberPlayers = 0x0;
	private static Game game;
	private static GameSpace space;
	
	private SpacePosition position;
	private int id;
	private GameStrategy strategy;
	private List<Player> reachablePlayers;
	//Last turn info
	private boolean decision;
	private double payoff;
	//Control flag
	private boolean hasDecided = false;
	
	//Constructors
	public Player(SpacePosition position) {
		this.position = position;
		id = generateId();
	}
	
	//Static
	public static void setGame(Game game) {
		Player.game = game;
	}
	
	public static void setSpace(GameSpace space) {
		Player.space = space;
	}
	
	//Non-static
	private int generateId() {
		int id = numberPlayers;
		++numberPlayers;
		return id;
	}

	public void decide() {
		if(hasDecided) {
			System.out.println("ERROR: Player "+Integer.toHexString(id)+" was in compute payoff turn.");
			System.exit(0);
		}
		decision = strategy.decide();
	}
	
	public double computePayoff() {
		payoff = game.computePayoff(this);
		return payoff;
	}
	
	public void move() {
		DynamicGameSpace dynamicSpace = (DynamicGameSpace) space;
		List<SpacePosition> possibleMoves = dynamicSpace.getPossibleMovePositions(position, 1);
		int numberPossibleMoves = possibleMoves.size();
		if(numberPossibleMoves<1)
			return;
		int[] oldCoordenates = position.getPosition();
		position = possibleMoves.get((new Random()).nextInt(numberPossibleMoves));
		((DynamicGameSpace)space).resetOccupation(this, oldCoordenates);
	}
	
	//Getters
	public int getId() {
		return id;
	}

	public String getLastIterationInfo() {
		String decision = "";
		if(this.decision)
			decision = "Cooperates";
		else
			decision = "Defects";
		return "Player: "+id+"; "+decision+"; Payoff: "+payoff;
	}
	
	public SpacePosition getPosition(){
		return position;
	}
	
	public List<Player> getReachablePlayers() {
		return reachablePlayers;
	}
	
	public Player getRandomReachablePlayer() {
		return reachablePlayers.get((new Random()).nextInt(reachablePlayers.size()));
	}

	public boolean getDecision() {
		return decision;
	}

	public double getPayoff() {
		return payoff;
	}

	public GameSpace getSpace(){
		return space;
	}
	
	public Game getGame() {
		return game;
	}
	
	public GameStrategy getStrategy() {
		return strategy;
	}
	
	//Setters
	public void setReachablePlayers() {
		reachablePlayers = space.reachablePlayers(this);
	}

	public GameStrategy setStrategy(String strategy) {
		switch(strategy) {
			case "Random":
				this.strategy = new RandomBehaviour();
				return this.strategy;
			case "Cooperative":
				this.strategy = new AltruisticBehaviour();
				return this.strategy;
			case "TitForTat":
				this.strategy = new TitForTat(this);
				return this.strategy;
			case "ForgivingTitForTat":
				this.strategy = new GenerousTitForTat(this);
				return this.strategy;
			case "RationalBehaviour":
				this.strategy = new RationalBehaviour(this);
				return this.strategy;
			default:
				System.out.println("ERROR: Strategy "+strategy+" unknown!");
				System.exit(0);
		}
		return null;
	}
	
	public void setStrategy(GameStrategy strategy) {
		this.strategy = strategy;
	}
}
