package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Cross implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double coeff = Math.sqrt(1 / Math.pow(point.x() * point.x() - point.y() * point.y(), 2));
        return point.multiply(coeff);
    }
}
