package pl.edu.pwr.engine.model.activation;

import java.util.function.DoubleUnaryOperator;

public class SigmoidBipolar implements DoubleUnaryOperator {


    @Override
    public double applyAsDouble(double operand) {
        return -1.0 + 2.0 / (1.0 + Math.exp(-operand));
    }
}
