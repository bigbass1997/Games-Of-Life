package com.bigbass1997.gamesoflife.states;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bigbass1997.gamesoflife.world.World;

public class State {
	
	public String id;
	public Camera cam;
	public World world;
	public Stage stage;
	
	public State(String id){
		this.id = id;
	}
	
	public void render() {
	}
	public void update(float delta) {
	}
	public State clone(){
		return new State(id);
	}
	public void dispose() {
	}
}
