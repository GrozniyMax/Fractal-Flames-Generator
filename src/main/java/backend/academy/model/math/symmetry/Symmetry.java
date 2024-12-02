package backend.academy.model.math.symmetry;

import backend.academy.model.plot.Point;
import java.util.Set;

public interface Symmetry {

    Set<Point> apply(Point p);
}
