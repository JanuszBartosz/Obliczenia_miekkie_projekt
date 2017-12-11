package pl.edu.pwr.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import pl.edu.pwr.PrimordialSoup;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class DesktopLauncher extends Application{
	private Parent root = null;

	private static boolean proceed = false;
	private static Graphics.DisplayMode displayMode;
	private static boolean fullscreen = false;

	public static void main (String[] arg) {
		launch(arg);
		if(!proceed){
			return;
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if(displayMode != null) {
			config.setFromDisplayMode(displayMode);
			config.fullscreen = fullscreen;
		}
		config.samples = 8;

		new LwjglApplication(new PrimordialSoup(config.width, config.height), config);
	}

	@Override
	public void start(final Stage primaryStage) {
		try {
			System.out.println(getClass().getResource("DesktopLauncher.fxml"));
			root = FXMLLoader.load(getClass().getResource("DesktopLauncher.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		primaryStage.setScene(new Scene(root, 400, 400));
		primaryStage.setTitle("Primordial Soup setup");
		primaryStage.show();
	}

	public static void setProceed(boolean value){
		proceed = value;
	}

	public static void setDisplayMode(Graphics.DisplayMode value){
		displayMode = value;
	}

	public static void setFullscreen(boolean value){
		fullscreen = value;
	}
}
