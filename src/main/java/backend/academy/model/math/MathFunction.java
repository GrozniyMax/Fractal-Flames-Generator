package backend.academy.model.math;

import backend.academy.model.plot.Point;
import java.util.function.Function;

@FunctionalInterface
public interface MathFunction extends Function<Point, Point> {

    Point apply(Point point);

    default MathFunction andThen(MathFunction after) {
        return new Composition<>(this, after);
    }
}
