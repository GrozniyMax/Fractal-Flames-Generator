package backend.academy.processing.correction.logarithmicGammaCorrection.alplaBased;

import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.processing.correction.logarithmicGammaCorrection.CorrectionTypeFactory;
import backend.academy.input.configuration.Modes;

public class AlphaBasedCorrectionFactory implements CorrectionTypeFactory {
    @Override
    public AbstractLogarithmicGammaCorrection create(Modes mode, double gamma) {
        switch (mode) {
            case SINGLE_THREAD -> {
                return new SingleTreadCorrection(gamma);
            }
            case MULTI_THREAD -> {
                return new ParallelStreamBasedCorrection(gamma);
            }
            case null, default ->{
                throw new IllegalArgumentException("Unsupported mode");
            }
        }
    }
}
