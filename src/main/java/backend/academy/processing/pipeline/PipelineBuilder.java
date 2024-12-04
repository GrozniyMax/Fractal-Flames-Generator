package backend.academy.processing.pipeline;

import backend.academy.input.cli.CommandLineSettings;
import backend.academy.input.configuration.Modes;
import backend.academy.input.configuration.PipelineObject;
import backend.academy.input.configuration.Settings;
import backend.academy.input.configuration.ThreadCounts;
import backend.academy.model.math.variations.Variations;
import backend.academy.output.image.ImageWriter;
import backend.academy.output.image.MultiTreadImageWriter;
import backend.academy.output.image.SingleTreadImageWriter;
import backend.academy.processing.correction.Corrector;
import backend.academy.processing.generating.GeneratorBuilder;
import backend.academy.processing.generating.functions.Functions;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class PipelineBuilder {

    private GeneratorBuilder generatorBuilder = new GeneratorBuilder();

    private List<Corrector> correctors = new LinkedList<>();

    private ImageWriter writer;

    private PrintStream out;

    private Path outputFile;

    private Modes mode;

    private ImageWriter.ImageMode imageMode;

    private ThreadCounts threadCounts;

    private void buildWriter() {
        switch (mode) {
            case SINGLE_THREAD, OPTIMAL -> writer = new SingleTreadImageWriter();
            case MULTI_THREAD -> writer = new MultiTreadImageWriter(threadCounts.imageWriter());
            case null, default -> throw new IllegalArgumentException("Unknown application mode");
        }
    }

    private void fillCorrectors(PipelineObject pipelineObject) {
        this.correctors = pipelineObject.corrections().stream()
            .map((correction) -> correction.getRealType(mode))
            .toList();
    }

    private Functions fillFunctions(Settings settings) {
        Functions functions = new Functions();
        settings.functions()
            .forEach(functionObject -> functions.add(functionObject.toFunction()));

        if (Objects.nonNull(settings.activeVariations())) {
            functions.setVariationsForAll(
                Variations.get(settings.activeVariations())
            );
        }

        settings.pipeline().postTransformations()
            .forEach(functions::andThenForAll);

        if (Objects.nonNull(settings.pipeline().symmetry())) {
            functions.addSymmetry(settings.pipeline().symmetry());
        }
        return functions;
    }

    private void fill(ThreadCounts counts) {
        this.generatorBuilder.threadCounts(counts.generator());
        this.threadCounts = counts;
    }

    public PipelineBuilder fill(PipelineObject rawPipelineObject) {
        this.mode = rawPipelineObject.mode();
        this.imageMode = rawPipelineObject.imageMode();

        this.generatorBuilder.mode(mode)
            .plotX(rawPipelineObject.plot().x())
            .plotY(rawPipelineObject.plot().y())
            .plotWidth(rawPipelineObject.plot().width())
            .plotHeight(rawPipelineObject.plot().height());

        fillCorrectors(rawPipelineObject);

        return this;
    }

    public PipelineBuilder fill(Settings settings) {
        fill(settings.threadCounts());
        this.generatorBuilder.functions(fillFunctions(settings));
        return fill(settings.pipeline());
    }

    public PipelineBuilder fill(CommandLineSettings settings) {
        this.generatorBuilder
            .imageHeight(settings.imageSize().left())
            .imageWidth(settings.imageSize().right())
            .iterations(settings.iterations());

        this.outputFile = settings.output();

        return this;
    }

    public PipelineBuilder output(PrintStream output) {
        this.generatorBuilder().out(output);
        this.out = output;
        return this;
    }

    public AbstractPipeline build() {
        buildWriter();
        validate();
        switch (mode) {
            case Modes.MULTI_THREAD -> {
                return new AsyncPipeline(
                    generatorBuilder.build(),
                    this.correctors,
                    this.writer,
                    this.outputFile,
                    this.out,
                    this.imageMode,
                    this.threadCounts.pipeline()
                );
            }
            case Modes.SINGLE_THREAD, OPTIMAL -> {
                return new Pipeline(
                    generatorBuilder.build(),
                    this.correctors,
                    this.writer,
                    this.outputFile,
                    this.out,
                    this.imageMode
                );
            }
            default -> throw new IllegalArgumentException("Unknown/non stated mode");
        }
    }

    private void validate() {
        validateThreadCounts();
        if (Objects.isNull(imageMode)) {
            throw new IllegalArgumentException("Image mode is not set");
        }
        if (Objects.isNull(outputFile)) {
            throw new IllegalArgumentException("Output file is not set");
        }
        if (Objects.isNull(out)) {
            throw new IllegalArgumentException("Problems with output stream");
        }
    }

    private void validateThreadCounts() {
        switch (mode) {
            case null -> throw new IllegalArgumentException("Mode is not set");
            case MULTI_THREAD -> {
                if (Objects.isNull(threadCounts)) {
                    throw new IllegalArgumentException("Thread counts are not set. Check your configuration file");
                }
                if (threadCounts.generator() < 1) {
                    throw new IllegalArgumentException("Generator thread count is less than 1. Check config");
                }
                if (threadCounts.pipeline() < 1) {
                    throw new IllegalArgumentException("Pipeline thread count is less than 1. Check config");
                }
                if (threadCounts.imageWriter() < 1) {
                    throw new IllegalArgumentException("Image writer thread count is less than 1. Check config");
                }
            }
            case OPTIMAL -> {
                if (Objects.isNull(threadCounts)) {
                    throw new IllegalArgumentException("Thread counts are not set");
                }
                if (threadCounts.generator() < 1) {
                    throw new IllegalArgumentException("Generator thread count is less than 1");
                }
                if (threadCounts.pipeline() < 1) {
                    throw new IllegalArgumentException("Pipeline thread count is less than 1");
                }
                if (threadCounts.imageWriter() < 1) {
                    throw new IllegalArgumentException("Image writer thread count is less than 1");
                }
            }
            case SINGLE_THREAD -> {
                // do nothing;
            }
        }
    }

}
