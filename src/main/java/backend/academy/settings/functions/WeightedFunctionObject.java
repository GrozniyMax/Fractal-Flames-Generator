package backend.academy.settings.functions;

import backend.academy.transformations.WeightedTransformation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeightedFunctionObject extends FunctionObject<WeightedTransformation> {

    @JsonProperty("coefs")
    private AffineFunctionObject affineFunction;

    @JsonProperty("weights")
    private double[] weights;

    @Override
    public WeightedTransformation getRealType() {
        return new WeightedTransformation(affineFunction.getRealType(), weights);
    }
}
