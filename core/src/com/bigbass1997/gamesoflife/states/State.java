package com.bigbass1997.gamesoflife.states;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bigbass1997.gamesoflife.world.World;

public class State {
	
	public String id;
	public Camera cam;
	public World world;
	public Stage stage;
	
	/**
	 * State constructor.
	 * 
	 * @param id any string to be used to identify the State
	 */
	public State(String id){
		this.id = id;
	}
	
	/**
	 * Any rendering should be done in this method.
	 */
	public void render() {
		//render stuff
	}
	
	/**
	 * Any variable updating or other calculations that require frame delta should be done in this method.
	 * 
	 * @param delta time in seconds between the beginning of the last frame and the beginning of the current frame
	 * @see {@link com.badlogic.gdx.Graphics#getDeltaTime()}
	 */
	public void update(float delta) {
		//update stuff
	}
	
	/**
	 * <p>Method should be called when the state needs to be removed from memory.</p>
	 * <p>Please use as needed in order to properly clear allocated assets to prevent memory leaks.</p>
	 */
	public void dispose() {
		//dispose stuff
	}
}
