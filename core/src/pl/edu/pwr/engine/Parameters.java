package pl.edu.pwr.engine;

import com.badlogic.gdx.graphics.Color;

public class Parameters {
    public static double randomMatrixLowerBound = -0.5d;
    public static double randomMatrixUpperBound = 0.5d;

    public static int tournamentSize = 3;
    public static int tournamentWinners = 1;

    public static double crossoverRate = 0.8d;
    public static double specimenMutationRate = 0.4d;
    public static double geneMutationRate = 0.2d;
    public static double maxMutationValue = 0.2d;

    public static int numberPlants = 40;
    public static int numberHerbivores = 30;
    public static int numberCarnivores = 15;

    public static int networkLayersHerbivores = 3;
    public static int neuronsPerLayerHerbivores = 20;
    public static int networkLayersCarnivores = 2;
    public static int neuronsPerLayerCarnivores = 15;

    public static int simulationTicks = 120000;
    public static int tickInterval = 20;

    public static GeneticType crossoverType = GeneticType.WEIGHTS;
    public static GeneticType mutationType = GeneticType.WEIGHTS;

    // Board borders
    public static float borderX;
    public static float borderY;
    public static double maxDistance;
    public static Color plantsColor = Color.GREEN;
    public static float plantsRadius = 10;
    public static Color herbivoreColor = Color.BLUE;
    public static float herbivoreRadius = 10;
    public static int initialFullness = 1000;
    public static int maxFullness = 2000;


    public static Color carnivoreColor = Color.RED;
    public static float carnivoreRadius = 12;

    public final static float maxMoveAngle = (float) (1.0 / 2.0 * Math.PI);

    public final static float maxSpeed = 2;

    public static String toStringStatic() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameters.randomMatrixLowerBound ");
        sb.append(randomMatrixLowerBound);
        sb.append("\nParameters.randomMatrixUpperBound ");
        sb.append(randomMatrixUpperBound);
        sb.append("\nParameters.tournamentSize ");
        sb.append(tournamentSize);
        sb.append("\nParameters.tournamentWinners ");
        sb.append(tournamentWinners);
        sb.append("\nParameters.crossoverRate ");
        sb.append(crossoverRate);
        sb.append("\nParameters.specimenMutationRate ");
        sb.append(specimenMutationRate);
        sb.append("\nParameters.geneMutationRate ");
        sb.append(geneMutationRate);
        sb.append("\nParameters.maxMutationValue ");
        sb.append(maxMutationValue);
        sb.append("\nParameters.numberPlants ");
        sb.append(numberPlants);
        sb.append("\nParameters.numberHerbivores ");
        sb.append(numberHerbivores);
        sb.append("\nParameters.numberCarnivores ");
        sb.append(numberCarnivores);
        sb.append("\nParameters.networkLayersHerbivores ");
        sb.append(networkLayersHerbivores);
        sb.append("\nParameters.neuronsPerLayerHerbivores ");
        sb.append(neuronsPerLayerHerbivores);
        sb.append("\nParameters.networkLayersCarnivores ");
        sb.append(networkLayersCarnivores);
        sb.append("\nParameters.neuronsPerLayerCarnivores ");
        sb.append(neuronsPerLayerCarnivores);
        sb.append("\nParameters.simulationTicks ");
        sb.append(simulationTicks);
        sb.append("\nParameters.tickInterval ");
        sb.append(tickInterval);
        sb.append("\nParameters.crossoverType ");
        sb.append(crossoverType);
        sb.append("\nParameters.mutationType ");
        sb.append(mutationType);
        sb.append("\nParameters.borderX ");
        sb.append(borderX);
        sb.append("\nParameters.borderY ");
        sb.append(borderY);
        sb.append("\nParameters.maxDistance ");
        sb.append(maxDistance);
        sb.append("\nParameters.plantsColor ");
        sb.append(plantsColor);
        sb.append("\nParameters.plantsRadius ");
        sb.append(plantsRadius);
        sb.append("\nParameters.herbivoreColor ");
        sb.append(herbivoreColor);
        sb.append("\nParameters.herbivoreRadius ");
        sb.append(herbivoreRadius);
        sb.append("\nParameters.carnivoreColor ");
        sb.append(carnivoreColor);
        sb.append("\nParameters.carnivoreRadius ");
        sb.append(carnivoreRadius);
        sb.append("\nParameters.maxMoveAngle ");
        sb.append(maxMoveAngle);
        sb.append("\nParameters.maxSpeed ");
        sb.append(maxSpeed);
        return sb.toString();
    }
}
