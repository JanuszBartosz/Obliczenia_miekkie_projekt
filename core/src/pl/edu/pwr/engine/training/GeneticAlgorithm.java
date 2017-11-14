package pl.edu.pwr.engine.training;

import java.util.List;
import java.util.stream.Collectors;

public class GeneticAlgorithm {


    private List<Genotype> population;

    public List<List<double[][]>> run() {

        return this
                .scorePopulation()
                .performSelection()
                .performCrossover()
                .performMutation()
                .getPopulation();
    }

    private GeneticAlgorithm performMutation() {
        return this;
    }

    private GeneticAlgorithm performCrossover() {
        return this;
    }

    private GeneticAlgorithm performSelection() {
        return this;
    }


    private GeneticAlgorithm scorePopulation() {
        return this;
    }

    private List<List<double[][]>> getPopulation() {
        return population.stream()
                .map(Genotype::getChromosomes)
                .collect(Collectors.toList());
    }
}
