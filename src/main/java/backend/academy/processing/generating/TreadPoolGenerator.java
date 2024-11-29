package backend.academy.processing.generating;

import backend.academy.processing.generating.functions.Function;
import backend.academy.processing.generating.functions.Functions;
import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Не совсем точная реализация.
 * Правильный порядок генерации точек гарантируется.
 * Однако не обеспечивается порядок переноса точек на график
 */
@Log4j2
public class TreadPoolGenerator extends Generator {
    // -1 так, чтобы мало ли не сломалось
    public static final int MAX_SUBMITTED_TASK = Integer.MAX_VALUE - 1;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private AtomicInteger tasksInProgress = new AtomicInteger(0);

    @RequiredArgsConstructor
    private class ExecutorTask implements Runnable {

        private final Point point;

        private final Function appliedFunction;

        @Override
        public void run() {
            appliedFunction.put(point, plot, image);
            tasksInProgress.decrementAndGet();
        }
    }

    @Builder
    public TreadPoolGenerator(Functions functions, Image image, Plot plot, int iterations, PrintStream out) {
        super(functions, image, plot, iterations, out);
    }

    @Override
    public Image generate() {
        log.debug("Setup generating parameters: iterations: {},  {}, image:{}x{}", iterations, plot, image.height(),
            image.width());
        Function functionToApply;
        Point point = plot.getRandomPoint();
        log.debug("Starting point: {}", point);

        for (int i = 0; i < 20; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.accept(point);
        }
        log.debug("After 20 iterations: {}", point);

        int i = 0;
        while (i < iterations) {
            // на тот случай если у нас будет Integer.MAX_VALUE-1 итераций
            if (tasksInProgress.get() <= MAX_SUBMITTED_TASK) {
                functionToApply = functions.getRandom();
                point = functionToApply.accept(point);
                executor.submit(new ExecutorTask(point, functionToApply));
                tasksInProgress.incrementAndGet();
                i++;
            }else {
                log.debug("Waiting");
            }
        }
        log.debug("Points were generated");
        executor.close();
        log.debug("All points were added to image");
        return image;
    }
}
