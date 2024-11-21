package backend.academy.correction.logarithmicGammaCorrection;

import backend.academy.Main;
import backend.academy.correction.logarithmicGammaCorrection.realisations.CompletableFutureLogarithmicGammaCorrector;
import backend.academy.correction.logarithmicGammaCorrection.realisations.LogarithmicGammaCorrector;
import backend.academy.correction.logarithmicGammaCorrection.realisations.TreadPoolLogarithmicGammaCorrector;
import lombok.experimental.UtilityClass;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class GammaCorrectionFactory {

    private Map<String ,Class<? extends AbstractLogarithmicGammaCorrection>> classes = Map
        .of("completable-future", CompletableFutureLogarithmicGammaCorrector.class,
            "single", LogarithmicGammaCorrector.class,
            "thread-pool",TreadPoolLogarithmicGammaCorrector.class);

    public AbstractLogarithmicGammaCorrection create(String threadOption, double gamma){
        try {
            return Optional.ofNullable(classes.get(threadOption))
                .orElseThrow(() -> new IllegalArgumentException("no such thread option"))
                .getDeclaredConstructor(double.class).newInstance(gamma);
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
            throw new IllegalArgumentException("Unable to find su");
        }
    }
}
