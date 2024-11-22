package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Spiral implements SimpleVariation {
    // Number 9
    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);

        return new Point(1 / r * (Math.cos(theta) + Math.sin(r)),
            1 / r * (Math.sin(theta) - Math.cos(r)));
    }
}
