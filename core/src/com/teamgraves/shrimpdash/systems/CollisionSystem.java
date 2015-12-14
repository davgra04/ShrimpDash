package com.teamgraves.shrimpdash.systems;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.teamgraves.shrimpdash.World;
import com.teamgraves.shrimpdash.components.BoundsComponent;
import com.teamgraves.shrimpdash.components.EnvironmentComponent;
import com.teamgraves.shrimpdash.components.MovementComponent;
import com.teamgraves.shrimpdash.components.ShrimpComponent;
import com.teamgraves.shrimpdash.components.StateComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;

public class CollisionSystem extends EntitySystem {
	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<BoundsComponent> bm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<MovementComponent> mm;
	
	private Engine engine;
	private World world;
	private Random rand = new Random();
	private ImmutableArray<Entity> shrimps;
	private ImmutableArray<Entity> environmentEntities;
	
	public CollisionSystem(World world) {
		this.world = world;

		tm = ComponentMapper.getFor(TransformComponent.class);
		bm = ComponentMapper.getFor(BoundsComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);

	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		shrimps = engine.getEntitiesFor(Family.all(ShrimpComponent.class, BoundsComponent.class, TransformComponent.class, StateComponent.class).get());
		environmentEntities = engine.getEntitiesFor(Family.all(EnvironmentComponent.class, BoundsComponent.class, TransformComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		ShrimpSystem shrimpSystem = engine.getSystem(ShrimpSystem.class);
		
		for (Entity shrimp : shrimps) {
			StateComponent shrimpState = sm.get(shrimp);
			MovementComponent shrimpMov = mm.get(shrimp);
			BoundsComponent shrimpBounds = bm.get(shrimp);
			TransformComponent shrimpPos = tm.get(shrimp);
			
//			System.out.println(shrimpState.get());
			
			for (Entity envObj : environmentEntities) {
				TransformComponent envPos = tm.get(envObj);
				BoundsComponent envBounds = bm.get(envObj);
				
				if (shrimpBounds.bounds.overlaps(envBounds.bounds)) {
					float w = 0.5f * (shrimpBounds.bounds.width + envBounds.bounds.width);
					float h = 0.5f * (shrimpBounds.bounds.height + envBounds.bounds.height);
					float dx = (shrimpBounds.bounds.x + shrimpBounds.bounds.width/2) - (envBounds.bounds.x + envBounds.bounds.width/2);
					float dy = (shrimpBounds.bounds.y + shrimpBounds.bounds.height/2) - (envBounds.bounds.y + envBounds.bounds.height/2);
//					float dx = shrimpPos.pos.x - envPos.pos.x;
//					float dy = shrimpPos.pos.y - envPos.pos.y;
					float wy = w * dy;
					float hx = h * dx;
					
//					System.out.println("wy: " + wy + "        hx: " + hx + "           absdiff: " + (Math.abs(wy - hx)));
					
					if (wy > hx) {
						if (wy > -hx) {
							// Top Collision
							System.out.println("TOP COLLISION");
							if (shrimpMov.velocity.y <= 0) {
								shrimpState.set(ShrimpComponent.STATE_RUNNING);
								shrimpMov.velocity.y = 0;
//								shrimpPos.pos.y = envPos.pos.y + envBounds.bounds.height/2 + shrimpBounds.bounds.height/2;		
								shrimpPos.pos.y = envBounds.bounds.y + envBounds.bounds.height + shrimpBounds.bounds.height/2 + 0.05f;
							}
						}
						else {
							// Left Collision
							if (shrimpState.get() != ShrimpComponent.STATE_JUMP) {
								shrimpState.set(ShrimpComponent.STATE_IDLE);								
							}
							shrimpMov.velocity.x = 0;
//							shrimpPos.pos.x = envPos.pos.x - envBounds.bounds.width/2 - shrimpBounds.bounds.width/2;
							shrimpPos.pos.x = envBounds.bounds.x - envBounds.bounds.width/2 - shrimpBounds.bounds.width/2;
						}
					}
					else {
						if (wy > -hx) {
							// Right Collision
						}
						else {
							// Bottom Collision
						}
					}

//					if (Math.abs(wy - hx) > 0.1) {
//						
//					}
				}
			}
		}
	}
}
