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
import pl.edu.pwr.engine.GeneticType;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.training.GeneticAlgorithm;

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
    @FXML
    private Spinner layersHerbivoresS;
    @FXML
    private Spinner neuronsHerbivoresS;
    @FXML
    private Spinner layersCarnivoresS;
    @FXML
    private Spinner neuronsCarnivoresS;
    @FXML
    private Spinner crossoverRateS;
    @FXML
    private Spinner mutationRateS;
    @FXML
    private Spinner mutationSpecimenS;
    @FXML
    private Spinner mutationMaxValueS;
    @FXML
    private Spinner tournamentSizeS;
    @FXML
    private Spinner tournamentPassS;
    @FXML
    private Spinner simulationTicksS;
    @FXML
    private Spinner tickIntervalS;
    @FXML
    private Spinner foodS;
    @FXML
    private Spinner herbivoresS;
    @FXML
    private Spinner carnivoresS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initControlsValues();
    }

    @FXML
    private void startSimulation(ActionEvent event) {
        saveControlsValues();
        Stage stage = (Stage) startB.getScene().getWindow();
        stage.close();
    }

    private void initControlsValues() {
        // Display settings
        ObservableList<Graphics.DisplayMode> options =
                FXCollections.observableList(Arrays.asList(LwjglApplicationConfiguration.getDisplayModes()));
        options.sort((o1, o2) -> {
            if (o1.width > o2.width) {
                return -1;
            }
            if (o1.width < o2.width) {
                return 1;
            }
            return 0;
        });
        displayModeCB.setItems(options);
        displayModeCB.setValue(options.get(0));

        // Simulation settings
        layersHerbivoresS.getValueFactory().setValue(Parameters.networkLayersHerbivores);
        neuronsHerbivoresS.getValueFactory().setValue(Parameters.neuronsPerLayerHerbivores);
        layersCarnivoresS.getValueFactory().setValue(Parameters.networkLayersCarnivores);
        neuronsCarnivoresS.getValueFactory().setValue(Parameters.neuronsPerLayerCarnivores);
        crossoverRateS.getValueFactory().setValue(Parameters.crossoverRate);
        mutationRateS.getValueFactory().setValue(Parameters.geneMutationRate);
        mutationSpecimenS.getValueFactory().setValue(Parameters.specimenMutationRate);
        mutationMaxValueS.getValueFactory().setValue(Parameters.maxMutationValue);
        tournamentSizeS.getValueFactory().setValue(Parameters.tournamentSize);
        tournamentPassS.getValueFactory().setValue(Parameters.tournamentWinners);
        simulationTicksS.getValueFactory().setValue(Parameters.simulationTicks);
        tickIntervalS.getValueFactory().setValue(Parameters.tickInterval);
        foodS.getValueFactory().setValue(Parameters.numberPlants);
        herbivoresS.getValueFactory().setValue(Parameters.numberHerbivores);
        carnivoresS.getValueFactory().setValue(Parameters.numberCarnivores);

        crossoverTG.getToggles().get(0).setUserData(GeneticType.WEIGHTS);
        crossoverTG.getToggles().get(1).setUserData(GeneticType.NODES);

        mutationTG.getToggles().get(0).setUserData(GeneticType.WEIGHTS);
        mutationTG.getToggles().get(1).setUserData(GeneticType.NODES);

        crossoverTG.selectToggle(crossoverTG.getToggles().get(0));
        mutationTG.selectToggle(mutationTG.getToggles().get(0));
    }

    private void saveControlsValues() {
        // Display settings
        DesktopLauncher.setProceed(true);
        DesktopLauncher.setDisplayMode((Graphics.DisplayMode) displayModeCB.getValue());
        DesktopLauncher.setFullscreen(fullscreenCB.isSelected());

        // Simulation settings
        Parameters.networkLayersHerbivores = (int) layersHerbivoresS.getValue();
        Parameters.neuronsPerLayerHerbivores = (int) neuronsHerbivoresS.getValue();
        Parameters.networkLayersCarnivores = (int) layersCarnivoresS.getValue();
        Parameters.neuronsPerLayerCarnivores = (int) neuronsCarnivoresS.getValue();
        Parameters.crossoverRate = (double) crossoverRateS.getValue();
        Parameters.geneMutationRate = (double) mutationRateS.getValue();
        Parameters.specimenMutationRate = (double) mutationSpecimenS.getValue();
        Parameters.maxMutationValue = (double) mutationMaxValueS.getValue();
        Parameters.tournamentSize = (int) tournamentSizeS.getValue();
        Parameters.tournamentWinners = (int) tournamentPassS.getValue();
        Parameters.simulationTicks = (int) simulationTicksS.getValue();
        Parameters.tickInterval = (int) tickIntervalS.getValue();
        Parameters.numberPlants = (int) foodS.getValue();
        Parameters.numberHerbivores = (int) herbivoresS.getValue();
        Parameters.numberCarnivores = (int) carnivoresS.getValue();

        Parameters.crossoverType = (GeneticType) crossoverTG.getSelectedToggle().getUserData();
        Parameters.mutationType = (GeneticType)mutationTG.getSelectedToggle().getUserData();
    }
}
