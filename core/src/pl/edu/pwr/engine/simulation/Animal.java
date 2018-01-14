package pl.edu.pwr.engine.simulation;

import com.badlogic.gdx.graphics.Color;
import pl.edu.pwr.engine.NeuralNetParams;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.model.FeedforwardNeuralNet;
import pl.edu.pwr.engine.model.NeuralNet;
import pl.edu.pwr.engine.training.Genotype;
import pl.edu.pwr.graphics.Entity;

import java.util.List;

public class Animal extends Entity {

    private NeuralNet neuralNet;
    private Entity mouth;
    private double[] nextInputs;
    private int respawnCooldown;
    private final static int defaultRespawnCooldown = 1000;
    private int stepCounter;
    private int fullness;
    private boolean alive;


    public Animal(float x, float y, float speed, float angle, Color color, float radius, NeuralNetParams neuralNetParams) {
        super(x, y, speed, angle, color, radius);
        respawnCooldown = 0;
        this.neuralNet = new FeedforwardNeuralNet(
                neuralNetParams.numberLayers,
                neuralNetParams.numberNeuronsPerLayer,
                neuralNetParams.numberInputs,
                neuralNetParams.numberOutputs);
        this.stepCounter = 0;
        this.fullness = Parameters.initialFullness;
        this.alive = true;
    }


    public Animal(float x, float y, float speed, float angle, Color color, float radius, List<double[][]> weights) {
        super(x, y, speed, angle, color, radius);
        respawnCooldown = 0;
        this.neuralNet = new FeedforwardNeuralNet(weights);
        this.stepCounter = 0;
        this.fullness = Parameters.initialFullness;
        this.alive = true;
    }

    private void calculateSpeedAndAngle(double[] inputs) {

        double[] outputs = neuralNet.computeOutputs(normalize(inputs));
        if (outputs[0] > outputs[1]) {
            this.setAngle(angle + (float) outputs[0] * Parameters.maxMoveAngle);
        } else {
            this.setAngle(angle - (float) outputs[1] * Parameters.maxMoveAngle);
        }
        this.speed = Math.abs((float) outputs[2]) * Parameters.maxSpeed;
    }

    private double[] normalize(double[] inputs) {

        for (int i = 0; i < inputs.length - 1; i++) {
            inputs[i] *= 10;
        }
        inputs[inputs.length - 1] = ((Parameters.initialFullness - inputs[inputs.length - 1]) / Parameters.maxFullness) * 100;
        return inputs;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean makeStep() {
        if (alive) {
            stepCounter++;
            fitness++;
            fullness--;
            alive = fullness > 0 && fullness <= Parameters.maxFullness;
            calculateSpeedAndAngle(nextInputs);
            super.makeStep();
        }
        return alive;
    }

    @Override
    public void setNextInputs(double[] nextInputs) {
        this.nextInputs = nextInputs;
    }

    @Override
    public Genotype mapToGenotype() {
        return new Genotype(fitness, neuralNet.getWeights());
    }

    public int decrementRespawnCooldown() {
        return --respawnCooldown;
    }

    public void setRespawnCooldown() {
        respawnCooldown = defaultRespawnCooldown;
    }

    @Override
    public void incrementFoundFood(int size) {
        fullness += size * 500;
        fitness += size * 500;
    }

    @Override
    public int getFullness() {
        return fullness;
    }

    @Override
    public void resetFullness(){
        fullness = 500;
        alive = true;
    }

}
