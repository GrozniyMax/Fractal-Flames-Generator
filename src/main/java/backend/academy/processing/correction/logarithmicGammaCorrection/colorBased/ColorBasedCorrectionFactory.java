package backend.academy.processing.correction.logarithmicGammaCorrection.colorBased;

import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.processing.correction.logarithmicGammaCorrection.CorrectionTypeFactory;
import backend.academy.input.configuration.Modes;

public class ColorBasedCorrectionFactory implements CorrectionTypeFactory {
    @Override
    public AbstractLogarithmicGammaCorrection create(Modes mode, double gamma) {
        switch (mode) {
            case SINGLE_THREAD -> {
                return new SingleTreadCorrector(gamma);
            }
            case MULTI_THREAD, OPTIMAL -> {
                return new ParallelStreamsBasedCorrection(gamma);
            }
            case null, default ->{
                throw new IllegalArgumentException("Unsupported mode");
            }
        }
    }
}
