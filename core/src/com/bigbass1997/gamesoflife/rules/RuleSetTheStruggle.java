package com.bigbass1997.gamesoflife.rules;

public class RuleSetTheStruggle implements RuleSet {

	@Override
	public boolean checkCellForLife(int neighbors, boolean isAlive) {
		if(isAlive){
			if(neighbors < 2 || neighbors > 3){
				return false; //under-population or over-population
			}else{
				return true; //lives on
			}
		}else if(!isAlive && neighbors > 2){
			return true; //reproduction
		}
		
		return false;
	}
}
