package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.SimpleVariation;

public class Cross implements SimpleVariation {
    @Override
    public Point apply(Point point) {
        double coeff = Math.sqrt(1/Math.pow(point.x()* point.x() - point.y()* point.y(), 2));
        return point.multiply(coeff);
    }
}
