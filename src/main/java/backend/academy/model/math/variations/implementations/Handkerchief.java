package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Handkerchief implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);
        return new Point(
            Math.sin(theta + r),
            Math.cos(theta - r)
        ).multiply(r);
    }
}
