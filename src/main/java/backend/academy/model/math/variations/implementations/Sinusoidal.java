package backend.academy.model.math.variations.implementations;

import backend.academy.model.plot.Point;
import backend.academy.model.math.variations.SimpleVariation;

public class Sinusoidal implements SimpleVariation {
    // Number 1

    @Override
    public Point apply(Point point) {
        return new Point(
            Math.sin(point.x()),
            Math.sin(point.y())
        );
    }
}
