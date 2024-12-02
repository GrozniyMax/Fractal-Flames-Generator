package math;

import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.math.symmetry.implementation.RadialSymmetryTransformation;
import backend.academy.model.math.symmetry.implementation.XSymmetryTransformation;
import backend.academy.model.math.symmetry.implementation.YSymmetryTransformation;
import backend.academy.model.plot.Point;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SymmetryTest {

    static Stream<Arguments> argSrc() {
        return Stream.of(
            Arguments.of(new XSymmetryTransformation(), new Point(1, 1),
                Set.of(new Point(1, 1), new Point(1, -1))),
            Arguments.of(new YSymmetryTransformation(), new Point(1, 1),
                Set.of(new Point(1, 1), new Point(-1, 1))),
            Arguments.of(new RadialSymmetryTransformation(4), new Point(1, 1),
                Set.of(new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)))
        );
    }

    @ParameterizedTest
    @MethodSource("argSrc")
    void symmetry_shouldReturnExpectedSet(Symmetry symmetry, Point applyTo, Set<Point> expected) {
        Set<Point> actual = symmetry.apply(applyTo);
        Set<Point> actualRounded = new HashSet<>(actual.size());
        actual.forEach(p->{
            actualRounded.add(round(p));
        });
        Set<Point> expectedRounded = expected.stream().map(this::round).collect(Collectors.toSet());

        Assertions.assertThat(actualRounded).containsAll(expectedRounded);
    }


    // Округляем до 5ого знака
    Point round(Point point) {
        double x = Math.round(point.x()*1e+5)*1e-5;
        double y = Math.round(point.y()*1e+5)*1e-5;
        return new Point(x, y);
    }

}
