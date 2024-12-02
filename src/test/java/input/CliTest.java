package input;

import backend.academy.input.cli.validators.ImageSizeValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

/**
 * Поскольку в данном проекте используется готовая библиотека для работы с консолью, то, будем, считать, что готовая
 * библиотека работает хорошо. И протестируем написанный нами код
 */
public class CliTest {

    JCommander jCommander;

    @Nested
    class IntegerPairConverter {

        ConverterSample objectToParse;

        @Getter
        private class ConverterSample {

            @Parameter(names = {"-p"}, converter = backend.academy.input.cli.converters.IntegerPairConverter.class)
            Pair<Integer, Integer> value;
        }

        @BeforeEach
        void init() {
            objectToParse = new ConverterSample();
            jCommander = JCommander.newBuilder().addObject(objectToParse).build();
        }

        static Stream<Arguments> validData() {
            return Stream.of(
                Arguments.of(new String[] {"-p", "10x10"}, Pair.of(10, 10)),
                Arguments.of(new String[] {"-p", "1x10"}, Pair.of(1, 10)),
                Arguments.of(new String[] {"-p", "10000x10"}, Pair.of(10000, 10)),
                Arguments.of(new String[] {"-p", "-1x10"}, Pair.of(-1, 10)),
                Arguments.of(new String[] {"-p", "1x123657"}, Pair.of(1, 123657)),
                Arguments.of(new String[] {"-p", "5x1"}, Pair.of(5, 1)),
                Arguments.of(new String[] {"-p", "9x120408888"}, Pair.of(9, 120408888)),
                Arguments.of(new String[] {"-p", "4x-15"}, Pair.of(4, -15))
            );
        }

        static Stream<Arguments> invalidData() {
            return Stream.of(
                Arguments.of("-p", "10x"),
                Arguments.of("-p", "x10"),
                Arguments.of("-p", "asdx10"),
                Arguments.of("-p", "gfdx5")
            );
        }

        @ParameterizedTest
        @MethodSource("validData")
        void validSource_shouldContainExpectedValues(String[] src, Pair<Integer, Integer> pair) {
            jCommander.parse(src);

            Assertions.assertThat(objectToParse.value).isEqualTo(pair);
        }

        @ParameterizedTest
        @MethodSource("invalidData")
        void invalidSource_shouldContainExpectedValues(String prefix, String value) {
            Assertions.assertThatThrownBy(() -> jCommander.parse(new String[] {prefix, value}))
                .isInstanceOfAny(IllegalArgumentException.class,
                    ParameterException.class);
        }

    }

    @Nested
    class ImageSizeValidatorTest {

        private ImageSizeValidator validator = new ImageSizeValidator();

        @ParameterizedTest
        @CsvSource(value = {"12x12", "1000x1000", "800x4800", "1920x1280", "1x1", "1x1520"}, delimiter = 'x')
        void validInput_shouldNotThrowException(Integer num1, Integer num2) {
            Assertions.assertThatNoException()
                .isThrownBy(() -> validator.validate("", Pair.of(num1, num2)));
        }

        @ParameterizedTest
        @CsvSource(value = {"-10x10", "-1542x1422", "5454x-102455", "-4544x-7657"}, delimiter = 'x')
        void invalidInput_shouldThrowException(Integer num1, Integer num2) {
            Assertions.assertThatThrownBy(() -> validator.validate("", Pair.of(num1, num2)))
                .isInstanceOfAny(IllegalArgumentException.class,
                    ParameterException.class);
        }

    }

}
