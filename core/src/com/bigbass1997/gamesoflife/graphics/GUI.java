package com.bigbass1997.gamesoflife.graphics;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * <p>To be used to manage all stage related objects to reduce clutter and individual method calls for each stage actor in use.</p>
 * 
 * @see com.badlogic.gdx.scenes.scene2d.Stage
 */
public class GUI {
	
	private Stage stage;
	
	private Hashtable<String, Actor> actors; 

	public static InputMultiplexer inputMulti = new InputMultiplexer();
	
	public GUI(){
		stage = new Stage();
		stage.addCaptureListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				if(!(event.getTarget() instanceof TextField)) stage.setKeyboardFocus(null);
				return false;
			}
		});
		
		inputMulti.addProcessor(stage);
		
		Gdx.input.setInputProcessor(inputMulti);
		
		actors = new Hashtable<String, Actor>();
	}
	
	public void render(){
		stage.draw();
	}
	
	public void update(float delta){
		stage.act(delta);
	}
	
	public void addActor(String id, Actor actor){
		actors.put(id, actor);
		stage.addActor(actor);
	}
	
	public void removeActor(String id){
		stage.getRoot().removeActor(actors.get(id));
		actors.remove(id);
	}
	
	public Actor getActor(String id){
		return actors.get(id);
	}
	
	public void unfocus(){
		stage.unfocusAll();
	}
	
	public void dispose(){
		stage.dispose();
	}
}
