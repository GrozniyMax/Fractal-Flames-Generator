package backend.academy.settings.symmetry;

import backend.academy.settings.ConvertableToRealType;
import backend.academy.settings.functions.AffineFunctionObject;
import backend.academy.settings.functions.FullFunctionObject;
import backend.academy.settings.functions.FunctionObject;
import backend.academy.settings.functions.SimpleFunctionObject;
import backend.academy.settings.functions.WeightedFunctionObject;
import backend.academy.transformations.symmenry.SymmetryTransformation;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = NoneSymmetry.class, name = "none"),
    @JsonSubTypes.Type(value = XSymmetryObject.class, name = "x"),
    @JsonSubTypes.Type(value = YSymmetryObject.class, name = "y"),
    @JsonSubTypes.Type(value = RadiationalSymmetryObject.class, name = "radial"),
})
public abstract class SymmetryObject<T extends SymmetryTransformation> implements ConvertableToRealType<T> {
}
