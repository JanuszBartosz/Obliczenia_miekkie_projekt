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

    public static int networkLayers = 2;
    public static int neuronsPerLayer = 12;

    public static int herbivoreNetworkLayers = 3;
    public static int herbivoreNeuronsPerLayer = 20;
    public static int carnivoreNetworkLayers = 2;
    public static int carnivoreNeuronsPerLayer = 15;

    public static int simulationTicks = 2400;

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
}
