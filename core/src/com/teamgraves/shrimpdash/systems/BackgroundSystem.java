package com.teamgraves.shrimpdash.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.teamgraves.shrimpdash.components.BackgroundComponent;
import com.teamgraves.shrimpdash.components.TextureComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;

public class BackgroundSystem extends IteratingSystem {
	private OrthographicCamera cam;
	
	ComponentMapper<TextureComponent> textureM;
	ComponentMapper<TransformComponent> transformM;
	
	public BackgroundSystem() {
		super(Family.all(TextureComponent.class, TransformComponent.class, BackgroundComponent.class).get());

		textureM = ComponentMapper.getFor(TextureComponent.class);
		transformM = ComponentMapper.getFor(TransformComponent.class);
	}
	
	public void setCamera (OrthographicCamera cam) {
		this.cam = cam;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent transform = transformM.get(entity);
		TextureComponent tex = textureM.get(entity);
		
		transform.pos.set(cam.position.x - tex.region.getTexture().getWidth(), cam.position.y, 10.0f);

	}

}
