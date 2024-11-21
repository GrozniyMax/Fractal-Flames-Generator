package backend.academy.generating;

import backend.academy.generating.functions.Function;
import backend.academy.generating.functions.Functions;
import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.unimi.dsi.fastutil.Pair;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Не совсем точная реализация.
 * Правильный порядок генерации точек гарантируется.
 * Однако не обеспечивается порядок переноса точек на график
 */
@Log4j2
public class TreadPoolGenerator extends Generator{
    /**
     * 1 - Наблюдатель (добавляет таски по мере необходимости)
     * 10 - Исполнители (переносят точку из графика на изображение)
     */
    private ThreadFactory factory = new ThreadFactoryBuilder()
        .setDaemon(false)
        .setNameFormat("image-generating-%d")
        .build();

    private final ExecutorService executor = Executors.newCachedThreadPool(factory);

    private final Queue<Pair<Point, Function>> pointsToAdd = new ConcurrentLinkedQueue<>();

    private AtomicBoolean generationFinished = new AtomicBoolean(false);

    private AtomicInteger tasksInProgress = new AtomicInteger(0);


    private class ObserverTask implements Runnable {

        @Override
        public void run() {
            while (!generationFinished.get()){
                if (!pointsToAdd.isEmpty() && tasksInProgress.get()<100) {
                        executor.submit(new ExecutorTask(pointsToAdd.poll()));
                }
            }
        }
    }

    @RequiredArgsConstructor
    private class ExecutorTask implements Runnable {

        private final Pair<Point, Function> pair;

        @Override
        public void run() {
            pair.right().put(pair.left(), plot, image);
            tasksInProgress.decrementAndGet();
        }
    }

    @Builder
    public TreadPoolGenerator(Functions functions, Image image, Plot plot, int iterations , PrintStream out) {
        super(functions, image, plot, iterations, out);
    }

    @Override
    public Image generate() {
        log.info("Setup generating parameters: iterations: {},  {}, image:{}x{}", iterations, plot, image.height(), image.width());
        Function functionToApply;
        Point point = plot.getRandomPoint();
        log.info("Starting point: {}", point);
        for (int i = 0; i < 20; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.accept(point);
        }
        log.info("After 20 iterations: {}", point);

        executor.submit(new ObserverTask());
        for (int i = 0; i < iterations; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.accept(point);
            pointsToAdd.add(Pair.of(point, functionToApply));
        }
        generationFinished.set(true);

        return image;
    }
}
