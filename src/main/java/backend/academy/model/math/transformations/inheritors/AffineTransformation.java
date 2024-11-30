package backend.academy.model.math.transformations.inheritors;

import backend.academy.input.configuration.deserializers.AffineTransformationDeserialization;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.plot.Point;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Аффинное(линейное) преобразование
 */
@JsonDeserialize(using = AffineTransformationDeserialization.class)
public class AffineTransformation extends BasicTransformation {

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    public AffineTransformation(double a, double b, double c, double d, double e, double f) {
        super(null);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    public Point apply(Point point) {
        // тк мы описываем по сути математические функции, то будет хорошо, если функции не будут менять данные
        return new Point(
            a * point.x() + b * point.y() + c,
            d * point.x() + d * point.y() + f
        );
    }

    public static AffineTransformation random() {
        AffineTransformation result = randomAffine();
        while (!result.correct()) {
            result = randomAffine();
        }
        return result;
    }

    private static AffineTransformation randomAffine() {
        final double MIN_VALUE_FOR_MATRIX_COEFFICIENTS = -1;
        final double MAX_VALUE_FOR_MATRIX_COEFFICIENTS = 1;

        final double MIN_VALUE_FOR_ADDITIONAL_COEFFICIENTS = -0.5;
        final double MAX_VALUE_FOR_ADDITIONAL_COEFFICIENTS = 0.5;

        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        Random random = ThreadLocalRandom.current();
        a = random.nextDouble(MIN_VALUE_FOR_MATRIX_COEFFICIENTS, MAX_VALUE_FOR_MATRIX_COEFFICIENTS);
        b = random.nextDouble(MIN_VALUE_FOR_MATRIX_COEFFICIENTS, MAX_VALUE_FOR_MATRIX_COEFFICIENTS);
        c = random.nextDouble(MIN_VALUE_FOR_MATRIX_COEFFICIENTS, MAX_VALUE_FOR_MATRIX_COEFFICIENTS);
        d = random.nextDouble(MIN_VALUE_FOR_MATRIX_COEFFICIENTS, MAX_VALUE_FOR_MATRIX_COEFFICIENTS);
        e = random.nextDouble(MIN_VALUE_FOR_ADDITIONAL_COEFFICIENTS, MAX_VALUE_FOR_ADDITIONAL_COEFFICIENTS);
        f = random.nextDouble(MIN_VALUE_FOR_ADDITIONAL_COEFFICIENTS, MAX_VALUE_FOR_ADDITIONAL_COEFFICIENTS);
        return new AffineTransformation(a, b, c, d, e, f);
    }

    private boolean correct() {
        return (a * a + d * d < 1)
            && (b * b + e * e < 1)
            && (a * a + b * b + d * d + e * e < 1 - (a * e - b * d) * (a * e - b * d));
    }
}
