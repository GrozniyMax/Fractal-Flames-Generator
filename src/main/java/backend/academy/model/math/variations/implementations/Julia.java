package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public class Julia implements SimpleVariation {
    // Number 13

    @Override
    public Point apply(Point point) {
        double r = SimpleVariation.calculateR(point);
        double theta = SimpleVariation.calculateTheta(point);
        double omega = Math.random() < 0.5 ? 0 : Math.PI;
        return new Point(
            Math.sqrt(r) * Math.cos(theta / 2 + omega),
            Math.sqrt(r) * Math.sin(theta / 2 + omega)
        );
    }

}
