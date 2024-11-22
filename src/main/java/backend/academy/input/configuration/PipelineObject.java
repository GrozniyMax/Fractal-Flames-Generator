package backend.academy.input.configuration;

import backend.academy.correction.Corrector;
import backend.academy.correction.NoCorrection;
import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.correction.logarithmicGammaCorrection.GammaCorrectionFactory;
import backend.academy.correction.stupidCorrection.SingleTreadStupidCorrection;
import backend.academy.correction.stupidCorrection.StupidCorrection;
import backend.academy.input.configuration.deserializers.PlotDeserializer;
import backend.academy.model.image.Image;
import backend.academy.model.math.MathFucntion;
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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

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
    private List<MathFucntion> postTransformations;

    @JsonProperty("symmetry")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = RadialSymmetryTransformation.class, name = "radial"),
        @JsonSubTypes.Type(value = XSymmetryTransformation.class, name = "x"),
        @JsonSubTypes.Type(value = YSymmetryTransformation.class, name = "y")
    })
    private Symmetry symmetry;

    @JsonProperty("corrections")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = SingleTreadStupidCorrection.class, name = "stupid"),
        @JsonSubTypes.Type(value = NoCorrection.class, name = "none"),
        @JsonSubTypes.Type(value = LogarithmicGammaCorrectionObject.class, name = "logarithmic")
    })
    private List<Corrector> corrections;


    public static class LogarithmicGammaCorrectionObject extends AbstractLogarithmicGammaCorrection {

        private String treadOption;

        @JsonCreator
        protected LogarithmicGammaCorrectionObject(double gamma, String treadOption) {
            super(gamma);
            this.treadOption = treadOption;
        }

        @Override
        public void accept(Image image) {
            return;
        }

        public AbstractLogarithmicGammaCorrection getReal() {
            return GammaCorrectionFactory.create(treadOption, gamma);
        }
    }

    public PipelineObject complete() {
        PipelineObject result = new PipelineObject();
        result.mode = this.mode;
        result.plot = this.plot;
        result.symmetry = this.symmetry;
        result.postTransformations = this.postTransformations;
        if (Objects.isNull(this.corrections)){
            this.corrections = List.of();
        }
        result.corrections = new LinkedList<>(this.corrections);

        this.corrections.forEach(corrector -> {
            if (corrector instanceof LogarithmicGammaCorrectionObject t){
                result.corrections.add(t);
                return;
            }
            result.corrections.add(corrector);
        });
        return result;
    }


}
