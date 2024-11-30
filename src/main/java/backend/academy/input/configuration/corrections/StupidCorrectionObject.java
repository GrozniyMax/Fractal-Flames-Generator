package backend.academy.input.configuration.corrections;

import backend.academy.input.configuration.Modes;
import backend.academy.processing.correction.stupidCorrection.SingleTreadStupidCorrection;
import backend.academy.processing.correction.stupidCorrection.StupidCorrection;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StupidCorrectionObject extends BaseCorrectionClass {

    @JsonProperty("hit-count")
    private int hitCount;

    @Override
    public StupidCorrection getRealType(Modes mode) {

        return new SingleTreadStupidCorrection(hitCount);
    }
}
