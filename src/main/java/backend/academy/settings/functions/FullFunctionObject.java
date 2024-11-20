package backend.academy.settings.functions;

import backend.academy.transformations.FullTransformation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FullFunctionObject extends FunctionObject<FullTransformation> {

    @JsonProperty("weighted")
    private WeightedFunctionObject weighted;

    @JsonProperty("post")
    private AffineFunctionObject after;


    @Override
    public FullTransformation getRealType() {
        return new FullTransformation(weighted.getRealType(), after.getRealType());
    }
}
