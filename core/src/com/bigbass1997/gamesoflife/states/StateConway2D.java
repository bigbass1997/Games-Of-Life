package com.bigbass1997.gamesoflife.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.gamesoflife.world.Grid2D;
import com.bigbass1997.gamesoflife.world.World;
import com.bigbass1997.gamesoflife.rules.*;
import com.bigbass1997.gamesoflife.skins.SkinManager;

public class StateConway2D extends State {
	
	private ShapeRenderer sr;
	
	private Grid2D grid;
	
	private Label infoLabel; 
	
	public StateConway2D(String id){
		super(id);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
        world = new World(cam);
        
		stage = new Stage();
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(50000);
		sr.setProjectionMatrix(cam.combined);
		
		grid = new Grid2D(new RuleSetExperimental(), 250, 0, Gdx.graphics.getHeight()-1, Gdx.graphics.getHeight()-1, 150, 150);
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
		
		if(isDirty || input.isKeyJustPressed(Keys.C)) grid.clean();
		
		if(input.isKeyPressed(Keys.SPACE)) grid.stepGeneration();
		
		String n = "\n";
		String info = 
				"Data:\n" +
				"  GridSize: " + grid.cellsWide + " x " + grid.cellsHigh + n +
				"  TotalCells: " + (grid.cellsWide * grid.cellsHigh) + n +
				"  Generations: " + grid.generationsCount + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond();
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
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
