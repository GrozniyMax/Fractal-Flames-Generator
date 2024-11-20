package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.SimpleVariation;

public class Cosine implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        return new Point(
            Math.cos(Math.PI* point.x())*Math.cosh(point.y()),
            -Math.sin(Math.PI* point.x())*Math.sinh(point.y())
        );
    }
}
