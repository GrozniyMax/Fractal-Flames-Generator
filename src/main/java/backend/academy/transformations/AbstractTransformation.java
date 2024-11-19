package backend.academy.transformations;

import backend.academy.model.plot.Point;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.function.Function;

/**
 * Обобщающий интерфейс для всех трансформаций, чтобы отделить иерархию наших функций от {@link Function}
 */
@FunctionalInterface
public interface AbstractTransformation extends Function<Point, Point> {
    public static final AbstractTransformation IDENTITY = new AbstractTransformation(){
        @Override
        public Point apply(Point point) {
            return point;
        }
    };

    @Override
    Point apply(Point point);
}