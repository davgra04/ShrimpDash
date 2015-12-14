package com.teamgraves.shrimpdash.components;

import com.badlogic.ashley.core.Component;

public class ShrimpComponent implements Component {
	public static final int STATE_IDLE = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_BOOST = 2;
	public static final int STATE_JUMP = 3;
	public static final int STATE_STUCK = 4;
	public static final int STATE_SMASHED = 5;
	public static final float JUMP_VELOCITY = 12f;
	public static final float RUN_VELOCITY = 8f;
	public static final float BOOST_MULTIPLIER = 1.5f;
	public static final float WIDTH = 1.0f;
	public static final float HEIGHT = 0.6f;
	
	public float distance = 0.0f;
}
