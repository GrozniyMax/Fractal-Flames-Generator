package backend.academy.processing.pipeline;

import backend.academy.processing.correction.Corrector;
import backend.academy.processing.generating.Generator;
import backend.academy.output.image.ImageWriter;
import lombok.RequiredArgsConstructor;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractPipeline {

    protected final Generator generator;
    protected final List<Corrector> correctors;
    protected final ImageWriter imageWriter;
    protected final Path pathToWrite;
    protected final PrintStream out;
    protected final ImageWriter.ImageMode imageMode;

    public abstract void run();
}
