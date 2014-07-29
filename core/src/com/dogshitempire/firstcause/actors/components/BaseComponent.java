package com.dogshitempire.firstcause.actors.components;

import com.dogshitempire.firstcause.actors.GameActor;

public abstract class BaseComponent {
	private String name;
	protected GameActor actor;
	
	public String getName() {
		return name;
	}
	
	public GameActor getActor() {
		return actor;
	}

	public BaseComponent(String n) {
		name = n;
	}
	
	public void onAttach(GameActor a) {
		actor = a;
	}
	
	public void update(float deltaSeconds) {
		
	}
	
	public void onCollisionStart(GameActor other) {
		
	}
	
	public void onCollisionEnd(GameActor other) {
		
	}
}
