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

public class Cylinder extends Object {
	
	public int divisions = 0;
	public int primitiveType;
	
	public Cylinder(Vector3 pos, Vector3 size, int divisions, int color, int primitiveType){
		this(pos.x, pos.y, pos.z, size.x, size.y, size.z, divisions, color, primitiveType);
	}
	
	public Cylinder(float x, float y, float z, float sx, float sy, float sz, int divisions, int color, int primitiveType){
		this.pos = new Vector3((x/2) + (sy/4), y/2, z/2);
		this.size = new Vector3(sx, sy/2, sz);
		this.divisions = divisions;
		this.color = color;
		this.primitiveType = primitiveType;
		
		model = createModel(divisions);
		modelInstance = new ModelInstance(model);
		modelInstance.transform.rotate(Vector3.Z, 90f);
		
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
		model = createModel(newDivisions);
		modelInstance = new ModelInstance(model);
		modelInstance.transform.rotate(Vector3.Z, 90f);
		this.setPos(pos);
	}
	
	public void setRadius(float radius){
		size.set(radius, size.y, radius);
		modelInstance = new ModelInstance(model);
		modelInstance.transform.rotate(Vector3.Z, 90f);
		this.setPos(pos);
	}
	
	private Model createModel(int divisions){
		ModelBuilder modelBuilder = new ModelBuilder();
		return modelBuilder.createCylinder(size.x, size.y, size.z,
				divisions, primitiveType,
				new Material(ColorAttribute.createDiffuse(new Color(color))),
				Usage.Position | Usage.Normal);
	}
}
