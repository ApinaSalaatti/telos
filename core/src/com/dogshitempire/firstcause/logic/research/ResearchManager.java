package com.dogshitempire.firstcause.logic.research;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.dogshitempire.firstcause.actors.GameActor;

public class ResearchManager {
	private GameActor player;
	
	private Array<Research> allResearch;
	private Array<String> availableResearch;
	private Array<String> doneResearch;
	
	public ResearchManager() {
		allResearch = new Array<Research>();
		availableResearch = new Array<String>();
		doneResearch = new Array<String>();
		
		allResearch.add(new Research("kakka", 1111) {
			@Override
			public void onResearch() {
				availableResearch.add("kakka2");
			}
		});
		
		allResearch.add(new Research("kakka2", 21111) {
			@Override
			public void onResearch() {
				Gdx.app.log("RESEARCH!!", player.getName());
			}
		});
	}
	
	public boolean researchDone(String research) {
		if(doneResearch.contains(research, false)) {
			return true;
		}
		
		return false;
	}
	
	public boolean researchAvailable(String research) {
		if(availableResearch.contains(research, false)) {
			return true;
		}
		
		return false;
	}
	
	public Research getResearch(String research) {
		for(int i = 0; i < allResearch.size; i++) {
			Research r = allResearch.get(i);
			if(r.getName().equals(research)) {
				return r;
			}
		}
		
		return null;
	}
	public Array<Research> getAllResearch() {
		return allResearch;
	}
	
	public void doResearch(String research) {
		doneResearch.add(research);
	}
}
