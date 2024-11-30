package backend.academy.input.configuration.corrections;

import backend.academy.input.configuration.Modes;
import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.processing.correction.logarithmicGammaCorrection.GammaCorrectionFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GammaCorrection extends BaseCorrectionClass {

    private String correctionBase;
    private double gamma;

    @JsonCreator
    protected GammaCorrection(
        @JsonProperty("gamma") double gamma,
        @JsonProperty("correction-base") String correctionBase
    ) {
        this.gamma = gamma;
        this.correctionBase = correctionBase;
    }

    @Override
    public AbstractLogarithmicGammaCorrection getRealType(Modes mode) {
        return GammaCorrectionFactory.create(mode, gamma, correctionBase);
    }
}
