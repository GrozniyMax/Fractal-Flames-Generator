package backend.academy.processing;

import backend.academy.correction.Corrector;
import backend.academy.generating.Generator;
import backend.academy.generating.functions.Function;
import backend.academy.generating.functions.Functions;
import backend.academy.output.cli.CommandLineSettings;
import backend.academy.output.cli.ProgressBar;
import backend.academy.output.image.ImageWriter;
import backend.academy.output.image.MultiTreadImageWriter;
import backend.academy.output.image.SingleTreadImageWriter;
import backend.academy.settings.PipelineObject;
import backend.academy.settings.Settings;
import backend.academy.settings.symmetry.NoneSymmetry;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;
import java.nio.file.Path;

@Log4j2
@Getter
public class PipelineBuilder {

    private Generator.GeneratorBuilder generatorBuilder = Generator.builder();

    private Corrector corrector;

    private ImageWriter writer;

    private ProgressBar.ProgressBarBuilder progressBarBuilder = ProgressBar.builder();

    private Path outputFile;

    public PipelineBuilder fill(PipelineObject pipelineObject) {
        this.generatorBuilder
            .plotX(pipelineObject.plot().x())
            .plotY(pipelineObject.plot().y())
            .plotWidth(pipelineObject.plot().width())
            .plotHeight(pipelineObject.plot().height());

        switch (pipelineObject.mode()) {
            case MULTI_THREAD -> writer = new MultiTreadImageWriter();
            case SINGLE_THREAD -> writer = new SingleTreadImageWriter();
        }

        corrector = pipelineObject.correctionObject().getRealType();

        return this;
    }

    public PipelineBuilder fill(Settings settings) {
        Functions functions = new Functions();
        settings.functions().stream()
            .map(functionObject -> new Function(functionObject.getRealType(), functionObject.color()))
            .forEach(functions::add);
        if (!(settings.pipeline().symmetry() instanceof NoneSymmetry)) {
            functions.addSymmetry(settings.pipeline().symmetry().getRealType());
        }
        this.generatorBuilder.functions(functions);
        fill(settings.pipeline());
        return this;
    }

    public PipelineBuilder fill(CommandLineSettings settings) {
        this.generatorBuilder
            .imageHeight(settings.imageSize().left())
            .imageWidth(settings.imageSize().right())
            .iterations(settings.iterations());

        progressBarBuilder.maxIterations(settings.iterations());

        this.outputFile = settings.output();

        return this;
    }

    public PipelineBuilder output(PrintStream output) {
        this.progressBarBuilder.out(output);
        return this;
    }

    public Pipeline build() {
        this.generatorBuilder.progressBar(this.progressBarBuilder.build());
        return new Pipeline(
            generatorBuilder.build(),
            this.corrector,
            this.writer,
            this.outputFile
        );
    }

}
