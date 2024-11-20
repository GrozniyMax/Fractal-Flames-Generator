package backend.academy.transformations.symmenry;

import backend.academy.model.plot.Point;
import java.util.List;

public class YSymmetryTransformation implements SymmetryTransformation{
    @Override
    public List<Point> apply(Point point) {
        return List.of(new Point(-point.x(), point.y()));
    }
}
