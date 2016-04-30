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
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		grid = new Grid2D("R1,S2.3,B3.3,NM", 150, 5, 400, 400, 5, 5);
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
		
		if(input.isKeyJustPressed(Keys.G)){
			if(grid.isDrawingGrid){
				grid.isDrawingGrid = false;
			} else {
				grid.isDrawingGrid = true;
			}
		}
		
		boolean isDirty = false;
		
		if(input.isKeyJustPressed(Keys.LEFT)){
			grid.modCellWidth(-1);
			isDirty = true;
		}
		if(input.isKeyJustPressed(Keys.RIGHT)) {
			grid.modCellWidth(1);
			isDirty = true;
		}

		if(input.isKeyJustPressed(Keys.DOWN)){
			grid.modCellHeight(-1);
			isDirty = true;
		}
		if(input.isKeyJustPressed(Keys.UP)){
			grid.modCellHeight(1);
			isDirty = true;
		}
		
		if(isDirty || input.isKeyJustPressed(Keys.C)) grid.clean();
		
		if(input.isKeyPressed(Keys.SPACE)) grid.stepGeneration();
		if(input.isKeyJustPressed(Keys.S)) grid.stepGeneration();
		
		String n = "\n";
		String info = 
				"Data:\n" +
				"  GridSize: " + grid.getCellsWide() + " x " + grid.getCellsHigh() + n +
				"  TotalCells: " + (grid.getCellsWide() * grid.getCellsHigh()) + n +
				"  CellSize: " + grid.getCellWidth() + " x " + grid.getCellHeight() + n +
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
