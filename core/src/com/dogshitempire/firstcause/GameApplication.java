package com.dogshitempire.firstcause;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.dogshitempire.firstcause.events.EventManager;
import com.dogshitempire.firstcause.logic.GameLogic;
import com.dogshitempire.firstcause.playerview.PlayerView;

public class GameApplication implements ApplicationListener {
	private static GameLogic logic;
	private static PlayerView view;
	private static EventManager events;
	
	// Used for stepping the game 1/60th second at a time.
	private  float accumulated = 0.0f;
	
	public static GameLogic getLogic() {
		return logic;
	}
	public static PlayerView getView() {
		return view;
	}
	public static EventManager getEventManager() {
		return events;
	}
	
	@Override
	public void create() {
		events = new EventManager();
		
		logic = new GameLogic();
		view = new PlayerView();
		
		Gdx.input.setInputProcessor(view);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		accumulated += Gdx.graphics.getDeltaTime();
		
		view.render();
		
		//Gdx.app.log("DELTA", ""+Gdx.graphics.getDeltaTime());
		float step = 1f / 60f;
		while(accumulated >= step) {
			view.update(step);
			logic.update(step);
			events.update(step);
			
			accumulated -= step;
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
