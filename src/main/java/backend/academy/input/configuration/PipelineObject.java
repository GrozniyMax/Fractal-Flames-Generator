package backend.academy.input.configuration;

import backend.academy.input.configuration.corrections.BaseCorrectionClass;
import backend.academy.input.configuration.corrections.GammaCorrection;
import backend.academy.input.configuration.corrections.StupidCorrectionObject;
import backend.academy.input.configuration.deserializers.PlotDeserializer;
import backend.academy.model.math.MathFunction;
import backend.academy.model.math.basicMath.Addition;
import backend.academy.model.math.basicMath.Multiplication;
import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.math.symmetry.implementation.RadialSymmetryTransformation;
import backend.academy.model.math.symmetry.implementation.XSymmetryTransformation;
import backend.academy.model.math.symmetry.implementation.YSymmetryTransformation;
import backend.academy.model.math.transformations.inheritors.AffineTransformation;
import backend.academy.model.math.transformations.inheritors.FullTransformation;
import backend.academy.model.math.transformations.inheritors.SimpleFunction;
import backend.academy.model.math.transformations.inheritors.WeightedTransformation;
import backend.academy.model.plot.Plot;
import backend.academy.output.image.ImageWriter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Getter;

/**
 * Объект описывающий процесс работы приложения
 */
@Getter
public class PipelineObject {

    @JsonProperty("mode")
    private Modes mode;

    @JsonProperty("plot")
    @JsonDeserialize(using = PlotDeserializer.class)
    private Plot plot;

    @JsonProperty("post-transformations")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = AffineTransformation.class, name = "affine"),
        @JsonSubTypes.Type(value = SimpleFunction.class, name = "simple"),
        @JsonSubTypes.Type(value = WeightedTransformation.class, name = "weighted"),
        @JsonSubTypes.Type(value = FullTransformation.class, name = "full"),
        @JsonSubTypes.Type(value = Multiplication.class, name = "multiplication"),
        @JsonSubTypes.Type(value = Addition.class, name = "addition")
    })
    private List<MathFunction> postTransformations;

    @JsonProperty("image-mode")
    private ImageWriter.ImageMode imageMode;

    @JsonProperty("symmetry")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = RadialSymmetryTransformation.class, name = "radial"),
        @JsonSubTypes.Type(value = XSymmetryTransformation.class, name = "x"),
        @JsonSubTypes.Type(value = YSymmetryTransformation.class, name = "y")
    })
    private Symmetry symmetry;

    @JsonProperty("correction")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = StupidCorrectionObject.class, name = "stupid"),
        @JsonSubTypes.Type(value = GammaCorrection.class, name = "logarithmic")
    })
    private List<BaseCorrectionClass> corrections;

}
