package pl.edu.pwr;

import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.simulation.Simulation;
import pl.edu.pwr.graphics.Entity;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class Dumper {
    private static Dumper instance = null;
    private PrintWriter parametersWriter;
    private PrintWriter herbivoresWriter;
    private PrintWriter carnivoresWriter;

    private final static String parametersFilename = "parameters.txt";
    private final static String herbivoresFilename = "herbivores.txt";
    private final static String carnivoresFilename = "carnivores.txt";

    protected Dumper() {
        try {
            parametersWriter = new PrintWriter(parametersFilename);
            herbivoresWriter = new PrintWriter(herbivoresFilename);
            carnivoresWriter = new PrintWriter(carnivoresFilename);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Dumper getInstance() {
        if(instance == null) {
            instance = new Dumper();
        }
        return instance;
    }

    public static void dumpParameters(){
        getInstance().parametersWriter.write(Parameters.toStringStatic());
        getInstance().parametersWriter.flush();
    }

    public static void dumpData(int generation, Simulation simulation){
        getInstance().dumpHerbivores(generation, simulation);
        getInstance().dumpCarnivores(generation, simulation);
    }

    public void dumpHerbivores(int generation, Simulation simulation){
        dumpEntities(generation, herbivoresWriter, simulation.getHerbivores());
    }

    public void dumpCarnivores(int generation, Simulation simulation){
        dumpEntities(generation, carnivoresWriter, simulation.getCarnivores());
    }

    public void dumpEntities(int generation, PrintWriter writer, List<Entity> entities){
        writer.write(Integer.toString(generation));
        writer.write(" ");
        for(Entity entity : entities){
            writer.write(Integer.toString(entity.getFitness()));
            writer.write(" ");
        }
        writer.write("\n");
        writer.flush();
    }
}
