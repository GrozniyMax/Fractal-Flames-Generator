package backend.academy.transformations.variantions;

import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;

/**
 * Интерфейс описывающий простую вариацию V<sub>j</sub>
 */
@FunctionalInterface
public interface SimpleVariation extends AbstractTransformation {

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
