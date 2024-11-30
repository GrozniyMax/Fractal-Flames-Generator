package backend.academy.model.math.variations;

import backend.academy.model.math.MathFunction;
import backend.academy.model.plot.Point;

/**
 * Интерфейс описывающий простую вариацию V<sub>j</sub>
 */
@FunctionalInterface
public interface SimpleVariation extends MathFunction {

    static double calculateR(Point point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y());
    }

    static double calculateTheta(Point point) {
        return Math.atan2(point.x(), point.y());
    }

    static double calculatePhi(Point point) {
        return Math.atan2(point.y(), point.x());
    }
}
