package backend.academy.transformations;

import backend.academy.model.plot.Point;
import java.security.SecureRandom;

public record AffineTransformation(double a, double b, double c, double d, double e, double f)
    implements AbstractTransformation{

    public Point apply(Point point) {
        // тк мы описываем по сути математические функции, то будет хорошо, если функции не будут менять данные
        return new Point(
            a * point.x() + b * point.y() + c,
            d * point.x() + d * point.y() + f
        );
    }

    public static AffineTransformation random() {
        SecureRandom random = new SecureRandom();
        double a = random.nextDouble(-1, 1);
        double b = random.nextDouble(-1, 1);
        double c = random.nextDouble(-1, 1);
        double d = random.nextDouble(-1, 1);
        double e = random.nextDouble(-1, 1);
        double f = random.nextDouble(-1, 1);
        AffineTransformation result = new AffineTransformation(a, b, c, d, e, f);
        while (!result.correct()) {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);
            result = new AffineTransformation(a, b, c, d, e, f);
        }
        return result;
    }

    private boolean correct() {
        return (a*a + d*d < 1)
            && (b*b + e*e < 1)
            && (a*a+b*b+d*d+e*e < 1 - (a*e - b*d)*(a*e - b*d));
    }

}
