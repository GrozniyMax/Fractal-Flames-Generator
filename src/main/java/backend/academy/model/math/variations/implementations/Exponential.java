package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Exponential implements SimpleVariation {
    // Number 18
    @Override
    public Point apply(Point point) {
        double exp = Math.exp(point.x() - 1);
        return new Point(
            exp * Math.cos(Math.PI * point.y()),
            exp * Math.sin(Math.PI * point.y())
        );
    }
}
