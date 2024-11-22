package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public final class Diamond implements SimpleVariation {
    // Number 11

    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);
        return new Point(Math.sin(theta) * Math.cos(r), Math.cos(theta) * Math.sin(r));
    }
}
