package model.mathoperations;

public class RectangleOperation implements MathOperation{
    @Override
    public double execute(Double[] values) {
        return values[0] * values[1];
    }

    @Override
    public String getName() {
        return "Área do retângulo";
    }
}
