package com.teamgraves.shrimpdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class MenuScreen extends ScreenAdapter {
	private ShrimpDash game;
	private OrthographicCamera cam;
	
	private int OPTION_PLAY = 0;
	private int OPTION_CONTROLS = 1;
	
	private Array<Vector2> arrowPos;
	private int arrowCurrentPos = 0;
	private GlyphLayout arrowLayout;
	
	private Vector2 playPos;
	private Vector2 controlsPos;
	
	private String playString = "Play";
	private String controlsString = "Controls";
	private String instructionsString = "Z - Select   X - Switch Option";
	private GlyphLayout playLayout;
	private GlyphLayout controlsLayout;
	private GlyphLayout instructionsLayout;
	
	private float bkg_time = 0;
	
	private Vector2 titlePos;
	private Vector2 titleSize;
	private float titleScale;
	private Vector2 titleOrigin;
	private float titleRot = 0f;
	private boolean titleRotAdd = false;
	private float titleRotAmt = -15f;
	
	
	public MenuScreen (ShrimpDash game) {
		this.game = game;
		
		Assets.mainTheme.play();
		
		cam = new OrthographicCamera(800, 450);
		cam.position.set(800/2, 450/2, 0);

		controlsLayout = new GlyphLayout(Assets.font24, controlsString, new Color(1, 1, 1, 1), 0, Align.left, false);
		controlsPos = new Vector2(800/2 - controlsLayout.width/2, 130 - controlsLayout.height*2);

		playLayout = new GlyphLayout(Assets.font24, playString, new Color(1, 1, 1, 1), 0, Align.left, false);
		playPos = new Vector2(controlsPos.x, 130);

		arrowLayout = new GlyphLayout(Assets.font24, ">", new Color(0.859f, 0.106f, 0.196f, 1), 0, Align.left, false);
		arrowPos = new Array<Vector2>();
		arrowPos.add(new Vector2(playPos.x - arrowLayout.width*2, playPos.y));
		arrowPos.add(new Vector2(controlsPos.x - arrowLayout.width*2, controlsPos.y));
		
		instructionsLayout = new GlyphLayout(Assets.font12, instructionsString, new Color(1, 1, 1, 1), 0, Align.left, false);

		titleScale = 0.54f;
		titleSize = new Vector2(Assets.title.getRegionWidth(), Assets.title.getRegionHeight());
		titlePos = new Vector2(88, 30);
		titleOrigin = new Vector2(titlePos.x + titleSize.x*titleScale/2.0f, titlePos.y + titleSize.y*titleScale/2.0f);
		
	}

	
	public void update (float delta) {
		
		if (Gdx.input.isKeyJustPressed(Keys.Z)) {
			if (arrowCurrentPos == OPTION_PLAY) {
				Assets.gameStartSound.play(1);
				Assets.mainTheme.stop();
				game.setScreen(new LevelOneScreen(game));
			}
			else if (arrowCurrentPos == OPTION_CONTROLS) {
				Assets.gameStartSound2.play();
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.X)) {
			Assets.changeOptionSound.play(1);
			arrowCurrentPos = (arrowCurrentPos + 1) % arrowPos.size;
		}
		
		bkg_time += delta;
//		System.out.println(bkg_time);
		if (bkg_time > 0.50238f) {
			bkg_time = 0;
			if (titleRotAdd) {
				titleRot += titleRotAmt;
			}
			else {
				titleRot -= titleRotAmt;				
			}
			titleRotAdd = !titleRotAdd;
			
		}
	}
	
	public void draw (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		
		game.batch.setProjectionMatrix(cam.combined);
		
		game.batch.begin();
		
//		TextureRegion tex = Assets.falling_bkg.getKeyFrame(bkg_time, true);
//		float scale = 800.0f / tex.getRegionWidth();
//		game.batch.draw(tex, 0, 0, 0, 0, tex.getRegionWidth(), tex.getRegionHeight(), scale, scale, 0);
		
//		float scale = 800.0f / Assets.title_bkg.getWidth();
//		game.batch.draw(Assets.title_bkg, 0, 0, 0, 0, Assets.title_bkg.getWidth(), Assets.title_bkg.getHeight(), scale, scale, 0);
		game.batch.draw(Assets.title_bkg, 0, 0, 800, 450);
		
		game.batch.draw(Assets.title, 
				titlePos.x, titlePos.y,
				titleOrigin.x, titleOrigin.y,
				titleSize.x*titleScale, titleSize.y*titleScale,
				titleScale, titleScale, 
				titleRot);
		game.batch.draw(Assets.shrimp_idle1.getFrame(), 430, 120, Assets.shrimp_idle1.width*4, Assets.shrimp_idle1.height*4);
		
		Assets.font24.draw(game.batch, playLayout, playPos.x, playPos.y);
		Assets.font24.draw(game.batch, controlsLayout, controlsPos.x, controlsPos.y);
		Assets.font12.draw(game.batch, instructionsLayout, 800/2 - instructionsLayout.width/2, instructionsLayout.height + 5);
		Assets.font24.draw(game.batch, arrowLayout, arrowPos.get(arrowCurrentPos).x, arrowPos.get(arrowCurrentPos).y);
		
		game.batch.end();
	}
	
	@Override
	public void render (float delta) {
		update(delta);
		draw(delta);
	}
}
