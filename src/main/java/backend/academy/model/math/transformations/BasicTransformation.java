package backend.academy.model.math.transformations;

import backend.academy.model.math.MathFunction;
import backend.academy.model.math.variations.Variations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasicTransformation implements MathFunction {

    protected Variations variations = Variations.getFull();

    @Override
    public BasicTransformation andThen(MathFunction after) {
        return new TransformationComposition(this, after);
    }
}
