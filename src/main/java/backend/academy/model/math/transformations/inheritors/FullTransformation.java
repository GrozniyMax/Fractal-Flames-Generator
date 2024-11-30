package backend.academy.model.math.transformations.inheritors;

import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.plot.Point;

/**
 * Полное преобразование (взвешенная сумма с дополнительной аффинной пост-трансформацией)
 */
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
