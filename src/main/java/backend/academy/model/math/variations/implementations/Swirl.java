package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Swirl implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double rSquared = r * r;
        return new Point(
            point.x() * Math.sin(rSquared) - point.y() * Math.cos(rSquared),
            point.x() * Math.cos(rSquared) + point.y() * Math.sin(rSquared)
        );
    }
}
