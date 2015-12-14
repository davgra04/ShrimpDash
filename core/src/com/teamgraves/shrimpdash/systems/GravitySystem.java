package com.teamgraves.shrimpdash.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.teamgraves.shrimpdash.World;
import com.teamgraves.shrimpdash.components.GravityComponent;
import com.teamgraves.shrimpdash.components.MovementComponent;
import com.teamgraves.shrimpdash.components.ShrimpComponent;
import com.teamgraves.shrimpdash.components.StateComponent;

public class GravitySystem extends IteratingSystem {
	ComponentMapper<MovementComponent> mm;
	ComponentMapper<StateComponent> sm;
	
	public GravitySystem() {
		super(Family.all(GravityComponent.class, MovementComponent.class, ShrimpComponent.class, StateComponent.class).get());

		mm = ComponentMapper.getFor(MovementComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MovementComponent mov = mm.get(entity);
		StateComponent state = sm.get(entity);

//		if (state.get() == ShrimpComponent.STATE_JUMP){			
			mov.velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
//		}
	}

}
