package pl.edu.pwr.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.edu.pwr.PrimordialSoup;

public class DesktopLauncher {
	public static void main (String[] arg) {

		Graphics.DisplayMode[] displayModes = LwjglApplicationConfiguration.getDisplayModes();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.setFromDisplayMode(displayModes[displayModes.length - 1]);
		for(Graphics.DisplayMode dm : displayModes){
			System.out.println(dm.toString());
		}

		new LwjglApplication(new PrimordialSoup(config.width, config.height), config);
	}
}
