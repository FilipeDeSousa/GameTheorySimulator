package gametheorysimulator.players;

import java.util.List;
import java.util.Random;

import gametheorysimulator.game.Game;
import gametheorysimulator.space.GameSpace;
import gametheorysimulator.space.position.SpacePosition;
import gametheorysimulator.strategy.CooperativeBehaviour;
import gametheorysimulator.strategy.GameStrategy;
import gametheorysimulator.strategy.RandomBehaviour;
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

	//Others
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
		reachablePlayers = space.reachablePlayers(this);
		decision = strategy.decide();
	}
	
	public double computePayoff() {
		payoff = game.computePayoff(this);
		return payoff;
	}

	public GameStrategy setStrategy(String strategy) {
		switch(strategy) {
			case "Random":
				this.strategy = new RandomBehaviour();
				return this.strategy;
			case "Cooperative":
				this.strategy = new CooperativeBehaviour();
				return this.strategy;
			case "TitForTat":
				this.strategy = new TitForTat(this);
				return this.strategy;
			default:
				System.out.println("ERROR: Strategy "+strategy+" unknown.");
				System.exit(0);
		}
		return null;
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
}
