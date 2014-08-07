package com.dogshitempire.firstcause.playerview;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.dogshitempire.firstcause.GameApplication;
import com.dogshitempire.firstcause.actors.GameActor;
import com.dogshitempire.firstcause.actors.components.PhysicsComponent;
import com.dogshitempire.firstcause.events.GameEvent;
import com.dogshitempire.firstcause.events.GameEventListener;

public class PlayerController implements InputProcessor, GameEventListener {
	public GameActor player;
	
	public boolean throttle;
	public float turn;
	public boolean firing;
	private float turnSpeed = 1.8f;
	private float force = 2000f;
	
	public PlayerController() {
		GameApplication.getEventManager().registerListener(GameEvent.playerShipCreated, this);
	}
	
	public void update(float deltaSeconds) {
		PhysicsComponent physics = (PhysicsComponent)player.getComponent("Physics");
		physics.getBody().setAngularVelocity(turn * turnSpeed);
		
		if(throttle) {
			physics.getBody().applyForceToCenter((float)Math.cos(physics.getBody().getAngle()) * force, (float)Math.sin(physics.getBody().getAngle()) * force, true);
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.W) {
			throttle = true;
			return true;
		}
		
		if(keycode == Keys.A) {
			turn += 1;
			return true;
		}
		if(keycode == Keys.D) {
			turn -= 1;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.W) {
			throttle = false;
			return true;
		}
		
		if(keycode == Keys.A) {
			turn -= 1;
			return true;
		}
		if(keycode == Keys.D) {
			turn += 1;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			firing = true;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			firing = false;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void receiveEvent(GameEvent event) {
		if(event.getType() == GameEvent.playerShipCreated) {
			player = (GameActor)event.getData();
		}
	}
}
