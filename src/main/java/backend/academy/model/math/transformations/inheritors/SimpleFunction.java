package backend.academy.model.math.transformations.inheritors;

import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.plot.Point;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * "Простая" функция: выбирает случайное преобразование из доступных
 */
@Getter
@Setter
@NoArgsConstructor
public class SimpleFunction extends BasicTransformation {

    @JsonProperty("basic")
    private AffineTransformation basic;

    public SimpleFunction(AffineTransformation basic) {
        this.basic = basic;
    }

    @Override
    public Point apply(Point point) {
        return basic.andThen(variations.getRandom()).apply(point);

    }
}
