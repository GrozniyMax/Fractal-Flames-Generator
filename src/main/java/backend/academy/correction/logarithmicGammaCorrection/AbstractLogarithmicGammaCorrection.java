package backend.academy.correction.logarithmicGammaCorrection;

import backend.academy.correction.Corrector;

public abstract class AbstractLogarithmicGammaCorrection implements Corrector {

    protected final double gamma;

    protected AbstractLogarithmicGammaCorrection(double gamma) {
        this.gamma = gamma;
    }
}
