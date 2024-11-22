package backend.academy.model.math.basicMath;

import backend.academy.model.math.MathFucntion;
import backend.academy.model.plot.Point;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Addition implements MathFucntion {

    private final Point pointToAdd;

    @Override
    public Point apply(Point point) {
        return point.add(pointToAdd);
    }
}