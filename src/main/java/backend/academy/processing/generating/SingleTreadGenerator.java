package backend.academy.processing.generating;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import backend.academy.output.cli.ProgressBar;
import backend.academy.processing.generating.functions.Function;
import backend.academy.processing.generating.functions.Functions;
import java.io.PrintStream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SingleTreadGenerator extends Generator {

    protected SingleTreadGenerator(Functions functions, Image image, Plot plot, int iterations, PrintStream out) {
        super(functions, image, plot, iterations, out);
    }

    public Image generate() {
        log.debug("Setup generating parameters: iterations: {},  {}, image:{}x{}", iterations, plot, image.height(),
            image.width());
        Function functionToApply;
        Point point = generateFirst();
        ProgressBar bar = ProgressBar.createDefault(iterations, out);
        out.println("Generating out image ...");
        for (int i = 0; i < iterations; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.accept(point);
            functionToApply.put(point, plot, image);
            bar.update(i);
        }
        return image;
    }

    private Point generateFirst() {
        log.debug("Generating first points");
        Function functionToApply;
        Point point = plot.getRandomPoint();
        ProgressBar bar = ProgressBar.createWithDefaultLength(PREPARE_ITERATIONS, out, 1);
        log.debug("Starting from {}", point);

        out.println("Preparing ...");
        for (int i = 0; i < PREPARE_ITERATIONS; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.accept(point);
            bar.update(i);
        }
        log.debug("After 20 iterations: {}", point);
        return point;
    }
}
