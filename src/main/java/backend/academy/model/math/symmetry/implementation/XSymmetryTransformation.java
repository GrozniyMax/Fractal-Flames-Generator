package backend.academy.model.math.symmetry.implementation;

import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.plot.Point;
import java.util.List;

public class XSymmetryTransformation implements Symmetry {

    @Override
    public List<Point> apply(Point point) {
        return List.of(new Point(point.x(), -point.y()));
    }
}
