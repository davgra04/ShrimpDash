package com.teamgraves.shrimpdash.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.teamgraves.shrimpdash.components.BoundsComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;

public class BoundsSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<BoundsComponent> bm;
	
	public BoundsSystem() {
		super(Family.all(TransformComponent.class,
				BoundsComponent.class).get()
				);

		tm = ComponentMapper.getFor(TransformComponent.class);
		bm = ComponentMapper.getFor(BoundsComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent pos = tm.get(entity);
		BoundsComponent bounds = bm.get(entity);

		bounds.bounds.x = pos.pos.x - bounds.bounds.width * 0.5f;
		bounds.bounds.y = pos.pos.y - bounds.bounds.height * 0.5f;

	}

}
