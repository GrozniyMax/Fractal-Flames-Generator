package backend.academy.model.math;

import backend.academy.model.plot.Point;
import java.util.function.Function;

@FunctionalInterface
public interface MathFucntion extends Function<Point, Point> {

    Point apply(Point point);

    default MathFucntion andThen(MathFucntion after) {
        return new Composition<>(this, after);
    }
}
