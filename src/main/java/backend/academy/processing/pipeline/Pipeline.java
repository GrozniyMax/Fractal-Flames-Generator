package backend.academy.processing.pipeline;

import backend.academy.processing.correction.Corrector;
import backend.academy.processing.generating.Generator;
import backend.academy.model.image.Image;
import backend.academy.output.image.ImageWriter;
import backend.academy.processing.GeneratingError;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@Log4j2
public class Pipeline extends AbstractPipeline{

    public Pipeline(
        Generator generator,
        List<Corrector> correctors,
        ImageWriter imageWriter,
        Path pathToWrite,
        PrintStream out,
        ImageWriter.ImageMode imageMode
    ) {
        super(generator, correctors, imageWriter, pathToWrite, out, imageMode);
    }

    public void run() {
        log.debug("Started pipeline with {}, {}, {}, mode:{}", generator.getClass(), imageWriter.getClass(),
            correctors.stream().map(Object::getClass).map(Class::getSimpleName).toList(), imageMode);

        try {
            out.println("Started generation ...");
            Image generated = generator.generate();
            out.println("Applying post-correction ...");
            Consumer<Image> finalCorrector = Corrector.identity();
            for (Corrector corrector : correctors) {
                finalCorrector = corrector.andThen((Consumer<? super Image>) corrector);
            }
            finalCorrector.accept(generated);
            out.println("Writing to file ...");
            imageWriter.writeToFile(generated, pathToWrite, imageMode);
        } catch (Exception e) {
            throw new GeneratingError(e);
        }
    }
}
