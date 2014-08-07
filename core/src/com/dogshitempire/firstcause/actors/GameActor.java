package com.dogshitempire.firstcause.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.dogshitempire.firstcause.actors.components.BaseComponent;
import com.dogshitempire.firstcause.actors.components.RenderComponent;

public class GameActor extends Actor {
	public RenderComponent rendering;
	
	private ObjectMap<String, BaseComponent> components;

	
	public GameActor() {
		setRotation(0f);
		
		components = new ObjectMap<String, BaseComponent>();
	}
	
	public void attachComponent(BaseComponent component) {
		component.onAttach(this);
		components.put(component.getName(), component);
		
		// Set references to some often used components
		if(component.getName() == RenderComponent.componentName) {
			rendering = (RenderComponent)component;
		}
	}
	public BaseComponent removeComponent(String component) {
		BaseComponent c = components.remove(component);
		return c;
	}
	public BaseComponent getComponent(String component) {
		if(components.containsKey(component)) {
			return components.get(component);
		}
		
		return null;
	}
	
	@Override
	public void act(float deltaSeconds) {
		Values<BaseComponent> v = components.values();
		while(v.hasNext()) {
			BaseComponent b = v.next();
			b.update(deltaSeconds);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(rendering != null) {
			rendering.draw(batch, parentAlpha);
		}
	}
	
	public void onCollisionStart(GameActor other) {
		//Gdx.app.log("GAMEACTOR", "COLLISION WITH: " + other);
	}
	
	public void onCollisionEnd(GameActor other) {
		
	}
}
