package backend.academy.processing.generating;

import backend.academy.processing.generating.functions.Function;
import backend.academy.processing.generating.functions.Functions;
import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Поскольку в рамках нашего алгоритма, влияние случайности достаточно большое, то внесем еще большее ее влияние.<br>
 * Данная реализация не гарантирует очередности генерации точек, так и очереди добавления в график
 */
@Log4j2
public class YetAnotherThreadPoolGenerator extends Generator {

    private final int SAMPLES = 100;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final int ITERATIONS_PER_SAMPLE = this.iterations/SAMPLES + 1;

    protected YetAnotherThreadPoolGenerator(
        Functions functions,
        Image image,
        Plot plot,
        int iterations,
        PrintStream out
    ) {
        super(functions, image, plot, iterations, out);
    }

    @AllArgsConstructor
    private class ExecuteTask implements Runnable{

        private Point point;

        @Override
        public void run() {
//            log.debug("{} started generating", Thread.currentThread().getName());
            Function functionToApply;
            for (int i= 0; i<ITERATIONS_PER_SAMPLE; i++) {
                functionToApply = functions.getRandom();
                point = functionToApply.accept(point);
                functionToApply.put(point, plot, image);
            }
//            log.debug("{} finished generating", Thread.currentThread().getName());

        }
    }
    @Override
    public Image generate() {
        log.debug("Started generation with {} samples and {} iterations per sample", SAMPLES, ITERATIONS_PER_SAMPLE);

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
}
