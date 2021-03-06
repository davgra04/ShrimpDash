package com.teamgraves.shrimpdash.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
	private int state = 0;
	public float time = 0.0f;
	
	public int get() {
		return state;
	}
	
	public void set (int newState) {
		if(newState != state) {
			state = newState;
			time = 0.0f;			
		}
	}
}
