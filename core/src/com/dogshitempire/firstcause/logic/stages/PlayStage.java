package com.dogshitempire.firstcause.logic.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.dogshitempire.firstcause.GameApplication;
import com.dogshitempire.firstcause.actors.GameActor;
import com.dogshitempire.firstcause.actors.components.PhysicsComponent;
import com.dogshitempire.firstcause.actors.components.RenderComponent;
import com.dogshitempire.firstcause.events.GameEvent;
import com.dogshitempire.firstcause.logic.Physics;
import com.dogshitempire.firstcause.playerview.CameraMover;
import com.dogshitempire.firstcause.playerview.PlayerController;
import com.dogshitempire.firstcause.playerview.screens.ResearchScreen;

public class PlayStage extends GameStage {
	private PlayerController controller;
	
	private CameraMover camera;
	
	private Texture bg;
	
	private Vector2 baseCameraViewport;
	private float aspectRatio;
	
	public PlayStage() {
		super();
		
		controller = new PlayerController();
		
		bg = new Texture(Gdx.files.internal("assets/images/bg.png"));
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Camera cam = getCamera();
		// Set camera viewport to half of our screen size (i.e. "zoom in")
		cam.viewportWidth = Gdx.graphics.getWidth() / 2;
		cam.viewportHeight = Gdx.graphics.getHeight() / 2;
		// Set camera starting position
		cam.position.x = Gdx.graphics.getWidth() / 2;
		cam.position.y = Gdx.graphics.getHeight() / 2;
		
		cam.update();
		
		camera = new CameraMover(cam);
		
		baseCameraViewport = new Vector2(cam.viewportWidth, cam.viewportHeight);
		aspectRatio = baseCameraViewport.y / baseCameraViewport.x;
		
		Physics physics = GameApplication.getLogic().getPhysics();
		
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
		
		GameActor actor = new GameActor();
		
		CircleShape circle = new CircleShape();
		circle.setRadius(4.5f);
		PhysicsComponent pc = new PhysicsComponent(BodyType.DynamicBody, new Vector2(100, 300), circle, 0.1f, 1f, 0.3f);
		circle.dispose();
		
		Texture texture = new Texture(Gdx.files.internal("assets/images/ship.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		RenderComponent rc = new RenderComponent();
		Array<TextureRegion> arr = new Array<TextureRegion>();
		arr.add(new TextureRegion(texture));
		rc.addAnimation("flying", new Animation(0.2f, arr, Animation.PlayMode.LOOP));
		
		actor.attachComponent(pc);
		actor.attachComponent(rc);
		
		addActor(actor);
		player = actor;
		
		GameApplication.getEventManager().queueEvent(new GameEvent(GameEvent.playerShipCreated, actor));
		
		ResearchScreen rs = new ResearchScreen();
		addScreen(rs);
	}
	
	private GameActor player;
	public GameActor getPlayer() {
		return player;
	}
	
	@Override
	public void act(float deltaSeconds) {
		super.act(deltaSeconds);
		
		controller.update(deltaSeconds);
		
		PhysicsComponent physics = (PhysicsComponent)controller.player.getComponent("Physics");
		
		camera.setTargetPosition(physics.getBody().getPosition().add(physics.getBody().getLinearVelocity()));
		
		// Set camera zoom according to the velocity of player
		float s = physics.getBody().getLinearVelocity().len() * 2;
		camera.getCamera().viewportWidth = baseCameraViewport.x + s;
		camera.getCamera().viewportHeight = baseCameraViewport.y + s * aspectRatio;
		
		camera.update(deltaSeconds);
		
		if(controller.firing) {
			camera.setRumble(3f);
		}
		else {
			camera.setRumble(0f);
		}
	}
	
	@Override
	public void draw() {
		Batch batch = getBatch();
		// Draw background
		batch.begin();
		for(int x = 0; x < 30; x++) {
			for(int y = 0; y < 30; y++) {
				batch.draw(bg, x*64, y*64);
			}
		}
		batch.end();
		
		// Draw other stufffff
		super.draw();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(super.keyDown(keycode)) {
			return true;
		}
		
		return controller.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		if(super.keyUp(keycode)) {
			return true;
		}
		
		return controller.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		if(super.keyTyped(character)) {
			return true;
		}
		
		return controller.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(super.touchDown(screenX, screenY, pointer, button)) {
			return true;
		}
		
		return controller.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(super.touchUp(screenX, screenY, pointer, button)) {
			return true;
		}
		
		return controller.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(super.touchDragged(screenX, screenY, pointer)) {
			return true;
		}
		
		return controller.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(super.mouseMoved(screenX, screenY)) {
			return true;
		}
		
		return controller.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(int amount) {
		if(super.scrolled(amount)) {
			return true;
		}
		
		return controller.scrolled(amount);
	}
}
