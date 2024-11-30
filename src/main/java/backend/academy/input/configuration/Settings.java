package backend.academy.input.configuration;

import backend.academy.input.configuration.deserializers.ColorDeserializer;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.math.transformations.inheritors.AffineTransformation;
import backend.academy.model.math.transformations.inheritors.FullTransformation;
import backend.academy.model.math.transformations.inheritors.SimpleFunction;
import backend.academy.model.math.transformations.inheritors.WeightedTransformation;
import backend.academy.processing.generating.functions.Function;
import backend.academy.utils.ColorUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Объект описывающий основные настройки приложения
 */
@Getter
@Setter
public class Settings {

    @JsonProperty("active-variations")
    private Set<String> activeVariations;

    @JsonProperty("functions")
    private List<FunctionObject> functions;

    @JsonProperty("pipeline")
    private PipelineObject pipeline;

    @JsonProperty("tread-counts")
    private ThreadCounts threadCounts;

    /**
     * Класс для отделения аннотаций для парсинга от функций
     */
    public static class FunctionObject {

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
        @JsonSubTypes({
            @JsonSubTypes.Type(value = AffineTransformation.class, name = "affine"),
            @JsonSubTypes.Type(value = SimpleFunction.class, name = "simple"),
            @JsonSubTypes.Type(value = WeightedTransformation.class, name = "weighted"),
            @JsonSubTypes.Type(value = FullTransformation.class, name = "full")
        })
        private BasicTransformation transformation;

        @JsonDeserialize(using = ColorDeserializer.class)
        private Color color;

        public Function toFunction() {

            return new Function(transformation,
                Objects.requireNonNullElse(color, ColorUtils.getRandomRGB()));
        }
    }

}
