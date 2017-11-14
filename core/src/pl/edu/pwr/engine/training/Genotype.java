package pl.edu.pwr.engine.training;

import java.util.List;

public class Genotype {

    public Genotype(int fitness, List<double[][]> chromosomes) {
        this.fitness = fitness;
        this.chromosomes = chromosomes;
    }

    private int fitness;
    private List<double[][]> chromosomes;

    public int getFitness() {
        return fitness;
    }

    public List<double[][]> getChromosomes() {
        return chromosomes;
    }
}
