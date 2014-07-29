package com.dogshitempire.firstcause.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.dogshitempire.firstcause.actors.GameActor;

public class Physics implements ContactListener {
	private World world;
	
	public World getWorld() {
		return world;
	}
	
	public Physics() {
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(this);
	}
	
	public Body createBody(BodyType type, Vector2 position, Shape shape, float density, float friction, float restitution) {
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// BodyType will be dynamic or static
		bodyDef.type = type;
		// Set our body's starting position in the world
		bodyDef.position.set(position.x, position.y);

		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density; 
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;

		// Create our fixture and attach it to the body
		body.createFixture(fixtureDef);
		
		return body;
	}
	
	public void update(float deltaSeconds) {
		world.step(deltaSeconds, 6, 2);
	}

	@Override
	public void beginContact(Contact contact) {
		GameActor actor1 = (GameActor)contact.getFixtureA().getBody().getUserData();
		GameActor actor2 = (GameActor)contact.getFixtureB().getBody().getUserData();
		
		actor1.onCollisionStart(actor2);
		actor2.onCollisionStart(actor1);
	}

	@Override
	public void endContact(Contact contact) {
		GameActor actor1 = (GameActor)contact.getFixtureA().getBody().getUserData();
		GameActor actor2 = (GameActor)contact.getFixtureB().getBody().getUserData();
		
		actor1.onCollisionEnd(actor2);
		actor2.onCollisionEnd(actor1);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
