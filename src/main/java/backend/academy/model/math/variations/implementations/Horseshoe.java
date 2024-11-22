package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Horseshoe implements SimpleVariation {
    // Number 4
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        return new Point(point.x() * point.x() - point.y() * point.y(),
            2 * point.x() * point.y()
        ).multiply(1/r);
    }
}
