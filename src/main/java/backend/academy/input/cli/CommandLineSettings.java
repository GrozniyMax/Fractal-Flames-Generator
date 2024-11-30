package backend.academy.input.cli;

import backend.academy.input.cli.converters.IntegerPairConverter;
import backend.academy.input.cli.validators.ImageSizeValidator;
import com.beust.jcommander.Parameter;
import it.unimi.dsi.fastutil.Pair;
import java.nio.file.Path;
import lombok.Getter;

@Getter
@SuppressWarnings("checkstyle:MagicNumber")
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

    @Parameter(
        names = {"--suppress-output"},
        description = "Whether to suppress output"
    )
    private boolean suppress = false;

    @Parameter(
        names = {"--debug"},
        description = "Whetver to enable debug or not",
        defaultValueDescription = "Debug is disabled by default"
    )
    private boolean debug = false;
}
