package backend.academy.correction.logarithmicGammaCorrection;

import backend.academy.correction.NoCorrection;
import backend.academy.correction.logarithmicGammaCorrection.realisations.CompletableFutureLogarithmicGammaCorrector;
import backend.academy.correction.logarithmicGammaCorrection.realisations.LogarithmicGammaCorrector;
import backend.academy.correction.logarithmicGammaCorrection.realisations.TreadPoolLogarithmicGammaCorrector;
import lombok.extern.log4j.Log4j2;
import java.lang.reflect.InvocationTargetException;

@Log4j2
public class LogarithmicCorrectionFactory {

    public static enum Variations{
        SINGLE_TREAD,
        TREAD_POOL,
        COMPLETABLE_FUTURES
    }

    public AbstractLogarithmicGammaCorrection create(Variations variation, double gamma) {
        Class<? extends AbstractLogarithmicGammaCorrection> clazz;
        switch (variation) {
            case TREAD_POOL -> clazz = TreadPoolLogarithmicGammaCorrector.class;
            case SINGLE_TREAD -> clazz = LogarithmicGammaCorrector.class;
            case COMPLETABLE_FUTURES -> clazz = CompletableFutureLogarithmicGammaCorrector.class;
            case null, default -> {
                throw new IllegalArgumentException("Unable to instanciate");
            }
        }

        try {
            return clazz.getDeclaredConstructor(double.class).newInstance(gamma);
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
            log.error("Unable to create instance");
            return new LogarithmicGammaCorrector(gamma);
        }

    }
}
