package com.bigbass1997.gamesoflife.graphics;

import com.badlogic.gdx.math.Vector3;

public class VectorColor {
	public Vector3 vec;
	public int color;
	
	public VectorColor(Vector3 vec, int color){
		this.vec = vec;
		this.color = color;
	}
	
	public VectorColor(float x, float y, float z, int color){
		this.vec = new Vector3(x, y, z);
		this.color = color;
	}
}
