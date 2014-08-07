package com.dogshitempire.firstcause.logic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dogshitempire.firstcause.playerview.screens.GameScreen;

/**
 * An awesome abstract class that all other stages of the game can extends.
 */
public abstract class GameStage extends Stage {
	public Array<GameScreen> screens;
	
	private Matrix4 oldTransform;
	private Matrix4 uiMatrix;
	
	public GameStage() {
		screens = new Array<GameScreen>();
		
		oldTransform = new Matrix4();
		uiMatrix = new Matrix4();
		uiMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void addScreen(GameScreen screen) {
		if(!screens.contains(screen, true)) {
			screens.add(screen);
			screen.onAdd(this);
		}
	}
	public GameScreen popScreen() {
		return screens.pop();
	}
	public void removeScreen(GameScreen screen) {
		screens.removeValue(screen, true);
	}
	
	@Override
	public void draw() {
		// Draw world
		super.draw();
		
		// Draw GUIs
		Batch batch = getBatch();
		
		oldTransform.set(batch.getProjectionMatrix());
		batch.setProjectionMatrix(uiMatrix);
		batch.begin();
		for(int i = 0; i < screens.size; i++) {
			screens.get(i).draw(batch);
		}
		batch.end();
		batch.setProjectionMatrix(oldTransform);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).keyDown(keycode)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).keyUp(keycode)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).keyTyped(character)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).touchDown(screenX, screenY, pointer, button)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).touchUp(screenX, screenY, pointer, button)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).touchDragged(screenX, screenY, pointer)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).mouseMoved(screenX, screenY)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		for(int i = 0; i < screens.size; i++) {
			if(screens.get(i).scrolled(amount)) {
				return true;
			}
		}
		
		return false;
	}
}
