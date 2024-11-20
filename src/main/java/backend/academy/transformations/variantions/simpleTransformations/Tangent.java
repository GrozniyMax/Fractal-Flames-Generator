package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.SimpleVariation;

public class Tangent implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        return new Point(
            Math.sin(point.x())/Math.cos(point.y()),
            Math.tan(point.y())
        );
    }
}
