package com.teamgraves.shrimpdash.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.teamgraves.shrimpdash.components.CameraComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;

public class CameraSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<CameraComponent> cm;
	
	public CameraSystem() {
		super(Family.all(CameraComponent.class).get());
		
//		System.out.println("CAMERA SYSTEM CREATED");

		tm = ComponentMapper.getFor(TransformComponent.class);
		cm = ComponentMapper.getFor(CameraComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CameraComponent cam = cm.get(entity);
//		System.out.println("UPDATING CAMERA");
		
		if (cam.target == null) {
//			System.out.println("CAMERA IS NULL");
			return;
		}
		
		TransformComponent target = tm.get(cam.target);
		
		if (target == null) {
//			System.out.println("TARGET IS NULL");
			return;
		}
		
		cam.cam.position.x = Math.max(cam.cam.position.x, target.pos.x);
//		cam.cam.position.x = target.pos.x;
		cam.cam.position.y = 4.5f;
//		cam.cam.position.y = target.pos.y;
//		System.out.println("CAM: " + cam.cam.position);

	}

}
