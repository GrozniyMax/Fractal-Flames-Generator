package backend.academy.processing.generating;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import backend.academy.processing.generating.functions.Function;
import backend.academy.processing.generating.functions.Functions;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Поскольку в рамках нашего алгоритма, влияние случайности достаточно большое, то внесем еще большее ее влияние.<br>
 * Данная реализация не гарантирует очередности генерации точек, так и очереди добавления в график
 */
@Log4j2
public class YetAnotherThreadPoolGenerator extends Generator {

    private static final int SAMPLES = 100;

    private final ExecutorService executor;

    private final int iterationsPerSample = this.iterations / SAMPLES + 1;

    protected YetAnotherThreadPoolGenerator(
        Functions functions,
        Image image,
        Plot plot,
        int iterations,
        PrintStream out,
        int threadsCount
    ) {
        super(functions, image, plot, iterations, out);
        executor = Executors.newFixedThreadPool(threadsCount);
        log.debug("Thread-count: {}", threadsCount);
    }

    @Override
    public Image generate() {
        log.debug("Started generation with {} samples and {} iterations per sample", SAMPLES, iterationsPerSample);

        // подготавливаем
        Point p = Point.random();
        for (int i = 0; i < PREPARE_ITERATIONS; i++) {
            p = functions.getRandom().accept(p);
        }
        log.debug("After preparing iterations {}", p);

        for (int i = 0; i < SAMPLES; i++) {
            executor.submit(new ExecuteTask(p));
        }
        executor.close();
        log.debug("Completed generation");
        return image;
    }

    @AllArgsConstructor
    private final class ExecuteTask implements Runnable {

        private Point point;

        @Override
        public void run() {
            Function functionToApply;
            for (int i = 0; i < iterationsPerSample; i++) {
                functionToApply = functions.getRandom();
                point = functionToApply.accept(point);
                functionToApply.put(point, plot, image);
            }
        }
    }
}
