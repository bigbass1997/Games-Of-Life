package com.bigbass1997.gamesoflife.rules;

public class RuleSetExperimental implements RuleSet {

	@Override
	public boolean checkCellForLife(int neighbors, boolean isAlive) {
		if(isAlive){
			if(neighbors < 2) return false; //under population
			if(neighbors > 4) return false; //over population
			return true; //lives on
		}else if(!isAlive){
			if(neighbors >= 1 && neighbors <= 5) return true; //reproduction
		}
		
		return false;
	}
}
