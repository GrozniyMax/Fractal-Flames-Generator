package backend.academy.model.math.transformations.inheritors;

import backend.academy.model.math.Composition;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.math.variations.Variations;

import backend.academy.model.plot.Point;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleFunction extends BasicTransformation {

    @JsonProperty("basic")
    private AffineTransformation basic;

    public SimpleFunction( AffineTransformation basic) {
        super();
        this.basic = basic;
    }

    @Override
    public Point apply(Point point) {
        return basic.andThen(variations.getRandom()).apply(point);

    }
}
