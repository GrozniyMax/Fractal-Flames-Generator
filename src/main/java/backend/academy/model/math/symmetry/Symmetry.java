package backend.academy.model.math.symmetry;

import backend.academy.model.plot.Point;
import java.util.List;

public interface Symmetry {

    List<Point> apply(Point p);
}
