package com.bigbass1997.gamesoflife.world;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class World {
	
	public Hashtable<String, Object> objects;
	
	public Camera cam;
	private final Vector3 camtmp = new Vector3();
	public float camSpeed = 20f;
	
	private ShapeRenderer sr;
	
	public ModelBatch modelBatch;
	public Environment environment;
	
	public World(Camera cam){
		objects = new Hashtable<String, Object>();
		
		this.cam = cam;
		
		sr = new ShapeRenderer(50000);
		sr.setProjectionMatrix(cam.combined);
		
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}
	
	public void addObject(String id, Object object){
		objects.put(id, object);
	}
	
	public void removeObject(String id){
		objects.get(id).dispose();
		objects.remove(id);
	}
	
	public void render(){
		modelBatch.begin(cam);
		for(Object object : objects.values()){
			object.render();
			modelBatch.render(object.modelInstance, environment);
		}
		modelBatch.end();
	}
	
	public void debugRender(){
		sr.begin(ShapeType.Line);
		sr.line(0, 0, 500, 0, new Color(0xFF0000FF), new Color(0xFF0000FF));
		sr.line(0, 0, 0, 500, new Color(0x00FF00FF), new Color(0x00FF00FF));
		sr.end();
	}
	
	public void update(float delta){
		for(Object object : objects.values()){
			object.update(delta);
		}
		
		Input input = Gdx.input;
		
		float speed = camSpeed * delta;
		
		if (input.isKeyPressed(Keys.W)) {
			camtmp.set(cam.direction).nor().scl(speed);
			cam.position.add(camtmp);
		}
		if (input.isKeyPressed(Keys.S)) {
			camtmp.set(cam.direction).nor().scl(-speed);
			cam.position.add(camtmp);
		}
		if (input.isKeyPressed(Keys.A)) {
			camtmp.set(cam.direction).crs(cam.up).nor().scl(-speed);
			cam.position.add(camtmp);
		}
		if (input.isKeyPressed(Keys.D)) {
			camtmp.set(cam.direction).crs(cam.up).nor().scl(speed);
			cam.position.add(camtmp);
		}
		if (input.isKeyPressed(Keys.SPACE)) {
			camtmp.set(cam.up).nor().scl(speed/2);
			cam.position.add(camtmp);
		}
		if (input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camtmp.set(cam.up).nor().scl(-speed/2);
			cam.position.add(camtmp);
		}
		
		if(input.isKeyPressed(Keys.Q)){
			cam.rotate(-(80f * delta), cam.direction.x, cam.direction.y, cam.direction.z);
		}
		if(input.isKeyPressed(Keys.E)){
			cam.rotate(80f * delta, cam.direction.x, cam.direction.y, cam.direction.z);
		}
		
		if(input.isButtonPressed(Buttons.LEFT)){
			float deltaX = -Gdx.input.getDeltaX() * 0.5f;
			float deltaY = -Gdx.input.getDeltaY() * 0.5f;
			cam.direction.rotate(cam.up, deltaX);
			camtmp.set(cam.direction).crs(cam.up).nor();
			cam.direction.rotate(camtmp, deltaY);
		}
		
		cam.update();
	}
	
	public void dispose(){
		for(Object object : objects.values()){
			object.dispose();
		}
		
		sr.dispose();
	}
}
