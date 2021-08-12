//Uses the last iteration players decision to compute expected payoffs
package gametheorysimulator.strategy;

import gametheorysimulator.players.Player;

public class RationalBehaviour implements GameStrategy {
	enum DecisionState{
		DECIDING, COMPUTING_COOPERATION, COMPUTING_DEFECTION
	}
	
	private DecisionState state = DecisionState.DECIDING;
	private Player self;
	
	//Constructor
	public RationalBehaviour(Player player){
		self = player;
	}
	
	//Non-Static
	@Override
	public boolean decide() {
		if(state == DecisionState.COMPUTING_DEFECTION)
			return false;
		if(state == DecisionState.COMPUTING_COOPERATION)
			return true;
		
		if(self.getReachablePlayers().size() == 0)
			return false;
		double expectedCooperationPayoff = computeExpectedCooperationPayoff();
		double expectedDefectionPayoff = computeExpectedDefectionPayoff();
		state = DecisionState.DECIDING;
		if(expectedCooperationPayoff>=expectedDefectionPayoff)
			return true;
		return false;
	}

	private double computeExpectedDefectionPayoff() {
		state = DecisionState.COMPUTING_DEFECTION;
		self.decide();
		return self.computePayoff();
	}

	private double computeExpectedCooperationPayoff() {
		state = DecisionState.COMPUTING_COOPERATION;
		self.decide();
		return self.computePayoff();
	}

}
