package backend.academy.model.math.transformations;

import backend.academy.model.math.MathFucntion;
import backend.academy.model.math.variations.Variations;
import backend.academy.model.plot.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransformationComposition extends BasicTransformation{

    private final BasicTransformation base;
    private final MathFucntion after;

    public TransformationComposition(BasicTransformation base, MathFucntion after) {
        super(base.variations);
        this.base = base;
        this.after = after;
    }

    @Override
    public Point apply(Point point) {
        return after.compose(base).apply(point);
    }


}
