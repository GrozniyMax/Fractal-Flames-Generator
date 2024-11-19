package backend.academy.transformations;

import backend.academy.model.plot.Point;
import backend.academy.transformations.variantions.Variations;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleFunction implements AbstractTransformation{

    private final AffineTransformation affineTransformation;

    @Override
    public Point apply(Point point) {
        return Variations.getRandom().apply(
            affineTransformation.apply(point)
        );
    }
}
