package backend.academy.input.cli.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import it.unimi.dsi.fastutil.Pair;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class IntegerPairConverter implements IStringConverter<Pair<Integer, Integer>> {
    @Override
    public Pair<Integer, Integer> convert(String s) {
        try {
            var split = s.replace(" +", "").split("x");
            return Pair.of(Integer.parseInt(split[0]),
                Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            throw new ParameterException("Unable to parse numbers from input");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParameterException("Unable to split pair of numbers");
        }
    }
}
