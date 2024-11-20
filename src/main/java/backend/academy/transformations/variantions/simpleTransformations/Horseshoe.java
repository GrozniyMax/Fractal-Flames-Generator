package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import backend.academy.transformations.variantions.SimpleVariation;

public class Horseshoe implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double r = AbstractTransformation.calculateR(point);
        return new Point(point.x() * point.x() - point.y() * point.y(),
            2 * point.x() * point.y()
        ).multiply(1/r);
    }
}
