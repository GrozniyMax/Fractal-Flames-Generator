package backend.academy.correction.logarithmicGammaCorrection.realisations;

import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import lombok.RequiredArgsConstructor;
import java.awt.Color;

public class LogarithmicGammaCorrector extends AbstractLogarithmicGammaCorrection {

    public LogarithmicGammaCorrector(double gamma) {
        super(gamma);
    }

    @Override
    public void accept(Image image) {
        int hitMax = 0;
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                hitMax = Math.max(hitMax, image.get(j, i).hitCount());
            }
        }

        double maxLog = Math.log10(hitMax);
        double gammaFactor;
        Pixel current;
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                current = image.get(j, i);
                if (current.hitCount() == 0) {
                    continue;
                }
                gammaFactor = Math.log10(current.hitCount())/maxLog;
                image.get(j, i).setColor(new Color(
                    (int) Math.min(current.getColor().getRed()*gammaFactor, 255),
                    (int) Math.min(current.getColor().getGreen()*gammaFactor, 255),
                    (int) Math.min(current.getColor().getBlue()*gammaFactor, 255)
                ));
            }
        }
    }

    private double calculateB(double current, double max) {
        return current/max;
    }
}
