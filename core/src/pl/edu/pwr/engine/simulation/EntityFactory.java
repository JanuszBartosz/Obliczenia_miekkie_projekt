package pl.edu.pwr.engine.simulation;

import pl.edu.pwr.engine.NeuralNetParams;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.graphics.Entity;

import java.util.List;
import java.util.Random;

public class EntityFactory {

    private static Random random = new Random();

    public static Entity getEntity(EntityType type) {

        switch (type) {
            case PLANT: {
                return new Plant(
                        random.nextFloat() * Parameters.borderX,
                        random.nextFloat() * Parameters.borderY,
                        Parameters.plantsColor,
                        Parameters.plantsRadius);
            }
            case HERBIVORE: {
                return new Animal(
                        random.nextFloat() * Parameters.borderX,
                        random.nextFloat() * Parameters.borderY,
                        0,
                        0,
                        Parameters.herbivoreColor,
                        Parameters.herbivoreRadius,
                        new NeuralNetParams(Parameters.herbivoreNetworkLayers, Parameters.herbivoreNeuronsPerLayer,
                                5, 3));
            }
            case CARNIVORE: {
                return new Animal(
                        random.nextFloat() * Parameters.borderX,
                        random.nextFloat() * Parameters.borderY,
                        0,
                        0,
                        Parameters.carnivoreColor,
                        Parameters.carnivoreRadius,
                        new NeuralNetParams(Parameters.carnivoreNetworkLayers, Parameters.carnivoreNeuronsPerLayer,
                                3, 3));
            }
        }
        return null;
    }

    public static Entity getEntity(EntityType type, List<double[][]> weights) {

        switch (type) {
            case HERBIVORE: {
                return new Animal(
                        random.nextFloat() * Parameters.borderX,
                        random.nextFloat() * Parameters.borderY,
                        0,
                        0,
                        Parameters.herbivoreColor,
                        Parameters.herbivoreRadius,
                        weights);
            }
            case CARNIVORE: {
                return new Animal(
                        random.nextFloat() * Parameters.borderX,
                        random.nextFloat() * Parameters.borderY,
                        0,
                        0,
                        Parameters.carnivoreColor,
                        Parameters.carnivoreRadius,
                        weights);
            }
        }
        return null;
    }
}
