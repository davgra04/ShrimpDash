package com.teamgraves.shrimpdash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShrimpDash extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		Gdx.gl.glEnable(GL20.GL_BLEND);
		Assets.load();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
	
//	public void resize (int width, int height) {
//		Vector2 size = Scaling.fit.apply(800, 450, width, height);
//		int viewportX = (int)(width - size.x) / 2;
//		int viewportY = (int)(height - size.y) / 2;
//		int viewportWidth = (int)size.x;
//		int viewportHeight = (int)size.y;
//		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
//		stage.setViewport(800, 450, true, viewportX, viewportY, viewportWidth, viewportHeight);
//		
//	}
}
