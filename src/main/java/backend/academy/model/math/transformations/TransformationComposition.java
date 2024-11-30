package backend.academy.model.math.transformations;

import backend.academy.model.math.MathFunction;
import backend.academy.model.plot.Point;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransformationComposition extends BasicTransformation {

    private final BasicTransformation base;
    private final MathFunction after;

    public TransformationComposition(BasicTransformation base, MathFunction after) {
        super(base.variations);
        this.base = base;
        this.after = after;
    }

    @Override
    public Point apply(Point point) {
        return after.compose(base).apply(point);
    }

}
