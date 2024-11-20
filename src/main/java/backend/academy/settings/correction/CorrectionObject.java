package backend.academy.settings.correction;

import backend.academy.correction.Corrector;
import backend.academy.settings.ConvertableToRealType;
import backend.academy.settings.functions.AffineFunctionObject;
import backend.academy.settings.functions.FullFunctionObject;
import backend.academy.settings.functions.SimpleFunctionObject;
import backend.academy.settings.functions.WeightedFunctionObject;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = NoCorrectionObject.class, name = "none"),
    @JsonSubTypes.Type(value = LogarithmicCorrectionObject.class, name = "logarithmic"),
})
public abstract class CorrectionObject<T extends Corrector> implements ConvertableToRealType<T> {
}
