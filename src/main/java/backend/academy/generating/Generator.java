package backend.academy.generating;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import backend.academy.output.cli.ProgressBar;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.Objects;

@Log4j2
public class Generator {

    private final Functions functions;
    private final Image image;
    private final Plot plot;
    private int iterations = 10000;

    @Builder
    public Generator(Integer imageWidth, Integer imageHeight,
        Double plotX, Double plotY,
        Double plotWidth, Double plotHeight,
        Functions functions, int iterations) {
        this.functions = Objects.requireNonNull(functions);

        this.image = new Image(
            Objects.requireNonNull(imageWidth),
            Objects.requireNonNull(imageHeight));

        this.plot = new Plot(
            Objects.requireNonNull(plotX),
            Objects.requireNonNull(plotY),
            Objects.requireNonNull(plotWidth),
            Objects.requireNonNull(plotHeight));
        this.iterations = iterations;
    }


    public Image generate() {
        Function functionToApply;
        Point point = plot.getRandomPoint();
        log.info("Starting point: {}", point);
        for (int i = 0; i < 20; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.accept(point);
        }
        log.info("After 20 iterations: {}", point);

        ProgressBar progressBar = new ProgressBar(iterations);
        for (int i = 0; i < iterations; i++) {
            functionToApply = functions.getRandom();
            point = functionToApply.acceptAndPutToImage(point, plot, image);
        }
        return image;
    }
}
