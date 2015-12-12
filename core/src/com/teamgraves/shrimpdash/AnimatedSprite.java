package com.teamgraves.shrimpdash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSprite {
	public int rows;
	public int cols;
	
	public int width;
	public int height;
	
	private Animation spriteAnimation;
	private Texture spriteSheet;
	private TextureRegion[] spriteFrames;
	private TextureRegion currentFrame;
	
	private float stateTime;
	
	public AnimatedSprite (String sheet, int rows, int cols, float frameLength) {
		spriteSheet = new Texture(sheet);
		width = spriteSheet.getWidth()/cols;
		height = spriteSheet.getHeight()/rows;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, width, height);
		spriteFrames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				spriteFrames[index++] = tmp[i][j];
			}
		}
		spriteAnimation = new Animation(frameLength, spriteFrames);
		stateTime = 0;
	}
	
	public TextureRegion getFrame() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = spriteAnimation.getKeyFrame(stateTime, true);
		return currentFrame;
	}
}
