package backend.academy.settings.functions;

import backend.academy.transformations.AffineTransformation;

public class AffineFunctionObject extends FunctionObject<AffineTransformation> {

    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;

    @Override
    public AffineTransformation getRealType() {
        return new AffineTransformation(a, b, c, d, e, f);
    }
}
