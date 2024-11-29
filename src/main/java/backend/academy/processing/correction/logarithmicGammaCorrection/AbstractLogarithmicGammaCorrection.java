package backend.academy.processing.correction.logarithmicGammaCorrection;

import backend.academy.processing.correction.Corrector;

public abstract class AbstractLogarithmicGammaCorrection implements Corrector {

    protected final double gamma;

    protected AbstractLogarithmicGammaCorrection(double gamma) {
        this.gamma = gamma;
    }
}
