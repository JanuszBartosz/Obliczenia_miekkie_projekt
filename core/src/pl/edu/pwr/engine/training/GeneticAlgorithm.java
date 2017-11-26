package pl.edu.pwr.engine.training;

import org.apache.commons.math3.random.RandomDataGenerator;
import pl.edu.pwr.engine.Parameters;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private GeneticAlgorithm scorePopulation() {
        return this;
    }

    private GeneticAlgorithm performSelection() {
        List<Genotype> newPopulation = new ArrayList<>(population.size());

        for (int i = 0; i < population.size(); i++) {
            Genotype tournamentWinner = new Random()
                    .ints(Parameters.tournamentSize, 0, population.size())
                    .mapToObj(population::get)
                    .sorted(Genotype.DESCENDING_COMPARATOR)
                    .findFirst()
                    .orElse(null);
            newPopulation.set(i, tournamentWinner);
        }
        assert (!newPopulation.contains(null));
        this.population = newPopulation;
        return this;
    }

    private GeneticAlgorithm performCrossover() {

        int populationSize = population.size();
        List<Genotype> newPopulation = new ArrayList<>();

        List<Integer> indexes = IntStream.range(0, population.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(indexes);

        Random random = new Random();
        while (population.isEmpty()) {
            int firstParentIdx = indexes.remove(indexes.size() - 1);
            int secondParentIdx = indexes.remove(indexes.size() - 1);

            Genotype firstParent = population.get(firstParentIdx);
            Genotype secondParent = population.get(secondParentIdx);

            if (random.nextInt(100) <= Parameters.crossoverRate * 100) {
                newPopulation.add(crossover(firstParent, secondParent));
                newPopulation.add(crossover(secondParent, firstParent));
            } else {
                newPopulation.add(firstParent);
                newPopulation.add(secondParent);
            }

            if (firstParentIdx > secondParentIdx) {
                population.remove(firstParentIdx);
                population.remove(secondParentIdx);
            } else {
                population.remove(secondParentIdx);
                population.remove(firstParentIdx);
            }
        }
        assert newPopulation.size() == populationSize;
        return this;
    }

    private Genotype crossover(Genotype firstParent, Genotype secondParent) {

        Random random = new Random();
        List<double[][]> firstParentChromosomes = firstParent.getChromosomes();
        List<double[][]> secondParentChromosomes = secondParent.getChromosomes();
        assert firstParentChromosomes.size() == secondParentChromosomes.size();

        List<double[][]> childChromosomes = new ArrayList<>(firstParentChromosomes.size());

        for (int i = 0; i < firstParentChromosomes.size(); i++) {
            double[][] firstParentChromosome = firstParentChromosomes.get(i);
            double[][] secondParentChromosome = secondParentChromosomes.get(i);

            int crossoverPoint = random.nextInt(firstParentChromosome.length);

            double[][] childChromosome = new double[firstParentChromosome.length][firstParentChromosome[0].length];

            for (int j = 0; j < crossoverPoint; j++) {
                childChromosome[j] = Arrays.copyOf(firstParentChromosome[j], firstParentChromosome[j].length);
            }

            for (int j = crossoverPoint; j < secondParentChromosome.length; j++) {
                childChromosome[j] = Arrays.copyOf(secondParentChromosome[j], secondParentChromosome[j].length);
            }

            childChromosomes.add(childChromosome);
        }

        return new Genotype(childChromosomes);
    }

    private GeneticAlgorithm performMutation() {

        Random random = new Random();
        for (Genotype genotype : population) {

            if (random.nextInt(100) <= Parameters.specimenMutationRate * 100) {
                mutate(genotype);
            }
        }
        return this;
    }

    private void mutate(Genotype genotype) {

        Random random = new Random();
        List<double[][]> chromosomes = genotype.getChromosomes();

        for (double[][] chromosome : chromosomes) {

            for (double[] neuron : chromosome) {
                if (random.nextInt(100) <= Parameters.geneMutationRate) {
                    double mutationValue = new RandomDataGenerator().nextUniform(-Parameters.geneMutationRate, Parameters.geneMutationRate);
                    for (int i = 0; i < neuron.length; i++) {
                        neuron[i] = neuron[i] + mutationValue > 1D || neuron[i] + mutationValue < -1D ? neuron[i] : neuron[i] + mutationValue;
                    }
                }
            }
        }
    }

    private List<List<double[][]>> getPopulation() {
        return population.stream()
                .map(Genotype::getChromosomes)
                .collect(Collectors.toList());
    }
}
