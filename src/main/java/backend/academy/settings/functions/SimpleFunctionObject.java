package backend.academy.settings.functions;

import backend.academy.transformations.SimpleFunction;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleFunctionObject extends FunctionObject<SimpleFunction> {

    @JsonProperty("coefs")
    private AffineFunctionObject affineFunction;

    @Override
    public SimpleFunction getRealType() {
        return new SimpleFunction(affineFunction.getRealType());
    }
}
