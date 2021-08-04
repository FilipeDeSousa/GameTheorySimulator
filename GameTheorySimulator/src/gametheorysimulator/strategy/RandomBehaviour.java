package gametheorysimulator.strategy;

import java.util.Random;

public class RandomBehaviour implements GameStrategy {
	double probCooperation = .5;
	
	//Constructors
	public RandomBehaviour() {
		
	}
	
	public RandomBehaviour(double d) {
		if(d<0 || d>1) {
			System.out.println("ERROR: Invalid probability of cooperation value "+d+"!");
		}
		probCooperation = d;
	}
	
	@Override
	public boolean decide() {
		if((new Random()).nextDouble()<probCooperation)
			return true;
		return false;
	}

}
