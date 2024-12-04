package backend.academy.processing.correction.logarithmicGammaCorrection.abstracts;

import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import java.util.Arrays;

public abstract class ParallelStreamsBased extends AbstractLogarithmicGammaCorrection {

    protected ParallelStreamsBased(double gamma) {
        super(gamma);
    }

    protected abstract void changeColor(Pixel pixel, double gammaFactor);

    @Override
    public final void accept(Image image) {
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
                changeColor(pixel, gammaFactor);
            }));
    }
}
