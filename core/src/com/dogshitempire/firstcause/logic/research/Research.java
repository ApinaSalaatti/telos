package com.dogshitempire.firstcause.logic.research;

public abstract class Research {
	private String name;
	private int cost;
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Research(String n, int c) {
		name = n;
		cost = c;
	}
	
	public abstract void onResearch();
}
