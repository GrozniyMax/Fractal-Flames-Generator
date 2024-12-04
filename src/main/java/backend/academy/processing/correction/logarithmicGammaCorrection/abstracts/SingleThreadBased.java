package backend.academy.processing.correction.logarithmicGammaCorrection.abstracts;

import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import backend.academy.processing.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;

public abstract class SingleThreadBased extends AbstractLogarithmicGammaCorrection {

    protected SingleThreadBased(double gamma) {
        super(gamma);
    }

    protected abstract void changeColor(Pixel pixel, double gammaFactor);

    @Override
    public final void accept(Image image) {

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
                changeColor(current, gammaFactor);
            }
        }
    }

    protected int maxHit(Image image) {
        int hitMax = 0;
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                hitMax = Math.max(hitMax, image.get(j, i).hitCount());
            }
        }
        return hitMax;
    }

    protected double maxLog(Image image) {
        return Math.log10(maxHit(image));
    }
}
