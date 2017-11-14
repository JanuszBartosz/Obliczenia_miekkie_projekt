package pl.edu.pwr.engine.model;

import java.util.List;

public interface NeuralNet {

    double[] computeOutputs(double[] inputs);

    List<double[][]> getWeights();

}
