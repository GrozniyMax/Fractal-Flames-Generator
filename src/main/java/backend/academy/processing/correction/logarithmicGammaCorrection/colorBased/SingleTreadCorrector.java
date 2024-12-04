package backend.academy.processing.correction.logarithmicGammaCorrection.colorBased;

import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.abstracts.SingleThreadBased;

class SingleTreadCorrector extends SingleThreadBased {

    SingleTreadCorrector(double gamma) {
        super(gamma);
    }

    @Override
    protected void changeColor(Pixel pixel, double gammaFactor) {
        pixel.multiplyColor(gammaFactor);
    }
}
