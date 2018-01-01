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
    private final static int defaultRespawnCooldown = 20;

    public Animal(float x, float y, float speed, float angle, Color color, float radius, NeuralNetParams neuralNetParams) {
        super(x, y, speed, angle, color, radius);
        respawnCooldown = 0;
        this.neuralNet = new FeedforwardNeuralNet(
                neuralNetParams.numberLayers,
                neuralNetParams.numberNeuronsPerLayer,
                neuralNetParams.numberInputs,
                neuralNetParams.numberOutputs);
    }


    public Animal(float x, float y, float speed, float angle, Color color, float radius, List<double[][]> weights) {
        super(x, y, speed, angle, color, radius);
        respawnCooldown = 0;
        this.neuralNet = new FeedforwardNeuralNet(weights);
    }

    private void calculateSpeedAndAngle(double[] inputs) {

        double[] outputs = neuralNet.computeOutputs(normalize(inputs));
        if (outputs[0] > outputs[1]) {
            this.angle = angle + (float) outputs[0] * Parameters.maxMoveAngle;
        } else {
            this.angle = angle - (float) outputs[1] * Parameters.maxMoveAngle;
        }
        this.speed = Math.abs((float) outputs[2]) * Parameters.maxSpeed;
    }

    private double[] normalize(double[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] /= Parameters.maxDistance / 100;
        }
        return inputs;
    }

    @Override
    public void makeStep() {
        calculateSpeedAndAngle(nextInputs);
        super.makeStep();
    }

    @Override
    public void setNextInputs(double[] nextInputs) {
        this.nextInputs = nextInputs;
    }

    @Override
    public Genotype mapToGenotype() {
        return new Genotype(fitness, neuralNet.getWeights());
    }

    public int decrementRespawnCooldown(){
        return --respawnCooldown;
    }

    public void setRespawnCooldown(){
        respawnCooldown = defaultRespawnCooldown;
    }
}
