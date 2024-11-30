package backend.academy.model.math;

import backend.academy.model.plot.Point;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Composition<T extends MathFunction> implements MathFunction {

    private final T basin;
    private final MathFunction wrapping;

    @Override
    public Point apply(Point point) {
        return basin.andThen(wrapping).apply(point);
    }
}
