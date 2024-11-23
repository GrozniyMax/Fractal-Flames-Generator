package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Cosine implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        return new Point(
            Math.cos(Math.PI* point.x())*Math.cosh(point.y()),
            -Math.sin(Math.PI* point.x())*Math.sinh(point.y())
        );
    }
}
