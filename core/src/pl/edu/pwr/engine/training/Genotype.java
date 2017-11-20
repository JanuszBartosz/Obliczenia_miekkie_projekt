package pl.edu.pwr.engine.training;

import java.util.Comparator;
import java.util.List;

public class Genotype implements Comparable<Genotype> {

    private int fitness;
    private List<double[][]> chromosomes;

    public Genotype(int fitness, List<double[][]> chromosomes) {
        this.fitness = fitness;
        this.chromosomes = chromosomes;
    }

    public Genotype(List<double[][]> chromosomes) {
        this.fitness = 0;
        this.chromosomes = chromosomes;
    }

    public int getFitness() {
        return fitness;
    }

    List<double[][]> getChromosomes() {
        return chromosomes;
    }

    @Override
    public int compareTo(Genotype other) {

        return Integer.compare(this.fitness, other.fitness);
    }

    final static Comparator<Genotype> DESCENDING_COMPARATOR = Comparator.reverseOrder();
}
