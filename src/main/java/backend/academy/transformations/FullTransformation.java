package backend.academy.transformations;

import backend.academy.model.plot.Point;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FullTransformation implements AbstractTransformation{

    private final WeightedTransformation weightedTransformation;

    private final AffineTransformation postTransformation;

    @Override
    public Point apply(Point point) {
        return postTransformation.apply(
            weightedTransformation.apply(
                point
            )
        );
    }
}
