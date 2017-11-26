package pl.edu.pwr.engine;

public class NeuralNetParams {

    public final int numberLayers;
    public final int numberNeuronsPerLayer;
    public final int numberInputs;
    public final int numberOutputs;

    public NeuralNetParams(int numberLayers, int numberNeuronsPerLayer, int numberInputs, int numberOutputs) {
        this.numberLayers = numberLayers;
        this.numberNeuronsPerLayer = numberNeuronsPerLayer;
        this.numberInputs = numberInputs;
        this.numberOutputs = numberOutputs;
    }
}