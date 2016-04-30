package com.bigbass1997.gamesoflife.rules;

import com.bigbass1997.gamesoflife.world.Cell2D;

public class Rule {
	
	private enum DirectionNN {
	    NORTH(0, 1),
	    EAST(1, 0),
	    SOUTH(0, -1),
	    WEST(-1, 0)
	    ;
		
	    final int dx;
	    final int dy;

	    DirectionNN(int dx, int dy) {
	        this.dx = dx;
	        this.dy = dy;
	    }
	}
	
	private enum DirectionNM {
		NORTHWEST(-1, 1),
	    NORTH(0, 1),
	    NORTHEAST(1, 1),
	    EAST(1, 0),
	    SOUTHEAST(1, -1),
	    SOUTH(0, -1),
	    SOUTHWEST(-1, -1),
	    WEST(-1, 0)
	    ;
		
	    final int dx;
	    final int dy;

	    DirectionNM(int dx, int dy) {
	        this.dx = dx;
	        this.dy = dy;
	    }
	}
	
	private enum Neighborhood {
		NM, //Moore
		NN  //von Neumann
	}
	
	public int range;						//R
	public int surviveMin, surviveMax;		//S
	public int birthMin, birthMax;			//B
	public Neighborhood hood;				//N
	
	public Rule(String formatRule){
		// R0,S0.1,B0.1,NM
		
		try {
			range = Integer.valueOf(formatRule.substring(formatRule.indexOf("R") + 1, formatRule.indexOf("R") + 2));
			surviveMin = Integer.valueOf(formatRule.substring(formatRule.indexOf("S") + 1, formatRule.indexOf("S") + 2));
			surviveMax = Integer.valueOf(formatRule.substring(formatRule.indexOf("S" + surviveMin + ".") + 3, formatRule.indexOf("S" + surviveMin + ".") + 4));
			birthMin = Integer.valueOf(formatRule.substring(formatRule.indexOf("B") + 1, formatRule.indexOf("B") + 2));
			birthMax = Integer.valueOf(formatRule.substring(formatRule.indexOf("B" + birthMin + ".") + 3, formatRule.indexOf("B" + birthMin + ".") + 4));
			hood = Neighborhood.valueOf(formatRule.substring(formatRule.indexOf("N"), formatRule.indexOf("N") + 2));
		} catch(Exception e) { //If any formatting is wrong or any other issue happens as a result...
			range = 0;
			surviveMin = 0;
			surviveMax = 0;
			birthMin = 0;
			birthMax = 0;
			hood = Neighborhood.NM;
			e.printStackTrace();
		}
	}
	
	public int countLiveNeighbors(Cell2D cell, Cell2D[][] grid, int cellsWide, int cellsHigh){
		int neighbors = 0;
		int x = cell.xIndex;
		int y = cell.yIndex;
		
		switch(hood){
		case NM:
			for (DirectionNM directionNM : DirectionNM.values()) {
				try {
			        if (grid[(x + directionNM.dx + cellsWide) % cellsWide][(y + directionNM.dy + cellsHigh) % cellsHigh].isAlive) {
			            neighbors++;
			        }
				} catch(Exception e) {}
		    }
			break;
		case NN:
			for (DirectionNN directionNN : DirectionNN.values()) {
				try {
			        if (grid[(x + directionNN.dx + cellsWide) % cellsWide][(y + directionNN.dy + cellsHigh) % cellsHigh].isAlive) {
			            neighbors++;
			        }
				} catch(Exception e) {}
		    }
			break;
		}
		
		return neighbors;
	}
	
	public boolean newStateOfCell(int liveNeighbors, boolean isAlive){
		if(isAlive){
			if(liveNeighbors >= surviveMin && liveNeighbors <= surviveMax){
				return true;
			} else {
				return false;
			}
		} else if(!isAlive && liveNeighbors >= birthMin && liveNeighbors <= birthMax){
			return true;
		}
		
		return false;
	}
}
