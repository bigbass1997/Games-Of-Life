package com.bigbass1997.gamesoflife.rules;

public class RuleSetExperimental implements RuleSet {

	@Override
	public boolean checkCellForLife(int neighbors, boolean isAlive) {
		if(isAlive){
			if(neighbors < 2 || neighbors > 4){
				return false; //under-population or over-population
			}else{
				return true; //lives on
			}
		}else if(!isAlive && neighbors >= 1 && neighbors <= 5){
			return true; //reproduction
		}
		
		return false;
	}
}
