package math;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.math.variations.Variations;
import backend.academy.model.math.variations.implementations.Cross;
import backend.academy.model.math.variations.implementations.Ex;
import backend.academy.model.math.variations.implementations.Handkerchief;
import backend.academy.model.math.variations.implementations.Horseshoe;
import backend.academy.model.math.variations.implementations.Identity;
import backend.academy.model.math.variations.implementations.Sinusoidal;
import backend.academy.model.math.variations.implementations.Spherical;
import backend.academy.model.math.variations.implementations.Swirl;
import backend.academy.model.math.variations.implementations.Tangent;
import backend.academy.model.plot.Point;
import java.util.stream.Stream;
import it.unimi.dsi.fastutil.Pair;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.data.Offset;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * В данном случае я не вижу смысла проверять каждую вариацию в отдельности. Я бы проверил все вариации разом на
 * основные принципы:
 * <li>В результате не получается NaN</li>
 * <li>В результате не получается Inf</li>
 * <li>В результате не получается -Inf</li>
 */
public class VariationsTest {

    // Делаем по 5 итераций для каждой трансформации
    static Stream<Arguments> createVariations() {
        return Variations.getFull().getVariations()
            .flatMap(variation -> Stream.generate(() -> variation).limit(5))
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("createVariations")
    void variations_shouldNotReturnNanInAnyCoordinate(SimpleVariation variation) {
        Point toApply = Instancio.create(Point.class);

        Point generated = variation.apply(toApply);

        Assertions.assertThat(generated.x()).doesNotHave(new Condition<>((v) -> v.isNaN(), "X is NaN"));
        Assertions.assertThat(generated.y()).doesNotHave(new Condition<>((v) -> v.isNaN(), "Y is NaN"));
    }

    @ParameterizedTest
    @MethodSource("createVariations")
    void variations_shouldNotReturnInfInAnyCoordinate(SimpleVariation variation) {
        Point toApply = Instancio.create(Point.class);

        Point generated = variation.apply(toApply);

        Assertions.assertThat(generated.x()).doesNotHave(new Condition<>((v) -> v.isInfinite(), "X is Inf"));
        Assertions.assertThat(generated.y()).doesNotHave(new Condition<>((v) -> v.isInfinite(), "Y is Inf"));
    }

    static Stream<Arguments> pointsConversionDataSrc() {

        return Stream.of(
            Arguments.of(new Identity(), new Point(1, 2), new Point(1, 2)),
            Arguments.of(new Sinusoidal(), new Point(1, 2), new Point( 0.84147, 0.90929)),
            Arguments.of(new Spherical(), new Point(1, 2), new Point( 0.19999, 0.39999)),
            Arguments.of(new Swirl(), new Point(1, 2), new Point( -1.52624, -1.63418)),
            Arguments.of(new Horseshoe(), new Point(1, 2), new Point( -1.34164, 1.78885)),
            Arguments.of(new Handkerchief(), new Point(1, 2), new Point( 0.95622,-0.44779 )),
            Arguments.of(new Ex(), new Point(1, 2), new Point( 0.15690, 0.19282)),
            Arguments.of(new Cross(), new Point(1, 2), new Point( 0.33333, 0.66666)),
            Arguments.of(new Tangent(), new Point(1, 2), new Point( -2.02205, -2.18503))
        );
    }

    @ParameterizedTest
    @MethodSource("pointsConversionDataSrc")
    void applyTest_shouldReturnExpectedValueTill5thDigit(SimpleVariation variation, Point applyTo, Point expected) {
        // Проверяем числа на соответствие до 5ого знака, то
        double epsilon = 1e-5;

        Point actual = variation.apply(applyTo);

        Assertions.assertThat(actual.x()).isCloseTo(expected.x(), Offset.offset(epsilon));
    }
}
