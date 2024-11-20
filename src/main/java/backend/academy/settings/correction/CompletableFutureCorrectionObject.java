package backend.academy.settings.correction;

import backend.academy.correction.logarithmicGammaCorrection.realisations.CompletableFutureLogarithmicGammaCorrector;

public class CompletableFutureCorrectionObject
    extends LogarithmicCorrectionObject<CompletableFutureLogarithmicGammaCorrector> {
    public CompletableFutureCorrectionObject(double gamma) {
        super(gamma);
    }

    @Override
    public CompletableFutureLogarithmicGammaCorrector getRealType() {
        return new CompletableFutureLogarithmicGammaCorrector(gamma());
    }
}
