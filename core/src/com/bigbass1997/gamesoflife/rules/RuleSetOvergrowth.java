package com.bigbass1997.gamesoflife.rules;

public class RuleSetOvergrowth implements RuleSet {

	@Override
	public boolean checkCellForLife(int neighbors, boolean isAlive) {
		if(isAlive){
			if(neighbors < 2 || neighbors > 5) return false; //under-population or over-population
			if(neighbors == 2 || neighbors == 3 || neighbors == 4) return true; //lives on
		}else if(!isAlive && neighbors >= 3 && neighbors <= 5){
			return true; //reproduction
		}
		
		return false;
	}
}
