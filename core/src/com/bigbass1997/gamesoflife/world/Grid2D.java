package com.bigbass1997.gamesoflife.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
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
	
	public boolean isDrawingGrid = true;
	
	private int cellWidth, cellHeight;
	
	private int cellsWide, cellsHigh;
	
	public Grid2D(RuleSet ruleSet, float x, float y, float width, float height, int cellWidth, int cellHeight){
		this(ruleSet, new Vector2(x, y), new Vector2(width, height), cellWidth, cellHeight);
	}
	
	public Grid2D(RuleSet ruleSet, Vector2 pos, Vector2 size, int cellWidth, int cellHeight){
		this.ruleSet = ruleSet;
		this.pos = pos;
		this.size = size;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		cells = new ArrayList<Cell2D>();
		generations = new ArrayList<ArrayList<Cell2D>>();
		
		cellsWide = (int) (size.x / cellWidth);
		cellsHigh = (int) (size.y / cellHeight);
		
		for(int i = 0; i < cellsWide; i++){
			for(int j = 0; j < cellsHigh; j++){
				cells.add(new Cell2D(pos.x, pos.y, i, j, this.cellWidth, this.cellHeight));
			}
		}
		
		tmpGen = new ArrayList<Cell2D>();
	}
	
	public void render(ShapeRenderer sr){
		sr.begin(ShapeType.Line);
		
		if(isDrawingGrid){
			drawGrid(sr);
		}

		sr.rect(pos.x, pos.y, cellsWide * cellWidth, cellsHigh * cellHeight);
		
		for(Cell2D cell : cells){
			cell.render(sr);
		}
		sr.end();
	}

	public void drawGrid(ShapeRenderer sr){
		Color c1 = new Color(0x402020FF);
		Color c2 = new Color(0x402020FF);
		for(int i = 1; i < cellsWide + 1; i++){
			sr.line(pos.x + (i * cellWidth), pos.y, pos.x + (i * cellWidth), pos.y + (cellsHigh * cellHeight), c1, c2);
		}
		for(int j = 1; j < cellsHigh + 1; j++){
			sr.line(pos.x, pos.y + (j * cellHeight), pos.x + (cellsWide * cellWidth), pos.y + (j * cellHeight), c1, c2);
		}
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
		
		for(int i = 0; i < cellsWide; i++){
			for(int j = 0; j < cellsHigh; j++){
				Cell2D tmpCell = new Cell2D(pos.x, pos.y, i, j, cellWidth, cellHeight);
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
	
	public void modCellWidth(int dVal){
		if((cellWidth + dVal) > 0){
			cellWidth += dVal; //Change cellWidth
			cellsWide = (int) (size.x / cellWidth); //Update grid size
		}
	}
	
	public void modCellHeight(int dVal){
		if((cellHeight + dVal) > 0){
			cellHeight += dVal; //Change cellHeight
			cellsHigh = (int) (size.y / cellHeight); //Update grid size
		}
	}
	
	public float getCellWidth(){
		return cellWidth;
	}
	public float getCellHeight(){
		return cellHeight;
	}
	
	public int getCellsWide(){
		return cellsWide;
	}
	public int getCellsHigh(){
		return cellsHigh;
	}
	
	public void clean(){
		cells.clear();
		for(int i = 0; i < cellsWide; i++){
			for(int j = 0; j < cellsHigh; j++){
				cells.add(new Cell2D(pos.x, pos.y, i, j, cellWidth, cellHeight));
			}
		}
		generationsCount = 0;
	}
}
