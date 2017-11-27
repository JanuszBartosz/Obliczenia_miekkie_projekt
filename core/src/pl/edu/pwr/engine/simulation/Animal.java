package pl.edu.pwr.engine.simulation;

import com.badlogic.gdx.graphics.Color;
import pl.edu.pwr.engine.NeuralNetParams;
import pl.edu.pwr.engine.model.FeedforwardNeuralNet;
import pl.edu.pwr.engine.model.NeuralNet;
import pl.edu.pwr.engine.training.Genotype;
import pl.edu.pwr.graphics.Entity;

import java.util.List;

public class Animal extends Entity {

    private NeuralNet neuralNet;
    private Entity mouth;
    private double[] nextInputs;

    public Animal(float x, float y, float speed, float angle, Color color, float radius, NeuralNetParams neuralNetParams) {
        super(x, y, speed, angle, color, radius);
        this.neuralNet = new FeedforwardNeuralNet(
                neuralNetParams.numberLayers,
                neuralNetParams.numberNeuronsPerLayer,
                neuralNetParams.numberInputs,
                neuralNetParams.numberOutputs);
    }


    public Animal(float x, float y, float speed, float angle, Color color, float radius, List<double[][]> weights) {
        super(x, y, speed, angle, color, radius);
        this.neuralNet = new FeedforwardNeuralNet(weights);
    }

    private void calculateSpeedAndAngle(double[] inputs) {
        double[] outputs = neuralNet.computeOutputs(inputs);
        this.angle = outputs[0] > outputs[1] ? angle + (float) outputs[0] * Entity.maxAngle : angle - (float) outputs[1] * Entity.maxAngle;
        this.speed = (float) outputs[2] * Entity.maxSpeed;
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
}
