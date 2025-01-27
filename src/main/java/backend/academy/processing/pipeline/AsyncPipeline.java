package backend.academy.processing.pipeline;

import backend.academy.model.image.Image;
import backend.academy.output.image.ImageWriter;
import backend.academy.processing.GeneratingError;
import backend.academy.processing.correction.Corrector;
import backend.academy.processing.generating.Generator;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AsyncPipeline extends AbstractPipeline {

    private final ExecutorService executor;

    public AsyncPipeline(
        Generator generator,
        List<Corrector> correctors,
        ImageWriter imageWriter,
        Path pathToWrite,
        PrintStream out,
        ImageWriter.ImageMode imageMode,
        int threadCount
    ) {
        super(generator, correctors, imageWriter, pathToWrite, out, imageMode);
        executor = Executors.newFixedThreadPool(threadCount);
        log.debug("Pipeline number: {}", threadCount);
    }

    @Override
    public void run() {

        log.debug("Processing using generator: {}, ImageWriter: {}, correctors, {}",
            generator.getClass().getSimpleName(),
            imageWriter.getClass().getSimpleName(),
            correctors.stream().map(Object::getClass).map(Class::getSimpleName).toList());
        out.println("Started generation ...");
        CompletableFuture<Image> currentTast = CompletableFuture.supplyAsync(generator::generate, executor);
        for (Corrector corrector : correctors) {
            currentTast = currentTast.thenApplyAsync(corrector, executor);
        }
        try {
            currentTast.thenAcceptAsync(image -> wrapIoException(image, pathToWrite, imageMode), executor).get();
            executor.close();
        } catch (InterruptedException | ExecutionException e) {
            throw new GeneratingError(e);
        }
    }

    private void wrapIoException(Image image, Path pathToWrite, ImageWriter.ImageMode mode) {
        try {
            imageWriter.writeToFile(image, pathToWrite, mode);
        } catch (Exception e) {
            throw new GeneratingError(e);
        }

    }
}
