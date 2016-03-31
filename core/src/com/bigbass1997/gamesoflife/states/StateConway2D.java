package com.bigbass1997.gamesoflife.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bigbass1997.gamesoflife.world.Grid2D;
import com.bigbass1997.gamesoflife.world.World;

public class StateConway2D extends State {
	
	private ShapeRenderer sr;
	
	private Grid2D grid;
	
	public StateConway2D(String id){
		super(id);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
        world = new World(cam);
        
		stage = new Stage();
		
		sr = new ShapeRenderer(50000);
		sr.setProjectionMatrix(cam.combined);
		
		grid = new Grid2D(0, 0, Gdx.graphics.getHeight()-1, Gdx.graphics.getHeight()-1, 16, 16);
	}
	
	@Override
	public void render() {
		grid.render(sr);
		
		world.render();
		stage.draw();
	}

	@Override
	public void update(float delta) {
		Input input = Gdx.input;
		
		boolean isDirty = false;
		
		if(input.isKeyPressed(Keys.LEFT)){
			grid.cellsWide -= 1;
			isDirty = true;
		}
		if(input.isKeyPressed(Keys.RIGHT)) {
			grid.cellsWide += 1;
			isDirty = true;
		}

		if(input.isKeyPressed(Keys.DOWN)){
			grid.cellsHigh -= 1;
			isDirty = true;
		}
		if(input.isKeyPressed(Keys.UP)){
			grid.cellsHigh += 1;
			isDirty = true;
		}
		
		if(grid.cellsWide <= 0){
			grid.cellsWide = 1;
			isDirty = true;
		}
		if(grid.cellsHigh <= 0){
			grid.cellsHigh = 1;
			isDirty = true;
		}
		
		if(isDirty) grid.clean();
		
		if(input.isKeyPressed(Keys.SPACE)) grid.stepGeneration();
		
		grid.update(delta);
		world.update(delta);
		stage.act(delta);
	}

	@Override
	public void dispose() {
		world.dispose();
		stage.dispose();
		sr.dispose();
	}
}