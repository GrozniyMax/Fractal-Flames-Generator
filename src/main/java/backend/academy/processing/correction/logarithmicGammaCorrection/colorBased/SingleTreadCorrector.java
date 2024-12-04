package backend.academy.processing.correction.logarithmicGammaCorrection.colorBased;

import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;

class SingleTreadCorrector extends AbstractLogarithmicGammaCorrection {

    SingleTreadCorrector(double gamma) {
        super(gamma);
    }

    @Override
    public void accept(Image image) {

        double maxLog = maxLog(image);
        double gammaFactor;
        Pixel current;
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                current = image.get(j, i);
                if (current.hitCount() == 0) {
                    continue;
                }
                gammaFactor = Math.log10(current.hitCount()) / maxLog;
                image.get(j, i).multiplyColor(gammaFactor);
            }
        }
    }
}
