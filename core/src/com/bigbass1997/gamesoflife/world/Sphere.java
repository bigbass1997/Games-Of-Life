package com.bigbass1997.gamesoflife.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Sphere extends Object {
	
	public int divisions = 0;
	public int primitiveType;
	
	public Sphere(Vector3 pos, float radius, int divisions, int color, int primitiveType){
		this(pos.x, pos.y, pos.z, radius, divisions, color, primitiveType);
	}
	
	public Sphere(float x, float y, float z, float radius, int divisions, int color, int primitiveType){
		this.pos = new Vector3(x, y, z);
		this.size = new Vector3(radius, radius, radius);
		this.divisions = divisions;
		this.color = color;
		this.primitiveType = primitiveType;
		
		model = createModel(radius, divisions);
		modelInstance = new ModelInstance(model);
		
		this.setPos(pos);
	}

	@Override
	public void update(float delta){
		Input input = Gdx.input;

		if(input.isKeyPressed(Keys.UP)){
			divisions += 1;
			if(divisions > 180) divisions = 180;
			setDivisions(divisions);
		}else if(input.isKeyPressed(Keys.DOWN)){
			divisions -= 1;
			if(divisions < 2) divisions = 2;
			setDivisions(divisions);
		}
	}
	
	public void setDivisions(int newDivisions){
		model = createModel(size.x, newDivisions);
		modelInstance = new ModelInstance(model);
		this.setPos(pos);
	}
	
	private Model createModel(float radius, int divisions){
		ModelBuilder modelBuilder = new ModelBuilder();
		return modelBuilder.createSphere(radius, radius, radius,
				divisions, divisions, primitiveType,
				new Material(ColorAttribute.createDiffuse(new Color(color))),
				Usage.Position | Usage.Normal);
	}
}
