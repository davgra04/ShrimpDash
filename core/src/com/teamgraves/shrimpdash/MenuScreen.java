package com.teamgraves.shrimpdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends ScreenAdapter {
	ShrimpDash game;
	OrthographicCamera cam;
	
	Rectangle playBounds;
	Vector2 playPos;
	Rectangle controlsBounds;
	Vector2 controlsPos;
	
	Vector2 titlePos;
	
	Vector3 touchPoint;
	
	public MenuScreen (ShrimpDash game) {
		this.game = game;
		
		cam = new OrthographicCamera(800, 450);
		cam.position.set(800/2, 450/2, 0);

		playPos = new Vector2(200, 40);
		playBounds = new Rectangle(playPos.x, playPos.y, Assets.playButton.getWidth()/2, Assets.playButton.getHeight()/2);
		controlsPos = new Vector2(410, 52);
		controlsBounds = new Rectangle(controlsPos.x, controlsPos.y, Assets.controlsButton.getWidth()/2, Assets.controlsButton.getHeight()/2);

		titlePos = new Vector2((800-Assets.title.getWidth()*3/4)/2, 450 - Assets.title.getHeight()*3/4 - 20);
		
		touchPoint = new Vector3();
	}
	
	public void update () {
		if (Gdx.input.justTouched()) {
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				System.out.println("PLAY!");
				Assets.selectSound.play(1);
			}
			if (controlsBounds.contains(touchPoint.x, touchPoint.y)) {
				System.out.println("CONTROLS!");
				Assets.selectSound.play(1);
			}
		}
	}
	
	public void draw () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		
		game.batch.begin();
		game.batch.draw(Assets.title, titlePos.x, titlePos.y, Assets.title.getWidth()*3/4, Assets.title.getHeight()*3/4);
		game.batch.draw(Assets.playButton, playPos.x, playPos.y, Assets.playButton.getWidth()*1/2, Assets.playButton.getHeight()*1/2);
		game.batch.draw(Assets.controlsButton, controlsPos.x, controlsPos.y, Assets.controlsButton.getWidth()/2, Assets.controlsButton.getHeight()/2);
		game.batch.draw(Assets.shrimp_idle1.getFrame(), 490, 75, Assets.shrimp_idle1.width*5, Assets.shrimp_idle1.height*5);
		game.batch.end();
		
	}
	
	@Override
	public void render (float delta) {
		update();
		draw();
	}
}
