package backend.academy.processing.correction.logarithmicGammaCorrection;

import backend.academy.input.configuration.Modes;

public interface CorrectionTypeFactory {

    AbstractLogarithmicGammaCorrection create(Modes mode, double gamma);
}
