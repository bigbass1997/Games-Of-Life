package com.bigbass1997.gamesoflife.rules;

public interface RuleSet {
	
	public boolean checkCellForLife(int neighbors, boolean isAlive);
}
