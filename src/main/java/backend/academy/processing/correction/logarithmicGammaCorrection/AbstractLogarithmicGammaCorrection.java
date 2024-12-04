package backend.academy.processing.correction.logarithmicGammaCorrection;

import backend.academy.model.image.Image;
import backend.academy.processing.correction.Corrector;

public abstract class AbstractLogarithmicGammaCorrection implements Corrector {

    protected final double gamma;

    protected AbstractLogarithmicGammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    protected double calculateGammaFactor(int hitCount, double maxLog) {
        return Math.log10(hitCount) / maxLog;
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
}
