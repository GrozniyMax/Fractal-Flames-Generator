package backend.academy.processing;

import backend.academy.correction.Corrector;
import backend.academy.generating.Generator;
import backend.academy.model.image.Image;
import backend.academy.output.image.ImageWriter;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.nio.file.Path;

@RequiredArgsConstructor
public class Pipeline {

    private final Generator generator;
    private final Corrector corrector;
    private final ImageWriter imageWriter;
    private final Path pathToWrite;

    public void run(){
        try {
            Image generated = generator.generate();
            corrector.accept(generated);
            imageWriter.writeToFile(generated, pathToWrite);
        } catch (Exception e) {
            throw new GeneratingError(e);
        }
    }
}
