package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.SimpleVariation;

public class Spherical implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        return point.multiply(1.0 / (r*r));
    }
}
