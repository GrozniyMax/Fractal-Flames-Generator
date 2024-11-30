package backend.academy.input.cli.converters;

import com.beust.jcommander.IStringConverter;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;

/**
 * Сейчас не используется
 */
public class DoublePairConverter implements IStringConverter<Pair<Double, Double>> {
    @Override
    public Pair<Double, Double> convert(String value) {
        String[] split = value.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException("Pair should be in format 'x,y'");
        }
        return new DoubleDoubleImmutablePair(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }
}
