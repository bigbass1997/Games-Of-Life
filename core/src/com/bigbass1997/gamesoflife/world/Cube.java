package com.bigbass1997.gamesoflife.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Cube extends Object {
	
	public Cube(Vector3 pos, float size, int color){
		this(pos.x, pos.y, pos.z, size, color);
	}
	
	public Cube(float x, float y, float z, float size, int color){
		this.pos = new Vector3(x, y, z);
		this.size = new Vector3(size, size, size);
		this.color = color;
		
		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(size, size, size, 
				new Material(ColorAttribute.createDiffuse(new Color(color))),
				Usage.Position | Usage.Normal);
		
		modelInstance = new ModelInstance(model);
		
		this.setPos(pos);
	}
}
