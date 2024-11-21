package backend.academy.model.math.transformations.inheritors;

import backend.academy.model.math.Composition;
import backend.academy.model.math.MathFucntion;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.math.variations.Variations;
import backend.academy.model.plot.Point;
import lombok.RequiredArgsConstructor;

public class FullTransformation extends BasicTransformation {

    private final WeightedTransformation basin;
    private final AffineTransformation wrapping;

    public FullTransformation(WeightedTransformation basin, AffineTransformation wrapping) {
        this.basin = basin;
        this.wrapping = wrapping;
    }

    @Override
    public Point apply(Point point) {
        return basin.andThen(wrapping).apply(point);
    }

}
