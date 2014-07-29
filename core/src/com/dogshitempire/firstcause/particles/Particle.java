package com.dogshitempire.firstcause.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Particle {
	private Texture tex;
	private float lifetime;
	
	private Vector2 position;
	private Vector2 velocity;
	
	public Particle(Texture tex, float maxLifetime, Vector2 position) {
		this.tex = tex;
		this.lifetime = maxLifetime * (float)Math.random();
		this.position = new Vector2(position.x, position.y);
		
		velocity = new Vector2((float)MathUtils.random(-0.5f, 0.5f)* 5, (float)MathUtils.random(-0.5f, 0.5f)*5);
	}
	
	public Texture getTexture() {
		return tex;
	}
	public Vector2 getPosition() {
		return position;
	}
	public float getLifetime() {
		return lifetime;
	}
	
	public void update(float deltaSeconds) {
		lifetime -= deltaSeconds;
		
		position.add(velocity);
	}
}
