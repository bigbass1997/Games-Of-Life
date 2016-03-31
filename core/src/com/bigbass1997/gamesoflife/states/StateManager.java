package com.bigbass1997.gamesoflife.states;

import java.util.ArrayList;

public class StateManager {
	
	private State curState;
	
	public ArrayList<State> pastStates; //Allows for not loosing data when switching to new state.
	
	public StateManager(State firstState){
		curState = firstState;
		pastStates = new ArrayList<State>();
	}
	
	public void setCurState(String id){
		for(State state : pastStates){
			if(state.id.equals(id)){
				setCurState(state);
			}
		}
	}
	
	public void setCurState(State state){
		pastStates.add(0, curState);
		curState = state;
	}
	
	public State getCurState(){
		return curState;
	}
	
	public State getPreviousState(String id) throws Exception{
		for(State prevState : pastStates){
			if(prevState.id.equals(id)) return prevState;
		}
		
		throw new Exception("State ID not found!");
	}
	
	public void render(){
		curState.render();
	}
	
	public void update(float delta){
		curState.update(delta);
	}
	
	public void dispose(){
		curState.dispose();
		for(State state : pastStates){
			state.dispose();
		}
		pastStates.clear();
	}
}
