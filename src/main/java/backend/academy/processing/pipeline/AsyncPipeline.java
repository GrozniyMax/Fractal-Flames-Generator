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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class AsyncPipeline extends AbstractPipeline {

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public AsyncPipeline(
        Generator generator,
        List<Corrector> correctors,
        ImageWriter imageWriter,
        Path pathToWrite,
        PrintStream out,
        ImageWriter.ImageMode imageMode
    ) {
        super(generator, correctors, imageWriter, pathToWrite, out, imageMode);
    }

    @Override
    public void run() {

        log.info("Processing using generator: {}, ImageWriter: {}", generator.getClass().getSimpleName(),
            imageWriter.getClass().getSimpleName());
        out.println("Started generation ...");
        CompletableFuture<Image> currentTast = CompletableFuture.supplyAsync(generator::generate, executor);
        for (Corrector corrector : correctors) {
            currentTast = currentTast.thenApplyAsync(corrector, executor);
        }
        currentTast.thenAcceptAsync(image -> wrapIoException(image, pathToWrite, imageMode), executor);
        executor.close();
    }

    private void wrapIoException(Image image, Path pathToWrite, ImageWriter.ImageMode mode) {
        try {
            imageWriter.writeToFile(image, pathToWrite, mode);
        } catch (Exception e) {
            throw new GeneratingError(e);
        }

    }
}
