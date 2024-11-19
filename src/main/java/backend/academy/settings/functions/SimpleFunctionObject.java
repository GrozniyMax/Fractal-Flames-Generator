package backend.academy.settings.functions;

import backend.academy.transformations.SimpleFunction;

public class SimpleFunctionObject extends FunctionObject<SimpleFunction> {
    private AffineFunctionObject affineFunction;

    @Override
    public SimpleFunction getRealType() {
        return new SimpleFunction(affineFunction.getRealType());
    }
}
