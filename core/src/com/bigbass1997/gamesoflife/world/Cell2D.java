package com.bigbass1997.gamesoflife.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Cell2D extends Cell {
	
	public float xOffset, yOffset, width, height;
	public int xIndex, yIndex;
	public int[] colors;
	
	/**
	 * To be used as only a reference. Assigning a new value, outside of this class' update(...) method, will not do anything.
	 * 
	 * @see Cell2D
	 */
	private Vector2 posRef;
	
	public boolean isAlive = false;
	
	public Cell2D(float xOffset, float yOffset, int xIndex, int yIndex, float width, float height){
		this(xOffset, yOffset, xIndex, yIndex, width, height, new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF});
	}
	
	public Cell2D(float xOffset, float yOffset, int xIndex, int yIndex, float width, float height, int[] colors){
		super();
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.width = width;
		this.height = height;
		this.colors = colors;
		
		posRef = new Vector2(0,0);
	}
	
	public void render(ShapeRenderer sr){
		sr.rect(xOffset + (xIndex * width), yOffset + (yIndex * height), width, height);
		
		if(isAlive){
			ShapeType type = sr.getCurrentType();
			sr.end();
			sr.begin(ShapeType.Filled);
			sr.rect(xOffset + (xIndex * width), yOffset + (yIndex * height), width, height);
			sr.end();
			sr.begin(type);
		}
	}
	
	public void update(float delta){
		posRef.x = xOffset + (xIndex * width);
		posRef.y = yOffset + (yIndex * height);
		
		Input input = Gdx.input;
		float mx = input.getX();
		float my = -input.getY() + Gdx.graphics.getHeight();
		
		if(input.isKeyJustPressed(Keys.P)){
			if(mx > posRef.x && mx < posRef.x + width && my > posRef.y && my < posRef.y + height){
				if(isAlive){
					isAlive = false;
				}else if(!isAlive){
					isAlive = true;
				}
			}
		}
	}
	
	public Cell2D clone(){
		Cell2D newCell = new Cell2D(xOffset, yOffset, xIndex, yIndex, width, height, colors);
		newCell.update(0);
		newCell.isAlive = this.isAlive;
		
		return newCell;
	}
}
