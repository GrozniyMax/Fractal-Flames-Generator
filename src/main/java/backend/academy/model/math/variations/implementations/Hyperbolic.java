package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Hyperbolic implements SimpleVariation{
    // Number 10

    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);
        return new Point(Math.sin(theta) / r, r * Math.cos(theta));
    }
}
