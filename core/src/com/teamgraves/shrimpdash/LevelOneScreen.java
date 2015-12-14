package com.teamgraves.shrimpdash;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.teamgraves.shrimpdash.systems.AnimationSystem;
import com.teamgraves.shrimpdash.systems.BackgroundSystem;
import com.teamgraves.shrimpdash.systems.BoundsSystem;
import com.teamgraves.shrimpdash.systems.CameraSystem;
import com.teamgraves.shrimpdash.systems.CollisionSystem;
import com.teamgraves.shrimpdash.systems.GravitySystem;
import com.teamgraves.shrimpdash.systems.MovementSystem;
import com.teamgraves.shrimpdash.systems.RenderingSystem;
import com.teamgraves.shrimpdash.systems.ShrimpSystem;
import com.teamgraves.shrimpdash.systems.StateSystem;

public class LevelOneScreen extends ScreenAdapter {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	
	public static final float PIXELS_TO_METERS = 1.0f / 32.0f;
	
	ShrimpDash game;
	
	OrthographicCamera guiCam;
	World world;
	
	Engine engine;
//	private GlyphLayout layout = new GlyphLayout();
	
	private int state;
	
	private GlyphLayout gameRunningLayout;
	
	public LevelOneScreen(ShrimpDash game) {
		this.game = game;
		
		state = GAME_READY;
		guiCam = new OrthographicCamera(800, 450);
		guiCam.position.set(800/2, 450/2, 0);
		
		engine = new Engine();
		
		world = new World(engine);
		
		//TODO add engines to system
		engine.addSystem(new StateSystem());
		engine.addSystem(new ShrimpSystem(world));
		engine.addSystem(new CameraSystem());
		engine.addSystem(new BackgroundSystem());
		engine.addSystem(new GravitySystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new BoundsSystem());
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new CollisionSystem(world));
		engine.addSystem(new RenderingSystem(game.batch));
		
		world.create();

		gameRunningLayout = new GlyphLayout(Assets.font24, "Game Running!", new Color(0, 0.5f, 1, 1), 0, Align.left, false);
		
		pauseSystems();
	}
	
	public void update (float delta) {
		
		engine.update(delta);
		
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(delta);
			break;
		}
	}
	
	private void updateReady () {
		if (Gdx.input.isKeyJustPressed(Keys.Z) || Gdx.input.isKeyJustPressed(Keys.X)) {
			Assets.gameStartSound.play(1);
//			Assets.mainThemeFast.play();
			Assets.scuttleTheme.play();
			state = GAME_RUNNING;
			resumeSystems();
		}
	}
	
	private void updateRunning (float deltaTime) {
		
		if (Gdx.input.isKeyJustPressed(Keys.Z)) {
			engine.getSystem(ShrimpSystem.class).setJump();
		}
		
		if (Gdx.input.isKeyPressed(Keys.X)) {
			engine.getSystem(ShrimpSystem.class).setSlowDown(true);
		}
		else {
			engine.getSystem(ShrimpSystem.class).setSlowDown(false);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.B)) {
			engine.getSystem(RenderingSystem.class).toggleBounds();
		}
	}
	
	private void pauseSystems() {
		engine.getSystem(ShrimpSystem.class).setProcessing(false);
		engine.getSystem(CameraSystem.class).setProcessing(false);
		engine.getSystem(BackgroundSystem.class).setProcessing(false);
		engine.getSystem(GravitySystem.class).setProcessing(false);
		engine.getSystem(MovementSystem.class).setProcessing(false);
		engine.getSystem(BoundsSystem.class).setProcessing(false);
		engine.getSystem(StateSystem.class).setProcessing(false);
		engine.getSystem(AnimationSystem.class).setProcessing(false);
		engine.getSystem(CollisionSystem.class).setProcessing(false);
		engine.getSystem(RenderingSystem.class).setProcessing(false);
	}
	
	private void resumeSystems() {
		engine.getSystem(ShrimpSystem.class).setProcessing(true);
		engine.getSystem(CameraSystem.class).setProcessing(true);
		engine.getSystem(BackgroundSystem.class).setProcessing(true);
		engine.getSystem(GravitySystem.class).setProcessing(true);
		engine.getSystem(MovementSystem.class).setProcessing(true);
		engine.getSystem(BoundsSystem.class).setProcessing(true);
		engine.getSystem(StateSystem.class).setProcessing(true);
		engine.getSystem(AnimationSystem.class).setProcessing(true);
		engine.getSystem(CollisionSystem.class).setProcessing(true);
		engine.getSystem(RenderingSystem.class).setProcessing(true);
	}
	
	public void drawUI () {
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		game.batch.setProjectionMatrix(guiCam.combined);
		game.batch.begin();
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		}
		game.batch.end();
	}
	
	private void presentReady () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Assets.font24.draw(game.batch, "Press Any Key to Start!", 800/2, 450/2, 5, Align.center, false);
	}
	
	private void presentRunning () {
//		Gdx.gl.glClearColor(0, 1, 1, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Assets.font24.draw(game.batch, gameRunningLayout, 10, 440);
//		Assets.font24.draw(game.batch, "Game Running!", 10, 440, 5, Align.bottomLeft, false);
	}
	
	
	
	
	@Override
	public void render (float delta) {
		update(delta);
		drawUI();
	}
	
}
