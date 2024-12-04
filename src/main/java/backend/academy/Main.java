package backend.academy;

import backend.academy.input.cli.CommandLineSettings;
import backend.academy.input.configuration.Settings;
import backend.academy.processing.pipeline.PipelineBuilder;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;

@Log4j2
@UtilityClass
public class Main {


    @SuppressWarnings("checkstyle:ReturnCount")
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

        if (!cliSettings.debug()) {
            Configurator.setRootLevel(org.apache.logging.log4j.Level.OFF);
        }

        PrintStream out;
        if (cliSettings.suppress()) {
            out = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    // do nothing;
                }
            });
            pipelineBuilder.output(out);
        } else {
            pipelineBuilder.output(System.out);
            out = System.out;
        }

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
            out.println("Invalid json structure due to: " + e.getMessage());
            log.debug(e);
            return;
        }

        if (cliSettings.suppress()) {
            pipelineBuilder.output(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    // do nothing;
                }
            }));
        } else {
            pipelineBuilder.output(System.out);
        }

        long start = System.currentTimeMillis();
        try {
            pipelineBuilder
                .build()
                .run();
        } catch (IllegalArgumentException e) {
            out.println("Invalid settings due to: " + e.getMessage());
            log.debug(e);
            return;
        } catch (Exception e) {
            out.println("Unexpected error while generating image. Sorry but image was not generates");
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        log.info("Execution time: {}", end - start);

    }

    private InputStream createReader(Path path) throws FileNotFoundException {
        if (Objects.isNull(path)) {
            log.debug("Config file path is not set. Using default settings");
            return Main.class.getResourceAsStream("/default.json");
        } else {
            log.debug("Config file path: {}", path);
            return new FileInputStream(path.toFile());
        }
    }

}
