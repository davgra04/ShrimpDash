package com.teamgraves.shrimpdash;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.teamgraves.shrimpdash.components.AnimationComponent;
import com.teamgraves.shrimpdash.components.BackgroundComponent;
import com.teamgraves.shrimpdash.components.BoundsComponent;
import com.teamgraves.shrimpdash.components.CameraComponent;
import com.teamgraves.shrimpdash.components.EnvironmentComponent;
import com.teamgraves.shrimpdash.components.GravityComponent;
import com.teamgraves.shrimpdash.components.MovementComponent;
import com.teamgraves.shrimpdash.components.ShrimpComponent;
import com.teamgraves.shrimpdash.components.StateComponent;
import com.teamgraves.shrimpdash.components.TextureComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;
import com.teamgraves.shrimpdash.systems.BackgroundSystem;
import com.teamgraves.shrimpdash.systems.RenderingSystem;

public class World {
	
	public static final float WORLD_WIDTH = 16 * 10;
	public static final float WORLD_HEIGHT = 9;
	public static final float PIXELS_TO_METERS = GameScreen.PIXELS_TO_METERS;
	
	public static final Vector2 gravity = new Vector2(0, -40);

	public final Random rand;
	
	private Engine engine;
	
	public World (Engine engine) {
		this.engine = engine;
		this.rand = new Random();
	}
	
	public void create () {
		//TODO: create shrimp
		Entity shrimp = createShrimp();
		//TODO: create camera
		createCamera(shrimp);
		//TODO: create background
//		createBackground();
		//TODO: generate level
		generateLevel();
	}
	
	private void generateLevel() {
		
		// Generate Counters
		int num_counters = 1000;
		int last_index = -1;
		int counter_index = -1;
		for(int i = 0; i < num_counters; i++) {
			while(counter_index == last_index) {
				counter_index = rand.nextInt(Assets.counters.size);
			}
			
			Entity entity = new Entity();
			
			TransformComponent pos = new TransformComponent();
			TextureComponent texture = new TextureComponent();
			BoundsComponent bounds = new BoundsComponent();
			EnvironmentComponent env = new EnvironmentComponent();
			
			texture.region = new TextureRegion(Assets.counters.get(counter_index));
			
			pos.scale.scl(0.4f);
//			pos.pos.set(i * 9.15f + 5.0f, 1.0f, 100.0f + num_counters - i);
			pos.pos.set(PIXELS_TO_METERS * pos.scale.x * (i * (texture.region.getRegionWidth() - 67) + 1.0f), 
						PIXELS_TO_METERS * 1.0f, 
						100.0f + num_counters - i);
			
			bounds.bounds.width = PIXELS_TO_METERS * pos.scale.x * (texture.region.getRegionWidth() - 67);
			bounds.bounds.height = PIXELS_TO_METERS * pos.scale.x * (texture.region.getRegionHeight() - 36*2);
			
			entity.add(pos);
			entity.add(texture);
			entity.add(bounds);
			entity.add(env);
			
			engine.addEntity(entity);
			
			last_index = counter_index;
		}
		
		// Generate obstacles
		float test_scale = 0.1f;
		for(int i = 0; i < 100; i++) {
			Entity entity = new Entity();
			
			TransformComponent pos = new TransformComponent();
			TextureComponent tex = new TextureComponent();
			BoundsComponent bounds = new BoundsComponent();
			EnvironmentComponent env = new EnvironmentComponent();
			
			tex.region = new TextureRegion(Assets.test_obstacle);
			
			pos.scale.scl(test_scale);

			bounds.bounds.width = PIXELS_TO_METERS * pos.scale.x * (tex.region.getRegionWidth() - 159*2);
			bounds.bounds.height = PIXELS_TO_METERS * pos.scale.x * (tex.region.getRegionHeight()) / 2;
			
			float xpos = 15 + rand.nextFloat()*5f + 10*i;
			float ypos = 1.9f;
			
			pos.pos.set(xpos, ypos, -ypos - bounds.bounds.height/2);

			entity.add(pos);
			entity.add(tex);
			entity.add(bounds);
			entity.add(env);
			
			engine.addEntity(entity);			
		}
		
		// Generate people
		int num_people = 500;
		for(int i = 0; i < num_people; i++) {
			Entity entity = new Entity();

			TransformComponent pos = new TransformComponent();
			TextureComponent texture = new TextureComponent();
			
			pos.pos.set(i * 3.0f + 2.0f * rand.nextFloat(), 2.0f + rand.nextFloat()*1.5f, 2000.0f);
			pos.scale.scl(1.75f - 0.25f*rand.nextFloat());
			
			texture.region = new TextureRegion(Assets.person);
			
			entity.add(pos);
			entity.add(texture);
			
			engine.addEntity(entity);
			
		}
	}
	
	private Entity createShrimp () {
		Entity entity = new Entity();
		
		AnimationComponent animation = new AnimationComponent();
		ShrimpComponent shrimp = new ShrimpComponent();
		BoundsComponent bounds = new BoundsComponent();
		GravityComponent gravity = new GravityComponent();
		MovementComponent movement = new MovementComponent();
		TransformComponent position = new TransformComponent();
		StateComponent state = new StateComponent();
		TextureComponent texture = new TextureComponent();

		animation.animations.put(ShrimpComponent.STATE_IDLE, Assets.shrimp_idle);
		animation.animations.put(ShrimpComponent.STATE_JUMP, Assets.shrimp_jump);
		animation.animations.put(ShrimpComponent.STATE_RUNNING, Assets.shrimp_run);
		
		bounds.bounds.width = ShrimpComponent.WIDTH;
		bounds.bounds.height = ShrimpComponent.HEIGHT;
		
		position.pos.set(2.0f, 2f, 0.0f);
		
		movement.velocity.x = ShrimpComponent.RUN_VELOCITY;
		movement.velocity.y = ShrimpComponent.JUMP_VELOCITY;

//		state.set(ShrimpComponent.STATE_IDLE);
		state.set(ShrimpComponent.STATE_JUMP);
//		state.set(ShrimpComponent.STATE_RUNNING);

		entity.add(animation);
		entity.add(shrimp);
		entity.add(bounds);
		entity.add(gravity);
		entity.add(movement);
		entity.add(position);
		entity.add(state);
		entity.add(texture);
		
		engine.addEntity(entity);
		
		return entity;
	}
	
	private void createCamera (Entity target) {
		Entity entity = new Entity();
		
		CameraComponent camera = new CameraComponent();
		camera.cam = engine.getSystem(RenderingSystem.class).getCamera();
		camera.target = target;
		
		entity.add(camera);
		
		engine.addEntity(entity);
	}
	
	private void createBackground() {
		Entity entity = new Entity();
		
		BackgroundComponent bkg = new BackgroundComponent();
		TransformComponent pos = new TransformComponent();
		TextureComponent tex = new TextureComponent();
		
		tex.region = new TextureRegion(Assets.bkg);
		
		OrthographicCamera cam = engine.getSystem(RenderingSystem.class).getCamera();
		
		engine.getSystem(BackgroundSystem.class).setCamera(cam);

//		pos.scale.set(new Vector2(5.0f, 5.0f));
		pos.pos.y = cam.position.y;
		pos.pos.x = cam.position.x + 5;
		
		entity.add(bkg);
		entity.add(pos);
		entity.add(tex);
		
		engine.addEntity(entity);
	}
}
