package backend.academy.processing.correction.logarithmicGammaCorrection.alplaBased;

import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import java.util.Arrays;

class ParallelStreamBasedCorrection extends AbstractLogarithmicGammaCorrection {

    protected ParallelStreamBasedCorrection(double gamma) {
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
            .filter(pixel -> pixel.hitCount() > 0)
            .forEach((pixel -> {
                double gammaFactor = Math.log10(pixel.hitCount()) / maxLog;
                pixel.setAlpha(gammaFactor);
            }));
    }
}
