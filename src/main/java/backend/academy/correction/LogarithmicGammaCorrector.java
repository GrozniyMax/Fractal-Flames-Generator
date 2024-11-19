package backend.academy.correction;

import backend.academy.Main;
import backend.academy.model.image.Image;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogarithmicGammaCorrector implements Corrector{

    private final double gamma;

    @Override
    public void accept(Image image) {
        int hitMax = 0;
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                hitMax = Math.max(hitMax, image.get(j, i).hitCount());
            }
        }

        double maxHitLog = Math.log(hitMax);

        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                double gammaFactor = Math.log10(image.get(j, i).hitCount()) / maxHitLog;
                gammaFactor = gammaFactor * gamma;
                image.get(j, i).setColor(image.get(j, i).getColor());
            }
        }
    }
}
