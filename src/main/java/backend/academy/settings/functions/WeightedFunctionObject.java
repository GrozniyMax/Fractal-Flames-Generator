package backend.academy.settings.functions;

import backend.academy.transformations.WeightedTransformation;

public class WeightedFunctionObject extends FunctionObject<WeightedTransformation> {

    private AffineFunctionObject affineFunction;
    private double[] weights;

    @Override
    public WeightedTransformation getRealType() {
        return new WeightedTransformation(affineFunction.getRealType(), weights);
    }
}
