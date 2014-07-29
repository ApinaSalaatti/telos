package com.dogshitempire.firstcause.events;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

public class EventManager {
	private IntMap<Array<GameEventListener>> listeners;
	
	// Use two queues so when an event causes a new event to be fired, it will be resolved next frame.
	private Array<GameEvent> eventQueue0;
	private Array<GameEvent> eventQueue1;
	private int currentQueue;
	
	public EventManager() {
		listeners = new IntMap<Array<GameEventListener>>();
		eventQueue0 = new Array<GameEvent>();
		eventQueue1 = new Array<GameEvent>();
		currentQueue = 0;
	}
	
	public void registerListener(int eventType, GameEventListener listener) {
		if(listeners.containsKey(eventType)) {
			Array<GameEventListener> l = listeners.get(eventType);
			l.add(listener);
		}
		else {
			Array<GameEventListener> l = new Array<GameEventListener>();
			l.add(listener);
			listeners.put(eventType, l);
		}
	}
	
	public void removeListener(int eventType, GameEventListener listener) {
		if(listeners.containsKey(eventType)) {
			Array<GameEventListener> l = listeners.get(eventType);
			l.removeValue(listener, true);
		}
	}
	
	public void queueEvent(GameEvent e) {
		if(currentQueue == 0) {
			eventQueue0.add(e);
		}
		else {
			eventQueue1.add(e);
		}
	}
	
	public void update(float deltaSeconds) {
		Array<GameEvent> queue;
		
		// Choose correct queue and change it so new events go to the other one
		if(currentQueue == 0) {
			queue = eventQueue0;
			currentQueue = 1;
		}
		else {
			queue = eventQueue1;
			currentQueue = 0;
		}
		
		// Go through the events and call the proper listeners
		while(queue.size > 0) {
			GameEvent e = queue.pop();
			Array<GameEventListener> a = listeners.get(e.getType());
			if(a != null) {
				for(int i = 0; i < a.size; i++) {
					a.get(i).receiveEvent(e);
				}
			}
		}
	}
}
