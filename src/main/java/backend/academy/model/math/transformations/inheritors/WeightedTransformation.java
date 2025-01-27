package backend.academy.model.math.transformations.inheritors;

import backend.academy.model.math.MathFunction;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.plot.Point;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Взвешенная сумма всех вариаций.
 */
public class WeightedTransformation extends BasicTransformation {

    private final MathFunction affineTransformation;
    private final double[] weights;

    public WeightedTransformation(AffineTransformation affineTransformation, double[] weights) {
        this.affineTransformation = affineTransformation;
        this.weights = weights;
    }

    @Override
    public Point apply(Point point) {
        AtomicInteger index = new AtomicInteger(0);
        var afterAffine = affineTransformation.apply(point);
        return variations.getVariations()
            .map(simpleVariation -> simpleVariation.apply(afterAffine))
            .map(point1 -> point1.multiply(getWeight(index)))
            .reduce(Point::add)
            .orElse(Point.empty());
    }

    private double getWeight(AtomicInteger integer) {
        int count = integer.getAndIncrement();
        if (count >= weights.length) {
            return weights[count % weights.length];
        }
        return weights[count];
    }

}
