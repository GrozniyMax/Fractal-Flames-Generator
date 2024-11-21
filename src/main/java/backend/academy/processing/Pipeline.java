package backend.academy.processing;

import backend.academy.correction.Corrector;
import backend.academy.generating.Generator;
import backend.academy.generating.SingleTreadGenerator;
import backend.academy.model.image.Image;
import backend.academy.output.image.ImageWriter;
import lombok.RequiredArgsConstructor;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Pipeline {

    private final Generator generator;
    private final List<Corrector> correctors;
    private final ImageWriter imageWriter;
    private final Path pathToWrite;
    private final PrintStream out;

    public void run() {
        try {
            out.println("Started generation ...");
            Image generated = generator.generate();
            out.println("Applying post-correction ...");
            Consumer<Image> finalCorrector = Corrector.identity();
            for (Corrector corrector : correctors) {
                finalCorrector = corrector.andThen(corrector);
            }
            finalCorrector.accept(generated);
            out.println("Writing to file ...");
            imageWriter.writeToFile(generated, pathToWrite);
        } catch (Exception e) {
            throw new GeneratingError(e);
        }
    }
}
