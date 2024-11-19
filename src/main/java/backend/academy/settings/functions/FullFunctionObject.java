package backend.academy.settings.functions;

import backend.academy.transformations.FullTransformation;

public class FullFunctionObject extends FunctionObject<FullTransformation> {

    private WeightedFunctionObject weighted;
    private AffineFunctionObject after;


    @Override
    public FullTransformation getRealType() {
        return new FullTransformation(weighted.getRealType(), after.getRealType());
    }
}
