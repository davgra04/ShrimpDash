package com.teamgraves.shrimpdash.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoundsComponent implements Component {
	public final Rectangle bounds = new Rectangle();
	public final Vector2 offset = new Vector2(0, 0);
}
