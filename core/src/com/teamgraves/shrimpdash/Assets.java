package com.teamgraves.shrimpdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;

public class Assets {
	public static TextureRegion title;
	public static Texture playButton;
	public static Texture controlsButton;
	
	public static AnimatedSprite shrimp_idle1;
	public static Animation shrimp_idle;
	public static Animation shrimp_run;
	public static Animation shrimp_boost;
	public static Animation shrimp_jump;

	public static Array<Texture> test_counters;
	public static Array<Texture> customer_counters;
	
	public static Texture bkg;
	public static Texture title_bkg;
	public static Animation falling_bkg;
	
	public static Texture person;
	public static Texture test_obstacle;
	
	public static Sound selectSound;
	public static Sound gameStartSound;
	public static Sound gameStartSound2;
	public static Sound changeOptionSound;
	public static Sound changeOptionSound2;
	public static Sound jumpSound;

	public static Music mainTheme;
	public static Music mainThemeFast;
	public static Music scuttleTheme;

	public static BitmapFont font12;
	public static BitmapFont font24;
	
	public static void load () {
		// Main Menu Assets
		title = new TextureRegion( new Texture("sprites/title.png") );
//		playButton = new Texture("play_button_placeholder.png");
//		controlsButton = new Texture("controls_button_placeholder.png");
		
		// Shrimp Assets
		shrimp_idle1 = new AnimatedSprite("sprites/shrimp/shrimpe_idle1_sheet.png", 4, 4, 0.133f);
		shrimp_idle = loadAnimFromSheet("sprites/shrimp/shrimpe_idle1_sheet.png", 4, 4, 0.133f);
		shrimp_idle.setPlayMode(Animation.PlayMode.LOOP);
		shrimp_run = loadAnimFromSheet("sprites/shrimp/shrimp_run_sheet.png", 1, 2, 0.1f);
		shrimp_run.setPlayMode(Animation.PlayMode.LOOP);
		shrimp_boost = loadAnimFromSheet("sprites/shrimp/shrimp_boost_sheet.png", 1, 2, 0.8f);
		shrimp_boost.setPlayMode(Animation.PlayMode.LOOP);
		shrimp_jump = loadAnimFromSheet("sprites/shrimp/shrimp_jump_sheet.png", 2, 2, 0.09f);
		shrimp_jump.setPlayMode(Animation.PlayMode.LOOP);
		
		// Counter Assets
		test_counters = new Array<Texture>();
		test_counters.add(new Texture("counter_placeholder/counter_placeholder_1.png"));
		test_counters.add(new Texture("counter_placeholder/counter_placeholder_2.png"));
		test_counters.add(new Texture("counter_placeholder/counter_placeholder_3.png"));
		test_counters.add(new Texture("counter_placeholder/counter_placeholder_4.png"));
		test_counters.add(new Texture("counter_placeholder/counter_placeholder_5.png"));
		test_counters.add(new Texture("counter_placeholder/counter_placeholder_6.png"));
		customer_counters = new Array<Texture>();
		customer_counters.add(new Texture("sprites/counters/customer counter 1.png"));
		customer_counters.add(new Texture("sprites/counters/customer counter 2.png"));
		customer_counters.add(new Texture("sprites/counters/customer counter 3.png"));
		customer_counters.add(new Texture("sprites/counters/customer counter 4.png"));
		
		// Background Assets
		bkg = new Texture("bkg_placeholder/bkg_placeholder_double.png");
		title_bkg = new Texture("sprites/backgrounds/title background.png");
		falling_bkg = loadAnimFromSheet("sprites/backgrounds/falling_bkg_sheet.png", 9, 9, 1/60.0f);
		
		
		// World Assets
		person = new Texture("bkg_placeholder/person.png");
		test_obstacle = new Texture("sprites/placeholder_obstacle.png");
		
		// Sounds
		selectSound = Gdx.audio.newSound(Gdx.files.internal("sfx/select_placeholder.wav"));
		gameStartSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gamestart_placeholder.wav"));
		gameStartSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/gamestart_placeholder2.wav"));
		changeOptionSound = Gdx.audio.newSound(Gdx.files.internal("sfx/changeoption_placeholder.wav"));
		changeOptionSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/changeoption_placeholder2.wav"));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sfx/shrimp_jump.wav"));
		
		// Music
		mainTheme = Gdx.audio.newMusic(Gdx.files.internal("music/ChipSambaLoop.mp3"));
		mainTheme.setLooping(true);
		mainThemeFast = Gdx.audio.newMusic(Gdx.files.internal("music/ChipSambaLoopFast.mp3"));
		mainThemeFast.setLooping(true);
		scuttleTheme = Gdx.audio.newMusic(Gdx.files.internal("music/ShrimpScuttleLoop.mp3"));
		scuttleTheme.setLooping(true);
		
		// Fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		font12 = generator.generateFont(parameter);
		parameter.size = 24;
		font24 = generator.generateFont(parameter);
		generator.dispose();
	}
	
	private static Animation loadAnimFromSheet (String sheet, int rows, int cols, float frameLength) {
		Texture spriteSheet = new Texture(sheet);
		int width = spriteSheet.getWidth()/cols;
		int height = spriteSheet.getHeight()/rows;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, width, height);
		TextureRegion[] spriteFrames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				spriteFrames[index++] = tmp[i][j];
			}
		}
		return new Animation(frameLength, spriteFrames);
	}
}
