package backend.academy.processing.generating;

import backend.academy.processing.generating.functions.Functions;
import backend.academy.model.image.Image;
import backend.academy.model.math.variations.Variations;
import backend.academy.model.plot.Plot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.io.PrintStream;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Generator {

    protected static final int PREPARE_ITERATIONS = 20;

    protected final Functions functions;
    protected final Image image;
    protected final Plot plot;
    protected int iterations = 10000;
    protected PrintStream out;

    protected Generator(
        @NonNull Integer imageWidth, @NonNull Integer imageHeight,
        @NonNull Double plotX, @NonNull Double plotY,
        @NonNull Double plotWidth, @NonNull Double plotHeight,
        @NonNull Functions functions, @Nullable Variations variations,
        int iterations, @NonNull PrintStream out
    ) {
        this(functions,
            new Image(imageWidth, imageHeight),
            new Plot(plotX, plotY, plotWidth, plotHeight),
            iterations, out);
    }


    public abstract Image generate();
}
