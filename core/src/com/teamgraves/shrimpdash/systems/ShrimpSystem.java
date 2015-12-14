package com.teamgraves.shrimpdash.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.teamgraves.shrimpdash.Assets;
import com.teamgraves.shrimpdash.World;
import com.teamgraves.shrimpdash.components.MovementComponent;
import com.teamgraves.shrimpdash.components.ShrimpComponent;
import com.teamgraves.shrimpdash.components.StateComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;

public class ShrimpSystem extends IteratingSystem {
	private static final Family family = Family.all(ShrimpComponent.class,
													StateComponent.class,
													TransformComponent.class,
													MovementComponent.class).get();
	
	private float accelX = 0.0f;
	private World world;
	
	private boolean setJump = false;
	private boolean slowDown = false;
	private boolean speedUp = false;
	private float slowRate = 0.2f;
	private float speedRate = 0.2f;
	
	private ComponentMapper<ShrimpComponent> shrimpM;
	private ComponentMapper<StateComponent> stateM;
	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<MovementComponent> mm;

	public ShrimpSystem(World world) {
		super(family);
		
		this.world = world;
		
		shrimpM = ComponentMapper.getFor(ShrimpComponent.class);
		stateM = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		TransformComponent t = tm.get(entity);
		StateComponent state = stateM.get(entity);
		MovementComponent mov = mm.get(entity);
		ShrimpComponent shrimp = shrimpM.get(entity);

		if (setJump) {
			setJump = false;
			shrimpJump(entity);
		}
		if (slowDown) {
			shrimpSlowDown(entity);
		}
		else if (speedUp){
			shrimpSpeedUp(entity);
		}
		
		t.pos.z = -t.pos.y;
		
		if(t.pos.y < -5) {
			t.pos.y = 5;
		}
		
	}
	
	public void setJump() {
		setJump = true;
	}
	
	public void shrimpJump(Entity entity) {
		if (!family.matches(entity)) return;
		
		StateComponent state = stateM.get(entity);
		MovementComponent mov = mm.get(entity);
		
		if (state.get() == ShrimpComponent.STATE_RUNNING || state.get() == ShrimpComponent.STATE_IDLE){
			Assets.jumpSound.play(1.0f);
			mov.velocity.y = ShrimpComponent.JUMP_VELOCITY;
			state.set(ShrimpComponent.STATE_JUMP);			
		}
		
	}
	
	public void shrimpSlowDown(Entity entity) {
		if (!family.matches(entity)) return;
		
		StateComponent state = stateM.get(entity);
		MovementComponent mov = mm.get(entity);
		
		if (state.get() == ShrimpComponent.STATE_RUNNING || state.get() == ShrimpComponent.STATE_IDLE){
			if (mov.velocity.x > slowRate) {
				mov.velocity.x -= slowRate;
			}
			else {
				mov.velocity.x = 0;
				state.set(ShrimpComponent.STATE_IDLE);
			}		
		}
		
	}	public void shrimpSpeedUp(Entity entity) {
		if (!family.matches(entity)) return;
		
		StateComponent state = stateM.get(entity);
		MovementComponent mov = mm.get(entity);
		
//		System.out.println(state.get());
		
//		if (state.get() != ShrimpComponent.STATE_RUNNING || state.get() != ShrimpComponent.STATE_JUMP) {
		if (state.get() == ShrimpComponent.STATE_IDLE) {
			state.set(ShrimpComponent.STATE_RUNNING);
		}
		
		
		if (mov.velocity.x < ShrimpComponent.RUN_VELOCITY - speedRate) {
			mov.velocity.x = mov.velocity.x + speedRate;
		}
		else {
			mov.velocity.x = ShrimpComponent.RUN_VELOCITY;
			speedUp = false;
		}
		
		
	}
	
	public void setSlowDown(boolean isSlowingDown) {
		slowDown = isSlowingDown;
		speedUp = !isSlowingDown;
	}

}
