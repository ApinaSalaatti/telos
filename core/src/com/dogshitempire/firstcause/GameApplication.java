package com.dogshitempire.firstcause;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dogshitempire.firstcause.events.EventManager;
import com.dogshitempire.firstcause.logic.GameLogic;
import com.dogshitempire.firstcause.logic.stages.PlayStage;

public class GameApplication implements ApplicationListener {
	Box2DDebugRenderer debugRenderer;
	
	private static GameLogic logic;
	private static EventManager events;
	
	// Used for stepping the game 1/60th second at a time.
	private  float accumulated = 0.0f;
	
	public static GameLogic getLogic() {
		return logic;
	}
	public static EventManager getEventManager() {
		return events;
	}
	
	@Override
	public void create() {
		events = new EventManager();
		
		logic = new GameLogic();
		
		// Set the starting stage
		logic.changeStage(new PlayStage());
		
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		accumulated += Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		logic.getStage().draw();
		//view.render();
		
		debugRenderer.render(logic.getPhysics().getWorld(), logic.getStage().getCamera().combined);
		
		//Gdx.app.log("DELTA", ""+Gdx.graphics.getDeltaTime());
		float step = 1f / 60f;
		while(accumulated >= step) {
			events.update(step);
			logic.update(step);
			
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
