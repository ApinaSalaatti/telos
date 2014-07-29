package com.dogshitempire.firstcause.events;

public class GameEvent {
	/*
	 * Event types
	 */
	public static final int playerShipCreated = 1;
	
	private int eventType;
	private Object eventData;
	
	public int getType() {
		return eventType;
	}
	
	public Object getData() {
		return eventData;
	}
	
	public GameEvent(int type, Object data) {
		eventType = type;
		eventData = data;
	}
}
