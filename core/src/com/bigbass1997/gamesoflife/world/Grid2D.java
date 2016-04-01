package com.bigbass1997.gamesoflife.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.gamesoflife.rules.RuleSet;

public class Grid2D {
	
	private enum Direction {
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

	    Direction(int dx, int dy) {
	        this.dx = dx;
	        this.dy = dy;
	    }
	}
	
	public RuleSet ruleSet;
	
	public Vector2 pos, size;
	public ArrayList<Cell2D> cells;
	public ArrayList<ArrayList<Cell2D>> generations;
	public int generationsCount = 0; //Temporary variable until the 'generations' log is implemented efficiently
	
	public ArrayList<Cell2D> tmpGen;
	
	public int cellsWide, cellsHigh;
	
	public Grid2D(RuleSet ruleSet, float x, float y, float width, float height, int cellsWide, int cellsHigh){
		this(ruleSet, new Vector2(x, y), new Vector2(width, height), cellsWide, cellsHigh);
	}
	
	public Grid2D(RuleSet ruleSet, Vector2 pos, Vector2 size, int cellsWide, int cellsHigh){
		this.ruleSet = ruleSet;
		this.pos = pos;
		this.size = size;
		this.cellsWide = cellsWide;
		this.cellsHigh = cellsHigh;
		
		cells = new ArrayList<Cell2D>();
		generations = new ArrayList<ArrayList<Cell2D>>();
		
		float xStep = size.x / cellsWide;
		float yStep = size.y / cellsHigh;
		for(int i = 0; i < cellsWide; i++){
			for(int j = 0; j < cellsHigh; j++){
				cells.add(new Cell2D(pos.x, pos.y, i, j, xStep, yStep));
			}
		}
		
		tmpGen = new ArrayList<Cell2D>();
	}
	
	public void render(ShapeRenderer sr){
		sr.begin(ShapeType.Line);
		for(Cell2D cell : cells){
			cell.render(sr);
		}
		sr.end();
	}
	
	public void update(float delta){
		for(Cell2D cell : cells){
			cell.update(delta);
		}
	}
	
	public void stepGeneration(){
		generationsCount++;
		Cell2D[][] orderedGrid = new Cell2D[cellsWide][cellsHigh];
		////Take "log" of last generation for history purposes
		//ArrayList<Cell2D> lastGen = new ArrayList<Cell2D>();
		for(Cell2D cell : cells){
			//lastGen.add(cell.clone());
			orderedGrid[cell.xIndex][cell.yIndex] = cell;
		}
		//generations.add(lastGen);
		
		//Step each cell to new list of cells (new generation)
		tmpGen.clear();
		
		float xStep = size.x / cellsWide;
		float yStep = size.y / cellsHigh;
		for(int i = 0; i < cellsWide; i++){
			for(int j = 0; j < cellsHigh; j++){
				Cell2D tmpCell = new Cell2D(pos.x, pos.y, i, j, xStep, yStep);
				int liveNeighbors = getLiveNeighbors(orderedGrid[i][j], orderedGrid);
				tmpCell.isAlive = ruleSet.checkCellForLife(liveNeighbors, orderedGrid[i][j].isAlive);
				tmpGen.add(tmpCell);
			}
		}
		
		cells = tmpGen;
	}
	
	public int getLiveNeighbors(Cell2D cell, Cell2D[][] grid){
		int neighbors = 0;
		int x = cell.xIndex;
		int y = cell.yIndex;
		
		for (Direction direction : Direction.values()) {
			try {
		        if (grid[(x + direction.dx + cellsWide) % cellsWide][(y + direction.dy + cellsHigh) % cellsHigh].isAlive) {
		            neighbors++;
		        }
			} catch(Exception e) {}
	    }
		
		return neighbors;
	}
	
	public void clean(){
		cells.clear();
		float xStep = size.x / cellsWide;
		float yStep = size.y / cellsHigh;
		for(int i = 0; i < cellsWide; i++){
			for(int j = 0; j < cellsHigh; j++){
				cells.add(new Cell2D(pos.x, pos.y, i, j, xStep, yStep));
			}
		}
	}
}
