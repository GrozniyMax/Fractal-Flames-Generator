package backend.academy.settings.correction;

import backend.academy.correction.logarithmicGammaCorrection.realisations.LogarithmicGammaCorrector;

public class SingleTreadCorrectorObject extends LogarithmicCorrectionObject<LogarithmicGammaCorrector> {

    public SingleTreadCorrectorObject(double gamma) {
        super(gamma);
    }

    @Override
    public LogarithmicGammaCorrector getRealType() {
        return new LogarithmicGammaCorrector(gamma());
    }
}
