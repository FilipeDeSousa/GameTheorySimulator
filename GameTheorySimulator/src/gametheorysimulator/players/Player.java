package gametheorysimulator.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gametheorysimulator.game.Game;
import gametheorysimulator.space.GameSpace;
import gametheorysimulator.space.position.SpacePosition;
import gametheorysimulator.strategy.GameStrategy;
import gametheorysimulator.strategy.RandomBehaviour;

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
	private Map<Player, Boolean> decisions = new HashMap<Player, Boolean>();
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

	public SpacePosition getPosition(){
		return position;
	}

	public int getId() {
		return id;
	}

	public String getLastIterationInfo() {
		String decisions = "";
		String cooperatesWith = "";
		String defectsWith = "";
		for(Map.Entry<Player, Boolean> decision: this.decisions.entrySet())
			if(decision.getValue())
				cooperatesWith += " "+Integer.toHexString(decision.getKey().id)+";";
			else
				defectsWith += " "+Integer.toHexString(decision.getKey().id)+";";
		if(cooperatesWith.length() > 0)
			decisions += "Cooperates:"+cooperatesWith;
		if(defectsWith.length() > 0)
			decisions += "Defects:"+defectsWith;
		return "Player: "+id+"; "+decisions+" Payoff: "+payoff;
	}

	public void decide() {
		if(hasDecided) {
			System.out.println("ERROR: Player "+Integer.toHexString(id)+" was in compute payoff turn.");
			System.exit(0);
		}
		reachablePlayers = space.reachablePlayers(this);
		for(Player player: reachablePlayers)
			decisions.put(player, strategy.decide(player));
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
			default:
				System.out.println("ERROR: Strategy "+strategy+" unknown.");
				System.exit(0);
		}
		return null;
	}

	public List<Player> getReachablePlayers() {
		return reachablePlayers;
	}

	public boolean getDecision(Player player) {
		return decisions.get(player);
	}

	public Map<Player, Boolean> getDecisions(boolean cooperates) {
		Map<Player, Boolean> result = new HashMap<Player, Boolean>();
		for(Map.Entry<Player, Boolean> decision: decisions.entrySet())
			if(decision.getValue() == cooperates)
				result.put(decision.getKey(), decision.getValue());
		return result;
	}

	public double getPayoff() {
		return payoff;
	}
}
