package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public final class Disc implements SimpleVariation {
    // Number 8

    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);
        return new Point(
            theta / Math.PI * Math.sin(Math.PI * r),
            theta / Math.PI * Math.cos(Math.PI * r));
    }
}
