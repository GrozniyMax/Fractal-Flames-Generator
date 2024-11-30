package backend.academy.processing.correction.logarithmicGammaCorrection;

import backend.academy.input.configuration.Modes;
import backend.academy.processing.correction.logarithmicGammaCorrection.alplaBased.AlphaBasedCorrectionFactory;
import backend.academy.processing.correction.logarithmicGammaCorrection.colorBased.ColorBasedCorrectionFactory;
import java.util.Map;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GammaCorrectionFactory {

    private final Map<String, ? extends CorrectionTypeFactory> factories = Map
        .of("alpha-based", new AlphaBasedCorrectionFactory(),
            "color-based", new ColorBasedCorrectionFactory());

    public AbstractLogarithmicGammaCorrection create(Modes mode, double gamma, String type) {
        return Optional.ofNullable(factories.get(type))
            .map(factory -> factory.create(mode, gamma))
            .orElseThrow(() -> new IllegalArgumentException("Unknown logarithmic gamma correction type"));
    }
}
