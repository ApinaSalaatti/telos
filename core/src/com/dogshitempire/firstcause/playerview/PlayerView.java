package com.dogshitempire.firstcause.playerview;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;
import com.dogshitempire.firstcause.GameApplication;
import com.dogshitempire.firstcause.actors.GameActor;
import com.dogshitempire.firstcause.actors.components.PhysicsComponent;
import com.dogshitempire.firstcause.actors.components.RenderComponent;
import com.dogshitempire.firstcause.events.GameEvent;
import com.dogshitempire.firstcause.events.GameEventListener;
import com.dogshitempire.firstcause.logic.GameLogic;
import com.dogshitempire.firstcause.particles.Particle;

public class PlayerView implements InputProcessor, GameEventListener {	
	private CameraMover camera;
	
	private Vector2 baseCameraViewport;
	private float aspectRatio;
	
	// some testing shit
	private Texture bg;
	private Texture particle;
	
	private Array<Particle> particles = new Array<Particle>();
	
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	public PlayerView() {
		GameLogic logic = GameApplication.getLogic();
		
		Camera cam = logic.getStage().getCamera();
		// Set camera viewport to half of our screen size (i.e. "zoom in")
		cam.viewportWidth = Gdx.graphics.getWidth() / 2;
		cam.viewportHeight = Gdx.graphics.getHeight() / 2;
		// Set camera starting position
		cam.position.x = Gdx.graphics.getWidth() / 2;
		cam.position.y = Gdx.graphics.getHeight() / 2;
		
		cam.update();
		
		camera = new CameraMover(logic.getStage().getCamera());
		
		baseCameraViewport = new Vector2(cam.viewportWidth, cam.viewportHeight);
		aspectRatio = baseCameraViewport.y / baseCameraViewport.x;
		
		bg = new Texture(Gdx.files.internal("assets/images/bg.png"));
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		particle = new Texture(Gdx.files.internal("assets/images/throttleParticle.png"));
		particle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
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
		
		logic.getStage().addActor(actor);
		logic.player = actor;
		
		GameApplication.getEventManager().registerListener(GameEvent.playerShipCreated, this);
	}
	
	public void update(float deltaSeconds) {
		GameLogic logic = GameApplication.getLogic();
		
		PhysicsComponent physics = (PhysicsComponent)logic.player.getComponent("Physics");
		camera.setTargetPosition(physics.getBody().getPosition().add(physics.getBody().getLinearVelocity()));
		
		// Set camera zoom according to the velocity of player
		float s = physics.getBody().getLinearVelocity().len() * 2;
		camera.getCamera().viewportWidth = baseCameraViewport.x + s;
		camera.getCamera().viewportHeight = baseCameraViewport.y + s * aspectRatio;
		
		camera.update(deltaSeconds);
		
		if(logic.throttle) {
			Particle p = new Particle(particle, 0.5f, physics.getBody().getPosition());
			particles.add(p);
		}
		
		Iterator<Particle> it = particles.iterator();
		while(it.hasNext()) {
			Particle p = it.next();
			p.update(deltaSeconds);
			if(p.getLifetime() <= 0) {
				//p.getTexture().dispose();
				it.remove();
			}
		}
	}
	
	public void render() {
		GameLogic logic = GameApplication.getLogic();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Batch batch = logic.getStage().getSpriteBatch();
		
		batch.begin();
		
		for(int x = 0; x < 30; x++) {
			for(int y = 0; y < 30; y++) {
				batch.draw(bg, x*64, y*64);
			}
		}
		
		//batch.draw(texture, pos.x - texture.getWidth() / 2, pos.y - texture.getHeight() / 2, texture.getWidth()  / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), 1, 1, angle, 0, 0, 16, 16, false, false);
		
		for(int i = 0; i < particles.size; i++) {
			batch.draw(particles.get(i).getTexture(), particles.get(i).getPosition().x, particles.get(i).getPosition().y, 2, 2);
		}
		
		batch.end();
		
		logic.getStage().draw();
		
		debugRenderer.render(logic.getPhysics().getWorld(), logic.getStage().getCamera().combined);
	}

	@Override
	public boolean keyDown(int keycode) {
		GameLogic logic = GameApplication.getLogic();
		
		if(keycode == Keys.W) {
			logic.throttle = true;
			return true;
		}
		
		if(keycode == Keys.A) {
			logic.turn += 1;
			return true;
		}
		if(keycode == Keys.D) {
			logic.turn -= 1;
			return true;
		}
		
		if(keycode == Keys.F) {
			GameApplication.getEventManager().queueEvent(new GameEvent(GameEvent.playerShipCreated, null));
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		GameLogic logic = GameApplication.getLogic();
		
		if(keycode == Keys.W) {
			logic.throttle = false;
			return true;
		}
		
		if(keycode == Keys.A) {
			logic.turn -= 1;
			return true;
		}
		if(keycode == Keys.D) {
			logic.turn += 1;
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
			camera.setRumble(3f);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			camera.setRumble(0f);
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
		Gdx.app.log("VIEW", "Event: " + event.getType());
	}
}
