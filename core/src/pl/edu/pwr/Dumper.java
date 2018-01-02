package pl.edu.pwr;

import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.simulation.Simulation;
import pl.edu.pwr.graphics.Entity;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Dumper {
    private static Dumper instance = null;
    private PrintWriter parametersWriter;
    private PrintWriter herbivoresWriter;
    private PrintWriter carnivoresWriter;
    private PrintWriter populationWriter;

    private final static String parametersFilename = "parameters";
    private final static String herbivoresFilename = "herbivores";
    private final static String carnivoresFilename = "carnivores";
    private final static String populationFilename = "population";
    private final static String extension = ".txt";

    protected Dumper() {
        try {
            DateFormat df = new SimpleDateFormat(" dd-MM-yy HHmmss");
            String sdt = df.format(new Date(System.currentTimeMillis()));

            parametersWriter = new PrintWriter(parametersFilename + sdt + extension);
            herbivoresWriter = new PrintWriter(herbivoresFilename + sdt + extension);
            carnivoresWriter = new PrintWriter(carnivoresFilename + sdt + extension);
            populationWriter = new PrintWriter(populationFilename + sdt + extension);
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

    private void dumpHerbivores(int generation, Simulation simulation){
        dumpEntities(generation, herbivoresWriter, simulation.getHerbivores());
    }

    private void dumpCarnivores(int generation, Simulation simulation){
        dumpEntities(generation, carnivoresWriter, simulation.getCarnivores());
    }

    private void dumpEntities(int generation, PrintWriter writer, List<Entity> entities){
        writer.write(Integer.toString(generation));
        writer.write(" ");
        for(Entity entity : entities){
            writer.write(Integer.toString(entity.getFitness()));
            writer.write(" ");
        }
        writer.write("\n");
        writer.flush();
    }

    public static void dumpPopulation(int generation, Simulation simulation) {
        getInstance().populationWriter.write(Integer.toString(generation));
        getInstance().populationWriter.write(" ");
        getInstance().populationWriter.write(Integer.toString(simulation.getAliveHerbivoresCount()));
        getInstance().populationWriter.write(" ");
        getInstance().populationWriter.write(Integer.toString(simulation.getAliveCarnivoresCount()));
        getInstance().populationWriter.write("\n");
    }

    public static void flushPopulation() {
        getInstance().populationWriter.flush();
    }
}
