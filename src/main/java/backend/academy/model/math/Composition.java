package backend.academy.model.math;

import backend.academy.model.plot.Point;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Composition<T extends MathFucntion> implements MathFucntion {

    private final T basin;
    private final MathFucntion wrapping;

    @Override
    public Point apply(Point point) {
        return basin.andThen(wrapping).apply(point);
    }
}
