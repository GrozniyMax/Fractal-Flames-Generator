package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.SimpleVariation;

public class Identity implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        return new Point(point.x(), point.y());
    }

}
