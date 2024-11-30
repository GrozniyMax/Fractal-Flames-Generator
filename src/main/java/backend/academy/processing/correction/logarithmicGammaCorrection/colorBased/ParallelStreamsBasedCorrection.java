package backend.academy.processing.correction.logarithmicGammaCorrection.colorBased;

import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;

@Log4j2
class ParallelStreamsBasedCorrection extends AbstractLogarithmicGammaCorrection {

    ParallelStreamsBasedCorrection(double gamma) {
        super(gamma);
    }

    @Override
    public void accept(Image image) {
        double maxLog = image.rowsStream()
            .flatMap(Arrays::stream)
            .parallel()
            .map(Pixel::hitCount)
            .max(Integer::compare)
            .map(Number::doubleValue)
            .map(Math::log10)
            .orElse(1.0);

        image.rowsStream()
            .flatMap(Arrays::stream)
            .parallel()
            .forEach((pixel -> {
                double gammaFactor = Math.log10(pixel.hitCount()) / maxLog;
                pixel.multiplyColor(gammaFactor);
            }));
        log.debug("Finished correction");
    }
}
