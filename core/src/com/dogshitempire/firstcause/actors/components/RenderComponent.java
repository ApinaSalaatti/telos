package com.dogshitempire.firstcause.actors.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class RenderComponent extends BaseComponent {
	public static final String componentName = "Render";
	
	private float alpha;
	
	private ObjectMap<String, Animation> animations;
	private Animation currentAnimation;
	private float stateTime;
	
	public void setAlpha(float a) {
		alpha = a;
	}
	public float getAlpha() {
		return alpha;
	}
	
	public RenderComponent() {
		super(componentName);
		
		alpha = 1f;
		
		animations = new ObjectMap<String, Animation>();
		stateTime = 0f;
	}
	
	public void addAnimation(String name, Animation anim) {
		animations.put(name, anim);
		if(currentAnimation == null) {
			currentAnimation = anim;
		}
	}
	public void playAnimation(String name) {
		if(animations.containsKey(name)) {
			stateTime = 0;
			currentAnimation = animations.get(name);
		}
	}
	
	@Override
	public void update(float deltaSeconds) {
		stateTime += deltaSeconds;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		float angle = actor.getAngle();
		
		// Set alpha
		Color c = batch.getColor();
		float oldAlpha = c.a;
		c.a = parentAlpha * alpha;
		batch.setColor(c);
		
		if(currentAnimation != null) {
			TextureRegion reg = currentAnimation.getKeyFrame(stateTime);
			batch.draw(
					reg,
					actor.getX() - reg.getRegionWidth() / 2,
					actor.getY() - reg.getRegionHeight() / 2,
					reg.getRegionWidth() / 2,
					reg.getRegionHeight() / 2,
					reg.getRegionWidth(),
					reg.getRegionHeight(),
					1, 1, angle);
		}
		
		/*
		batch.draw(
				texture,
				actor.getX() - texture.getWidth() / 2,
				actor.getY() - texture.getHeight() / 2,
				texture.getWidth()  / 2, texture.getHeight() / 2,
				texture.getWidth(),
				texture.getHeight(),
				1, 1, angle, 0, 0, 16, 16, false, false
		);
		*/
		
		// Return old alpha
		c.a = oldAlpha;
		batch.setColor(c);
	}
}
