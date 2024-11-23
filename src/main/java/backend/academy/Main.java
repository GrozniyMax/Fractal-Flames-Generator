package backend.academy;

import backend.academy.input.cli.CommandLineSettings;
import backend.academy.processing.PipelineBuilder;
import backend.academy.input.configuration.Settings;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
            System.out.println("Invalid json structure due to: " + e.getMessage());
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
        log.debug("{} points were skipped because they're out of image size", count);

    }

    private InputStream createReader(Path path) throws FileNotFoundException {
        if (Objects.isNull(path)) {
            return Main.class.getResourceAsStream("/default.json");
        } else {
            return new FileInputStream(path.toFile());
        }
    }


}
