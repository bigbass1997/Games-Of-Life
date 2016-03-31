package com.bigbass1997.gamesoflife.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

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
	
	public Vector2 pos, size;
	public ArrayList<Cell2D> cells;
	public ArrayList<ArrayList<Cell2D>> generations;
	
	public ArrayList<Cell2D> tmpGen;
	
	public int cellsWide, cellsHigh;
	
	public Grid2D(float x, float y, float width, float height, int cellsWide, int cellsHigh){
		this(new Vector2(x, y), new Vector2(width, height), cellsWide, cellsHigh);
	}
	
	public Grid2D(Vector2 pos, Vector2 size, int cellsWide, int cellsHigh){
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
				tmpCell.isAlive = checkCellForLife(liveNeighbors, orderedGrid[i][j].isAlive);
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
	
	/**
	 * 
	 *  N N N
	 *  N C N
	 *  N N N
	 *  
	 *  1 2 3
	 *  4 C 5
	 *  6 7 8
	 * 
	 * @param neighbors
	 * @return
	 */
	public boolean checkCellForLife(int neighbors, boolean isAlive){
		if(isAlive){
			if(neighbors < 2) return false; //under population
			if(neighbors > 3) return false; //over population
			if(neighbors == 2 || neighbors == 3) return true; //lives on
		}else if(!isAlive){
			if(neighbors == 3) return true; //reproduction
		}
		
		return false;
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
