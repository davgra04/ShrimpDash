package com.teamgraves.shrimpdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Texture title;
	public static Texture playButton;
	public static Texture controlsButton;
	public static AnimatedSprite shrimp_idle1;
	
	public static Sound selectSound;
	
	public static void load () {
		title = new Texture("title_placeholder.png");
		playButton = new Texture("play_button_placeholder.png");
		controlsButton = new Texture("controls_button_placeholder.png");
		
		shrimp_idle1 = new AnimatedSprite("shrimpe_idle1_sheet.png", 4, 4, 0.133f);
		
		selectSound = Gdx.audio.newSound(Gdx.files.internal("select_placeholder.wav"));
	}
}
