package backend.academy.model.math.basicMath;

import backend.academy.model.math.MathFunction;
import backend.academy.model.plot.Point;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Multiplication implements MathFunction {

    private double coefficent;

    @Override
    public Point apply(Point point) {
        return point.multiply(coefficent);
    }
}
