package backend.academy.processing.generating;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.processing.generating.functions.Functions;
import java.io.PrintStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Generator {

    protected static final int PREPARE_ITERATIONS = 20;

    protected final Functions functions;
    protected final Image image;
    protected final Plot plot;
    protected int iterations;
    protected PrintStream out;

    public abstract Image generate();
}
