package backend.academy;

import backend.academy.correction.logarithmicGammaCorrection.realisations.LogarithmicGammaCorrector;
import backend.academy.generating.functions.Function;
import backend.academy.generating.functions.Functions;
import backend.academy.generating.Generator;
import backend.academy.model.image.Image;
import backend.academy.output.image.SingleTreadImageWriter;
import backend.academy.output.cli.CommandLineSettings;
import backend.academy.processing.PipelineBuilder;
import backend.academy.settings.Settings;
import backend.academy.transformations.AffineTransformation;
import backend.academy.transformations.SimpleFunction;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Objects;

@Log4j2
@UtilityClass
public class Main {

    public static int count;

    public static void main(String[] args) throws IOException {

        PipelineBuilder pipelineBuilder = new PipelineBuilder();
        CommandLineSettings cliSettings = new CommandLineSettings();

        JCommander jCommander = JCommander.newBuilder()
            .addObject(cliSettings)
            .build();

        try {
            jCommander.parse(args);
        } catch (ParameterException | IllegalArgumentException e) {
            jCommander.usage();
            return;
        }
        if (cliSettings.help()) {
            jCommander.usage();
            return;
        }

        Configurator.setRootLevel(org.apache.logging.log4j.Level.OFF);
        pipelineBuilder.fill(cliSettings);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            pipelineBuilder.fill(
                objectMapper.readValue(
                    createReader(cliSettings.jsonPath()),
                    Settings.class
                )
            );
        } catch (IllegalArgumentException | JacksonException e) {
            System.out.println("Invalid json structure");
            return;
        }

        pipelineBuilder.output(System.out);

        try {
            pipelineBuilder
                .build()
                .run();
        } catch (Exception e) {
            System.out.println("Unexpected error while generating image. Sorry but image was not generates");
            throw new RuntimeException(e);
        }
        System.out.printf("Skipped %d points", count);

    }

    private InputStream createReader(Path path) throws FileNotFoundException {
        if (Objects.isNull(path)) {
            return Main.class.getResourceAsStream("/default.json");
        } else {
            return new FileInputStream(path.toFile());
        }
    }


}
