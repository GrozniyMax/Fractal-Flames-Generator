package backend.academy.settings.correction;

import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.settings.deserializers.LogarithmicCorrectionObjectDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonDeserialize(using = LogarithmicCorrectionObjectDeserializer.class)
public abstract class LogarithmicCorrectionObject<T extends AbstractLogarithmicGammaCorrection>
    extends CorrectionObject<T>{

    @JsonProperty("gamma")
    private double gamma;

    public LogarithmicCorrectionObject(double gamma) {
        this.gamma = gamma;
    }

}
