package backend.academy.processing.correction.logarithmicGammaCorrection;

import backend.academy.processing.correction.logarithmicGammaCorrection.alplaBased.AlphaBasedCorrectionFactory;
import backend.academy.processing.correction.logarithmicGammaCorrection.colorBased.ColorBasedCorrectionFactory;
import backend.academy.input.configuration.Modes;
import lombok.experimental.UtilityClass;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class GammaCorrectionFactory {

    private Map<String ,? extends CorrectionTypeFactory> factories = Map
        .of("alpha-based", new AlphaBasedCorrectionFactory(),
            "color-based", new ColorBasedCorrectionFactory());

    public AbstractLogarithmicGammaCorrection create(Modes mode, double gamma, String type){
        return Optional.ofNullable(factories.get(type))
            .map(factory->factory.create(mode, gamma))
            .orElseThrow(()->new IllegalArgumentException("Unknown logarithmic gamma correction type"));
    }
}
