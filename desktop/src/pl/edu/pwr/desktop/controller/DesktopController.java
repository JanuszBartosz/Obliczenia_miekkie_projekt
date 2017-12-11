package pl.edu.pwr.desktop.controller;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.edu.pwr.desktop.DesktopLauncher;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DesktopController implements Initializable {
    @FXML
    private ComboBox displayModeCB;

    @FXML
    private CheckBox fullscreenCB;

    @FXML
    private Button startB;

    @FXML
    private ToggleGroup crossoverTG;

    @FXML
    private ToggleGroup mutationTG;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        displayModeCB.setValue(options.get(0));

        crossoverTG.selectToggle(crossoverTG.getToggles().get(0));
        mutationTG.selectToggle(mutationTG.getToggles().get(0));
    }

    @FXML
    private void startSimulation(ActionEvent event){
        DesktopLauncher.setProceed(true);
        DesktopLauncher.setDisplayMode((Graphics.DisplayMode)displayModeCB.getValue());
        DesktopLauncher.setFullscreen(fullscreenCB.isSelected());

        Stage stage = (Stage)startB.getScene().getWindow();
        stage.close();
    }
}
