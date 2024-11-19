package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.SimpleVariation;

public class Handkerchief implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);
        return new Point(
            Math.sin(theta + r),
            Math.cos(theta - r)
        ).multiply(r);
    }
}
