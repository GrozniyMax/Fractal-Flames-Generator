package backend.academy.model.math.symmetry.implementation;

import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.plot.Point;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RadialSymmetryTransformation implements Symmetry {

    @JsonProperty("sectors")
    private int sectors;

    public RadialSymmetryTransformation(int sectors) {
        this.sectors = sectors;
    }

    @Override
    public Set<Point> apply(Point point) {
        double angle = 2 * Math.PI / sectors;
        double currentAngle;
        Set<Point> result = new HashSet<>(sectors);
        result.add(point);
        for (int i = 0; i < sectors; i++) {
            currentAngle = i * angle;
            result.add(new Point(
                point.x() * Math.cos(currentAngle) - point.y() * Math.sin(currentAngle),
                point.x() * Math.sin(currentAngle) + point.y() * Math.cos(currentAngle)
            ));
        }
        return result;
    }

}
