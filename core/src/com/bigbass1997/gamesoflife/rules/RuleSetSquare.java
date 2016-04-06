package com.bigbass1997.gamesoflife.rules;

public class RuleSetSquare implements RuleSet {

	@Override
	public boolean checkCellForLife(int neighbors, boolean isAlive) {
		if(isAlive){
			if(neighbors < 1 || neighbors > 3) return false; //under-population or over-population
			return true; //lives on
		}else if(!isAlive && neighbors >= 1 && neighbors <= 3){
			return true; //reproduction
		}
		
		return false;
	}

}
