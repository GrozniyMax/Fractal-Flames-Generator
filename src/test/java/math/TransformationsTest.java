package math;

import backend.academy.model.math.transformations.inheritors.AffineTransformation;
import backend.academy.model.math.transformations.inheritors.FullTransformation;
import backend.academy.model.math.transformations.inheritors.SimpleFunction;
import backend.academy.model.math.transformations.inheritors.WeightedTransformation;
import backend.academy.model.plot.Point;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

public class TransformationsTest {

    /**
     * Проверяет на те же базовые правила, что и в {@link VariationsTest}
     */
    @Nested
    class BasicRulesTest {

        AffineTransformation affineTransformation =
            new AffineTransformation(1.5, 74, 0.10245, -451.0004, 0, -0.1555);

        SimpleFunction simpleFunction = new SimpleFunction(affineTransformation);

        WeightedTransformation weightedTransformation =
            new WeightedTransformation(affineTransformation, new double[] {0, -5, 61.455});

        FullTransformation fullTransformation = new FullTransformation(weightedTransformation, affineTransformation);

        @RepeatedTest(10)
        void affine_shouldNotReturnNaN() {
            Point toApply = Instancio.create(Point.class);

            Point actual = affineTransformation.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "X is NaN"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "Y is NaN"));
        }

        @RepeatedTest(10)
        void affine_shouldNotReturnInf() {
            Point toApply = Instancio.create(Point.class);

            Point actual = affineTransformation.apply(toApply);

            Assertions.assertThat(actual.x())

                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "X is Inf"));
            Assertions.assertThat(actual.y())

                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "Y is Inf"));
        }

        @RepeatedTest(10)
        void simple_shouldNotReturnNaN() {
            Point toApply = Instancio.create(Point.class);

            Point actual = simpleFunction.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "X is NaN"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "Y is NaN"));
        }

        @RepeatedTest(10)
        void simple_shouldNotReturnInf() {
            Point toApply = Instancio.create(Point.class);

            Point actual = simpleFunction.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "X is Inf"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "Y is Inf"));
        }


        @RepeatedTest(10)
        void weighted_shouldNotReturnNaN() {
            Point toApply = Instancio.create(Point.class);

            Point actual = weightedTransformation.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "X is NaN"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "Y is NaN"));
        }

        @RepeatedTest(10)
        void weighted_shouldNotReturnInf() {
            Point toApply = Instancio.create(Point.class);

            Point actual = weightedTransformation.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "X is Inf"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "Y is Inf"));
        }

        @RepeatedTest(10)
        void full_shouldNotReturnNaN() {
            Point toApply = Instancio.create(Point.class);

            Point actual = fullTransformation.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "X is NaN"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isNaN(), "Y is NaN"));
        }

        @RepeatedTest(10)
        void full_shouldNotReturnInf() {
            Point toApply = Instancio.create(Point.class);

            Point actual = fullTransformation.apply(toApply);

            Assertions.assertThat(actual.x())
                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "X is Inf"));
            Assertions.assertThat(actual.y())
                .doesNotHave(new Condition<>((v) -> v.isInfinite(), "Y is Inf"));
        }
    }
}
