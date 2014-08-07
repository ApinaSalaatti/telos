package com.dogshitempire.firstcause.playerview.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.dogshitempire.firstcause.logic.stages.GameStage;

public abstract class GameScreen implements InputProcessor {
	protected GameStage stage;
	
	private Array<Actor> screenElements;
	
	public GameScreen() {
		screenElements = new Array<Actor>();
	}
	
	public void addScreenElement(Actor element) {
		screenElements.add(element);
	}
	public void removeScreenElement(Actor element) {
		screenElements.removeValue(element, true);
	}
	
	public void onAdd(GameStage s) {
		stage = s;
	}
	
	public void draw(Batch batch) {
		for(int i = 0; i < screenElements.size; i++) {
			screenElements.get(i).draw(batch, 1f);
		}
	}
	
	public void update(float deltaSeconds) {
		
	}
	
	private Actor findTarget(int sx, int sy) {
		for(int i = 0; i < screenElements.size; i++) {
			float left = screenElements.get(i).getX();
			float right = screenElements.get(i).getRight();
			float bottom = screenElements.get(i).getY();
			float top = screenElements.get(i).getTop();
			
			if(sx > left && sx < right && sy > bottom && sy < top) {
				return screenElements.get(i);
			}
		}
		
		return null;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	private Actor touched;
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		InputEvent event = Pools.obtain(InputEvent.class);
		event.setType(Type.touchDown);
		event.setStage(this.stage);
		event.setStageX(screenX);
		event.setStageY(screenY);
		event.setPointer(pointer);
		event.setButton(button);

		Actor target = findTarget(screenX, screenY);
		
		if(target != null) {
			target.fire(event);
			touched = target;
		}
		
		boolean handled = event.isHandled();
		Pools.free(event);
		
		return handled;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		InputEvent event = Pools.obtain(InputEvent.class);
		event.setType(Type.touchUp);
		event.setStage(this.stage);
		event.setStageX(screenX);
		event.setStageY(screenY);
		event.setPointer(pointer);
		event.setButton(button);

		boolean handled = false;
		
		if(touched != null) {
			handled = true;
			touched.fire(event);
			touched = null;
		}
		
		Pools.free(event);
		
		return handled;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
