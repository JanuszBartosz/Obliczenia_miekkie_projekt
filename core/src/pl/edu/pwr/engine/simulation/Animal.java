package pl.edu.pwr.engine.simulation;

import com.badlogic.gdx.graphics.Color;
import pl.edu.pwr.engine.NeuralNetParams;
import pl.edu.pwr.engine.model.FeedforwardNeuralNet;
import pl.edu.pwr.engine.model.NeuralNet;
import pl.edu.pwr.graphics.Entity;

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

    private void calculateSpeedAndAngle(double[] inputs) {
        double[] outputs = neuralNet.computeOutputs(inputs);
        this.angle = outputs[0] > outputs[1] ? angle + (float) outputs[0] * Entity.maxAngle : angle - (float) outputs[1] * Entity.maxAngle;
        this.speed = (float) outputs[2] * Entity.maxSpeed;
    }

    @Override
    public void makeStep(){
        calculateSpeedAndAngle(nextInputs);
        super.makeStep();
    }

    @Override
    public void setNextInputs(double[] nextInputs) {
        this.nextInputs = nextInputs;
    }
}
