package com.bigbass1997.gamesoflife.states;

public class StateMenu extends State {
	
	public StateMenu(String id){
		super(id);
		
	}
	
	@Override
	public void render() {
		
	}

	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public State clone(){
		State newState = new StateMenu(id);
		
		return newState;
	}

	@Override
	public void dispose() {
		
	}

}
