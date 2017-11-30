package pl.edu.pwr.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pl.edu.pwr.PrimordialSoup;

import java.util.Arrays;
import java.util.Comparator;

public class DesktopLauncher extends Application{
	private static ComboBox displayModeCB;
	private static CheckBox fullscreenCB;
	private static boolean proceed = false;

	public static void main (String[] arg) {
		launch(arg);
		if(!proceed){
			return;
		}

		Graphics.DisplayMode displayMode = (Graphics.DisplayMode)displayModeCB.getValue();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if(displayMode != null) {
			config.setFromDisplayMode(displayMode);
			config.fullscreen = fullscreenCB.isSelected();
		}
		config.samples = 8;

		new LwjglApplication(new PrimordialSoup(config.width, config.height), config);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Primordial Soup setup");
		ObservableList<Graphics.DisplayMode> options =
				FXCollections.observableList(Arrays.asList(LwjglApplicationConfiguration.getDisplayModes()));
		options.sort((o1, o2) -> {
            if(o1.width > o2.width) {
                return -1;
            }
            if(o1.width  < o2.width) {
                return 1;
            }
            return 0;
        });
		displayModeCB = new ComboBox(options);
		displayModeCB.setValue(options.get(0));

		fullscreenCB = new CheckBox();
		fullscreenCB.setText("Fullscreen");
		fullscreenCB.setSelected(false);

		Button btn = new Button();
		btn.setAlignment(Pos.BOTTOM_RIGHT);
		btn.setText("Start simulation");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				proceed = true;
				primaryStage.close();
			}
		});

		HBox top = new HBox();
		HBox bottom = new HBox();
		VBox left = new VBox();
		VBox right = new VBox();
		GridPane center = new GridPane();

		BorderPane root = new BorderPane();
		root.setTop(top);
		root.setBottom(bottom);
		root.setLeft(left);
		root.setRight(right);
		root.setCenter(center);

		top.getChildren().add(displayModeCB);
		top.getChildren().add(fullscreenCB);
		bottom.getChildren().add(btn);


		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}
