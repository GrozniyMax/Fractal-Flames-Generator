package backend.academy.transformations.symmenry;

import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import java.util.List;

public interface SymmetryTransformation {

    List<Point> apply(Point point);
}
