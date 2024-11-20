package backend.academy.output.cli;

import backend.academy.model.plot.Point;
import backend.academy.output.cli.converters.DoublePairConverter;
import backend.academy.output.cli.converters.IntegerPairConverter;
import backend.academy.output.cli.converters.PointConverter;
import backend.academy.output.cli.validators.ImageSizeValidator;
import com.beust.jcommander.Parameter;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import java.nio.file.Path;

@Getter
public class CommandLineSettings {

    @Parameter(
        names = {"-s", "--image-size"},
        description = "Image size in format WIDTHxHEIGHT",
        defaultValueDescription = "800x800",
        converter = IntegerPairConverter.class,
        validateValueWith = ImageSizeValidator.class
    )
    private Pair<Integer, Integer> imageSize = Pair.of(800, 800);


    @Parameter(
        names = {"-o", "--output"},
        description = "Output file",
        defaultValueDescription = "output.png"
    )
    private Path output = Path.of("output.png");

    @Parameter(
        names = {"-i", "--iterations"},
        description = "Number of iterations",
        defaultValueDescription = "100 000"
    )
    private int iterations = 3_000_000;

    @Parameter(
        names = {"-j", "--json-hath"},
        description = "Json path for settings"
    )
    private Path jsonPath = null;

    @Parameter(names = {"-h", "--help"}, help = true, description = "Show help")
    private boolean help = false;
}
