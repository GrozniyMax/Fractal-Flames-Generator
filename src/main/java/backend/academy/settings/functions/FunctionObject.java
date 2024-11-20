package backend.academy.settings.functions;

import backend.academy.settings.ConvertableToRealType;
import backend.academy.settings.deserializers.ColorDeserializer;
import backend.academy.transformations.AbstractTransformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import java.awt.Color;

/**
 * Базовый класс для всех функций<br>
 * Иерархия данных классов повторяет иерархию реализаций {@link backend.academy.transformations.AbstractTransformation}
 * Данная иерархия создана, чтобы отделить парсинг Json от реализаций функции
 */
@Getter @Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AffineFunctionObject.class, name = "affine"),
    @JsonSubTypes.Type(value = SimpleFunctionObject.class, name = "simple"),
    @JsonSubTypes.Type(value = WeightedFunctionObject.class, name = "weighted"),
    @JsonSubTypes.Type(value = FullFunctionObject.class, name = "full")
})
public abstract class FunctionObject<T extends AbstractTransformation> implements ConvertableToRealType<T> {


    @JsonProperty("color")
    @JsonDeserialize(using = ColorDeserializer.class)
    private Color color;

    public abstract T getRealType();
}
