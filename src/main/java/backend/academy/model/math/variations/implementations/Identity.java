package backend.academy.model.math.variations.implementations;

import backend.academy.model.plot.Point;
import backend.academy.model.math.variations.SimpleVariation;

public class Identity implements SimpleVariation {
    // Number 0

    @Override
    public Point apply(Point point) {
        return new Point(point.x(), point.y());
    }

}
