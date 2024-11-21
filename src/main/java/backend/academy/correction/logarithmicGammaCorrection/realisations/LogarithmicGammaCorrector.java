package backend.academy.correction.logarithmicGammaCorrection.realisations;

import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.model.image.Image;
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

        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                if (image.get(j, i).hitCount() == 0) {
                    continue;
                }
                image.get(j, i).multiply(Math.log(1+Math.pow(calculateB(image.get(j, i).hitCount(), hitMax), gamma)));
            }
        }
    }

    private double calculateB(double current, double max) {
        return current/max;
    }
}
