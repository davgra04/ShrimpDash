package com.teamgraves.shrimpdash.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.teamgraves.shrimpdash.GameScreen;
import com.teamgraves.shrimpdash.components.BoundsComponent;
import com.teamgraves.shrimpdash.components.TextureComponent;
import com.teamgraves.shrimpdash.components.TransformComponent;

public class RenderingSystem extends IteratingSystem {

	static final float FRUSTUM_WIDTH = 16;
	static final float FRUSTUM_HEIGHT = 9;
	static final float PIXELS_TO_METERS = GameScreen.PIXELS_TO_METERS;
	
	private SpriteBatch batch;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;
	private OrthographicCamera cam;
	private ShapeRenderer sr;
	
	public ComponentMapper<TextureComponent> textureM;
	public ComponentMapper<TransformComponent> transformM;
	public ComponentMapper<BoundsComponent> bm;
	
	public boolean drawBounds = false;
	
	public RenderingSystem(SpriteBatch batch) {
		super(Family.all(TextureComponent.class, TransformComponent.class).get());
		
		textureM = ComponentMapper.getFor(TextureComponent.class);
		transformM = ComponentMapper.getFor(TransformComponent.class);
		bm = ComponentMapper.getFor(BoundsComponent.class);
		
		renderQueue = new Array<Entity>();
		
		comparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int) Math.signum(transformM.get(entityB).pos.z - transformM.get(entityA).pos.z);
			}
		};
		
		this.batch = batch;
		
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
		
		sr = new ShapeRenderer();
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		renderQueue.sort(comparator);
		
//		System.out.println(cam.position);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		batch.begin();
		
		for (Entity entity : renderQueue) {
			TextureComponent tex = textureM.get(entity);
			
			if (tex.region == null) {
				continue;
			}
			
			
			TransformComponent t = transformM.get(entity);
			
			float width = tex.region.getRegionWidth();
			float height = tex.region.getRegionHeight();
			float originX = width / 2.0f;
			float originY = height / 2.0f;
			
//			batch.begin();
			batch.draw(tex.region, 
					t.pos.x - originX, t.pos.y - originY, 
					originX, originY, 
					width, height, 
					t.scale.x * PIXELS_TO_METERS, t.scale.y * PIXELS_TO_METERS, 
					MathUtils.radiansToDegrees * t.rotation);
//			batch.end();

		}
		batch.end();
		
		if (drawBounds) {

			for (Entity entity : renderQueue) {			
				if(bm.has(entity)) {
					
					BoundsComponent bounds = bm.get(entity);
					
					Gdx.gl.glEnable(GL20.GL_BLEND);
					sr.begin(ShapeType.Filled);
					sr.setColor(new Color(0, 0, 1, 0.25f));
					sr.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
					sr.end();
					
					sr.begin(ShapeType.Line);
					sr.setColor(new Color(0, 1, 0, 0.5f));
					sr.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
					sr.end();
					
				}
			}
			
		}
		
		renderQueue.clear();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public void toggleBounds() {
		drawBounds = !drawBounds;
	}

}
