package pl.edu.pwr.engine.model;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.model.activation.SigmoidBipolar;
import pl.edu.pwr.engine.model.activation.SigmoidUnipolar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FeedforwardNeuralNet implements NeuralNet {

    List<RealMatrix> neuronLayers;

    //TODO: Maybe implement biases.
    public FeedforwardNeuralNet(int numberLayers, int numberNeuronsPerLayer, int numberInputs, int numberOutputs) {

        this.neuronLayers = new ArrayList<>();

        neuronLayers.add(createRandomRealMatrix(numberNeuronsPerLayer, numberInputs));

        for (int i = 0; i < numberLayers - 2; i++) {
            neuronLayers.add(createRandomRealMatrix(numberNeuronsPerLayer, numberNeuronsPerLayer));
        }

        neuronLayers.add(createRandomRealMatrix(numberOutputs, numberNeuronsPerLayer));
    }

    @Override
    public double[] computeOutputs(double[] inputs) {

        double[] outputs = null;

        for (int i = 0; i < neuronLayers.size(); i++) {
            RealMatrix neuronLayer = neuronLayers.get(i);
            outputs = neuronLayer.operate(inputs);
            outputs = Arrays.stream(outputs).map(new SigmoidBipolar()).toArray();
            inputs = outputs;
        }

        return outputs;
    }

    @Override
    public List<double[][]> getWeights() {
        return neuronLayers.stream().map(RealMatrix::getData).collect(Collectors.toList());
    }

    private RealMatrix createRandomRealMatrix(int rows, int cols) {

        double[][] randomMatrix = new double[rows][cols];

        for (int row = 0; row < rows; row++) {
            randomMatrix[row] = new Random().doubles(cols, Parameters.randomMatrixLowerBound, Parameters.randomMatrixUpperBound).toArray();
        }

        return new Array2DRowRealMatrix(randomMatrix);
    }
}
