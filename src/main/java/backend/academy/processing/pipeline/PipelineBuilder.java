package backend.academy.processing.pipeline;

import backend.academy.input.configuration.ThreadCounts;
import backend.academy.output.image.MultiTreadImageWriter;
import backend.academy.processing.correction.Corrector;
import backend.academy.processing.generating.GeneratorBuilder;
import backend.academy.processing.generating.functions.Functions;
import backend.academy.input.configuration.Modes;
import backend.academy.model.math.variations.Variations;
import backend.academy.input.cli.CommandLineSettings;
import backend.academy.output.image.ImageWriter;
import backend.academy.output.image.SingleTreadImageWriter;
import backend.academy.input.configuration.PipelineObject;
import backend.academy.input.configuration.Settings;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public PipelineBuilder fill(PipelineObject rawPipelineObject) {
        var completedPipelineObject = rawPipelineObject.complete();
        this.imageMode = rawPipelineObject.imageMode();

        this.generatorBuilder
            .plotX(completedPipelineObject.plot().x())
            .plotY(completedPipelineObject.plot().y())
            .plotWidth(completedPipelineObject.plot().width())
            .plotHeight(completedPipelineObject.plot().height());

        switch (completedPipelineObject.mode()) {
            case MULTI_THREAD, OPTIMAL -> {
                writer = new MultiTreadImageWriter(threadCounts.imageWriter());
                this.generatorBuilder.mode(Modes.MULTI_THREAD);
            }
            case SINGLE_THREAD -> {
                this.generatorBuilder.mode(Modes.SINGLE_THREAD);
                writer = new SingleTreadImageWriter();
            }
        }

        this.correctors = completedPipelineObject.corrections();
//        this.generatorBuilder.mode(completedPipelineObject.mode());
        this.mode = completedPipelineObject.mode();

        return this;
    }

    public PipelineBuilder fill(Settings settings) {
        Functions functions = new Functions();
        settings.functions()
            .forEach(f -> functions.add(f.toFunction()));

        if (Objects.nonNull(settings.activeVariations())) {
            functions.setVariationsForAll(Variations.get(settings.activeVariations()));
        }
        this.generatorBuilder.functions(functions);
        this.threadCounts = settings.threadCounts();
        this.generatorBuilder.threadCounts(threadCounts.generator());
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
        }
        throw new IllegalArgumentException("Unknown/Not stated mode");
    }

//    public AbstractPipeline build() {
//        return new Pipeline(
//            generatorBuilder.build(),
//            this.correctors,
//            this.writer,
//            this.outputFile,
//            this.out,
//            this.imageMode
//        );
//    }

}
