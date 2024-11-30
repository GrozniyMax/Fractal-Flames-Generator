package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Sinusoidal implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        return new Point(
            Math.sin(point.x()),
            Math.sin(point.y())
        );
    }
}
