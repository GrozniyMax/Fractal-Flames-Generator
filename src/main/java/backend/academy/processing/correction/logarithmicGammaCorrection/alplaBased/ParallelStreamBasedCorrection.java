package backend.academy.processing.correction.logarithmicGammaCorrection.alplaBased;

import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.abstracts.ParallelStreamsBased;

class ParallelStreamBasedCorrection extends ParallelStreamsBased {

    protected ParallelStreamBasedCorrection(double gamma) {
        super(gamma);
    }

    @Override
    protected void changeColor(Pixel pixel, double gammaFactor) {
        pixel.setAlpha(gammaFactor);
    }

}
