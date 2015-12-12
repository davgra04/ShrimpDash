package com.teamgraves.shrimpdash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.teamgraves.shrimpdash.ShrimpDash;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Shrimp Dash!";
		config.resizable = false;
		config.height = 450;
		config.width = 800;
		new LwjglApplication(new ShrimpDash(), config);
	}

}
