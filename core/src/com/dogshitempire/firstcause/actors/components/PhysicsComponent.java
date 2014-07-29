package com.dogshitempire.firstcause.actors.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.dogshitempire.firstcause.GameApplication;

public class PhysicsComponent extends BaseComponent {
	public static final String componentName = "Physics";
	private Body body;
	
	public Body getBody() {
		return body;
	}
	
	public PhysicsComponent(BodyType type, Vector2 position, Shape shape, float density, float friction, float restitution) {
		super(componentName);
		
		body = GameApplication.getLogic().getPhysics().createBody(type, position, shape, density, friction, restitution);
		body.setUserData(actor);
		
		// Test stuff
		body.setAngularDamping(4.5f);
		body.setLinearDamping(1.5f);
	}
	
	@Override
	public void update(float deltaSeconds) {
		actor.setX(body.getPosition().x);
		actor.setY(body.getPosition().y);
		actor.setAngle(body.getAngle()  * MathUtils.radiansToDegrees);
	}
}
