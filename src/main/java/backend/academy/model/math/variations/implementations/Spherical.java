package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Spherical implements SimpleVariation {
    // Number 2
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        return point.multiply(1.0 / (r*r));
    }
}
