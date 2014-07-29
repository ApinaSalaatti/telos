package com.dogshitempire.firstcause.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dogshitempire.firstcause.actors.GameActor;
import com.dogshitempire.firstcause.actors.components.PhysicsComponent;

public class GameLogic {
	// Stage
	private Stage currentStage;
	
	// Physics stuff
	private Physics physics;
	
	
	public Stage getStage() {
		return currentStage;
	}
	public void setStage(Stage stage) {
		currentStage = stage;
	}
	
	public Physics getPhysics() {
		return physics;
	}
	
	
	public GameLogic() {
		physics = new Physics();
		
		// Create our body definition
		BodyDef groundBodyDef =new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 10));  

		// Create a body from the definition and add it to the world
		Body groundBody = physics.getWorld().createBody(groundBodyDef);  

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(900, 10.0f);
		// Create a fixture from our polygon shape and add it to our ground body  
		groundBody.createFixture(groundBox, 0.0f); 
		// Clean up after ourselves
		groundBox.dispose();
		
		currentStage = new Stage();
	}
	
	public GameActor player;
	
	public boolean throttle;
	public float turn;
	private float turnSpeed = 1.8f;
	private float force = 2000f;
	
	public void update(float deltaSeconds) {
		physics.update(deltaSeconds);
		
		PhysicsComponent physics = (PhysicsComponent)player.getComponent("Physics");
		physics.getBody().setAngularVelocity(turn * turnSpeed);
		
		if(throttle) {
			physics.getBody().applyForceToCenter((float)Math.cos(physics.getBody().getAngle()) * force, (float)Math.sin(physics.getBody().getAngle()) * force, true);
		}
		
		currentStage.act(deltaSeconds);
	}
	
	public void changeStage(Stage newStage) {
		currentStage.clear();
		currentStage.dispose();
		
		currentStage = newStage;
	}
}
