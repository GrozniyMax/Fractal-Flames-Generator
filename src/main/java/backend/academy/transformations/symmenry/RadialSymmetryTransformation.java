package backend.academy.transformations.symmenry;

import backend.academy.model.plot.Point;
import backend.academy.transformations.AffineTransformation;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RadialSymmetryTransformation implements SymmetryTransformation {

    private int sectors;

    public RadialSymmetryTransformation(int sectors) {
        this.sectors = sectors;
    }

    @Override
    public List<Point> apply(Point point) {
        double angle = 2* Math.PI / sectors;
        double currentAngle;
        List<Point> result = new ArrayList<>(sectors);
        for (int i = 0; i < sectors; i++) {
            currentAngle = i*angle;
            result.add(new Point(
                point.x() * Math.cos(currentAngle) - point.y() * Math.sin(currentAngle),
                point.x() * Math.sin(currentAngle) + point.y() * Math.cos(currentAngle)
            ));
        }
        return result;
    }

}
