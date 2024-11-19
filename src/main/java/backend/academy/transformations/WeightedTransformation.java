package backend.academy.transformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.Variations;
import lombok.RequiredArgsConstructor;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class WeightedTransformation implements AbstractTransformation {

    private final AffineTransformation affineTransformation;
    private final double[] weights;

    @Override
    public Point apply(Point point) {
        AtomicInteger index = new AtomicInteger(0);
        var afterAffine = affineTransformation.apply(point);
        return Variations.getVariations()
            .map(simpleVariation -> simpleVariation.apply(afterAffine))
            .map(point1 -> point1.multiply(getWeight(index.getAndIncrement())))
            .reduce(Point::add)
            .orElse(Point.empty());
    }

    private double getWeight(int count) {
        if (count >= weights.length) {
            return weights[count % weights.length];
        }
        return weights[count];
    }
}
