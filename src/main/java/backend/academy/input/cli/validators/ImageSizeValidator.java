package backend.academy.input.cli.validators;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import it.unimi.dsi.fastutil.Pair;

/**
 * Валидатор для размеров изображения
 */
public class ImageSizeValidator implements IValueValidator<Pair<Integer, Integer>> {
    @Override
    public void validate(String name, Pair<Integer, Integer> value) throws ParameterException {
        if (value.left() < 0 || value.right() < 0) {
            throw new ParameterException("Each number of size must be above 0");
        }
    }
}
