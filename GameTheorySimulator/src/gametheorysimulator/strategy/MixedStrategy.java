package gametheorysimulator.strategy;

import java.util.Random;

public class MixedStrategy implements GameStrategy {
	private static GameStrategy[] strategies;
	private double[] probabilities;
	
	//Constructor
	public MixedStrategy(double[] probabilities) {
		double total = 0;
		for(double probability: probabilities)
			total+=probability;
		if(total != 1) {//Check if probability vector is valid
			System.out.println("Invalid probability vector");
			System.exit(0);
		}
		this.probabilities = probabilities;
	}
	
	//Static
	public static void setStrategies(GameStrategy[] strategies) {
		MixedStrategy.strategies = strategies;
	}
	
	@Override
	public boolean decide() {
		Random r = new Random();
		double d = r.nextDouble();
		double auxProb = 0;
		
		for(int i=0;;i++) {
			auxProb+=probabilities[i];
			if(d <= auxProb)
				return strategies[i].decide();
		}		
	}

}
