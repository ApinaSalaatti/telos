package com.dogshitempire.firstcause.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameLogic {
	// Stage
	private Stage currentStage;
	
	// Physics stuff
	private Physics physics;
	
	
	public Stage getStage() {
		return currentStage;
	}
	
	public Physics getPhysics() {
		return physics;
	}
	
	
	public GameLogic() {
		physics = new Physics();
	}
	
	public void update(float deltaSeconds) {
		physics.update(deltaSeconds);
		
		currentStage.act(deltaSeconds);
	}
	
	public void changeStage(Stage newStage) {
		if(currentStage != null) {
			currentStage.clear();
			currentStage.dispose();
		}
		
		currentStage = newStage;
		Gdx.input.setInputProcessor(currentStage);
	}
}
