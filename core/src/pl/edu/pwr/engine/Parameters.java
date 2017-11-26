package pl.edu.pwr.engine;

import com.badlogic.gdx.graphics.Color;

public class Parameters {

    public static double randomMatrixLowerBound = 0.3d;
    public static double randomMatrixUpperBound = 0.3d;

    public static int tournamentSize = 3;
    public static int tournamentWinners = 1;

    public static double crossoverRate = 0.7d;
    public static double specimenMutationRate = 0.3d;
    public static double geneMutationRate = 0.2d;
    public static double maxMutationValue = 0.2d;

    public static int numberPlants = 40;
    public static int numberHerbivores = 30;
    public static int numberCarnivores = 20;

    // Board borders
    public static float borderX;
    public static float borderY;
    public static Color plantsColor = Color.GREEN;
    public static float plantsRadius = 5;
    public static Color herbivoreColor = Color.BLUE;
    public static float herbivoreRadius = 10;


    public static Color carnivoreColor = Color.RED;
    public static float carnivoreRadius = 12;
}
