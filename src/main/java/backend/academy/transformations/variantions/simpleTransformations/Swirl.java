package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import backend.academy.transformations.variantions.SimpleVariation;

public class Swirl implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double r = AbstractTransformation.calculateR(point);
        double rSquared = r * r;
        return new Point(
            point.x() * Math.sin(rSquared) - point.y() * Math.cos(rSquared),
            point.x() * Math.cos(rSquared) + point.y() * Math.sin(rSquared)
        );
    }
}
