package backend.academy.output.cli.converters;

import backend.academy.model.plot.Point;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class PointConverter implements IStringConverter<Point> {
    @Override
    public Point convert(String s) {
        try {
            var splitted = s.replace("[", "").replace("]", "").replace(" ", "")
                .replace("(", "").replace(")", "").split("[;,]");

            return new Point(Double.parseDouble(splitted[0]),
                Double.parseDouble(splitted[1]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParameterException(e.getMessage() != null ? e.getMessage() : "Invalid point");
        }
    }
}
