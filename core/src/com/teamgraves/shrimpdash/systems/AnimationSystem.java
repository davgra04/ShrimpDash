package com.teamgraves.shrimpdash.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.teamgraves.shrimpdash.components.AnimationComponent;
import com.teamgraves.shrimpdash.components.StateComponent;
import com.teamgraves.shrimpdash.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {
	private ComponentMapper<TextureComponent> tm;
	private ComponentMapper<AnimationComponent> am;
	private ComponentMapper<StateComponent> sm;

	public AnimationSystem() {
		super(Family.all(TextureComponent.class,
				AnimationComponent.class,
				StateComponent.class).get()
				);

		tm = ComponentMapper.getFor(TextureComponent.class);
		am = ComponentMapper.getFor(AnimationComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TextureComponent tex = tm.get(entity);
		AnimationComponent anim = am.get(entity);
		StateComponent state = sm.get(entity);
		
		Animation animation = anim.animations.get(state.get());
		
		if (animation != null) {
			tex.region = animation.getKeyFrame(state.time);
		}
		
		state.time += deltaTime;
	}

}
