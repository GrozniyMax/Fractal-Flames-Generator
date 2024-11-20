package backend.academy.settings.correction;

import backend.academy.correction.logarithmicGammaCorrection.realisations.TreadPoolLogarithmicGammaCorrector;

public class TreadPoolLogarithmicCorrector extends LogarithmicCorrectionObject<TreadPoolLogarithmicGammaCorrector> {
    public TreadPoolLogarithmicCorrector(double gamma) {
        super(gamma);
    }

    @Override
    public TreadPoolLogarithmicGammaCorrector getRealType() {
        return new TreadPoolLogarithmicGammaCorrector(gamma());
    }
}
