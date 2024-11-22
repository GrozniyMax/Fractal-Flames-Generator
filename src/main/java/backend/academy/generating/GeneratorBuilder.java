package backend.academy.generating;

import backend.academy.generating.functions.Functions;
import backend.academy.model.image.Image;
import backend.academy.model.math.variations.Variations;
import backend.academy.model.plot.Plot;
import backend.academy.output.cli.ProgressBar;
import backend.academy.input.configuration.Modes;
import lombok.Getter;
import java.io.PrintStream;

@Getter
public class GeneratorBuilder {

    private Integer imageWidth;
    private Integer imageHeight;

    private Double plotX;
    private Double plotY;

    private Double plotWidth;
    private Double plotHeight;

    private Functions functions;
    private Variations variations;

    private int iterations;
    private PrintStream out;

    private Modes mode;

    public GeneratorBuilder imageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public GeneratorBuilder imageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
        return this;
    }

    public GeneratorBuilder plotX(Double plotX) {
        this.plotX = plotX;
        return this;
    }

    public GeneratorBuilder plotY(Double plotY) {
        this.plotY = plotY;
        return this;
    }

    public GeneratorBuilder plotWidth(Double plotWidth) {
        this.plotWidth = plotWidth;
        return this;
    }

    public GeneratorBuilder plotHeight(Double plotHeight) {
        this.plotHeight = plotHeight;
        return this;
    }

    public GeneratorBuilder functions(Functions functions) {
        this.functions = functions;
        return this;
    }

    public GeneratorBuilder variations(Variations variations) {
        this.variations = variations;
        return this;
    }

    public GeneratorBuilder iterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public GeneratorBuilder out(PrintStream stream) {
        this.out = stream;
        return this;
    }

    public GeneratorBuilder mode(Modes mode) {
        this.mode = mode;
        return this;
    }

    public Generator build() {
        Image image = new Image(imageWidth, imageHeight);
        Plot plot = new Plot(plotX, plotY, plotWidth, plotHeight);
        switch (mode) {
            case SINGLE_THREAD -> {
                return new SingleTreadGenerator(functions, image, plot, iterations, out);
            }
            case MULTI_THREAD -> {
                return new TreadPoolGenerator(functions, image, plot, iterations, out);
            }
            case null, default -> {
                return new SingleTreadGenerator(functions, image, plot, iterations, out);
            }
        }
    }
}