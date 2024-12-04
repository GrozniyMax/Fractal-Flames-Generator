package backend.academy.processing.correction.logarithmicGammaCorrection.colorBased;

import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.abstracts.ParallelStreamsBased;
import lombok.extern.log4j.Log4j2;

@Log4j2
class ParallelStreamsBasedCorrection extends ParallelStreamsBased {

    ParallelStreamsBasedCorrection(double gamma) {
        super(gamma);
    }

    @Override
    protected void changeColor(Pixel pixel, double gammaFactor) {
        pixel.multiplyColor(gammaFactor);
    }
}
