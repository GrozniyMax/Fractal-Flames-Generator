package backend.academy.model.math.symmetry.implementation;

import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.plot.Point;
import java.util.Set;

public class XSymmetryTransformation implements Symmetry {

    @Override
    public Set<Point> apply(Point point) {
        return Set.of(new Point(point.x(), -point.y()), point);
    }
}
