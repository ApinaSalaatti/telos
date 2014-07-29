package com.dogshitempire.firstcause.playerview;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraMover {
	private Camera camera;
	
	private float deadZone = 81f;
	private Vector2 targetPos;
	
	private float rumble;
	
	public CameraMover(Camera camera) {
		this.camera = camera;
		targetPos = new Vector2(camera.position.x, camera.position.y);
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setTargetPosition(Vector2 target) {
		targetPos.x = target.x;
		targetPos.y = target.y;
	}
	
	public void setRumble(float rumble) {
		this.rumble = rumble;
	}
	
	public void update(float deltaSeconds) {
		float dX= Math.abs(camera.position.x - targetPos.x);
		float dY = Math.abs(camera.position.y - targetPos.y);
		float dist = (float)Math.sqrt(dX*dX + dY*dY);
		
		if(dist > deadZone) {
			Vector2 newPos = new Vector2(camera.position.x, camera.position.y);
			
			newPos = newPos.lerp(targetPos, deltaSeconds * (deadZone+1f)/dist);
			camera.position.x = newPos.x;
			camera.position.y = newPos.y;
			
		}
		
		if(rumble != 0) {
			camera.position.x += MathUtils.random(-0.5f, 0.5f) * MathUtils.random(1.25f, 1.5f) * rumble;
			camera.position.y += MathUtils.random(-0.5f, 0.5f) * MathUtils.random(1.25f, 1.5f) * rumble;
		}
		
		camera.update();
	}
}
