package backend.academy;

import backend.academy.correction.LogarithmicGammaCorrector;
import backend.academy.generating.Function;
import backend.academy.generating.Functions;
import backend.academy.generating.Generator;
import backend.academy.model.image.Image;
import backend.academy.output.ImageUtil;
import backend.academy.transformations.AffineTransformation;
import backend.academy.transformations.SimpleFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import java.awt.Color;
import java.io.IOException;

@UtilityClass
public class Main {

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMappe

        Functions f = new Functions()
            .add(new Function(new SimpleFunction(AffineTransformation.random()), Color.CYAN))
            .add(new Function(new SimpleFunction(AffineTransformation.random()), Color.MAGENTA))
            .add(new Function(new SimpleFunction(AffineTransformation.random()), Color.YELLOW))
            .add(new Function(new SimpleFunction(AffineTransformation.random()), Color.GREEN))
            .add(new Function(new SimpleFunction(AffineTransformation.random()), Color.RED));

        Generator g = Generator.builder()
            .plotX(-1.777)
            .plotY(-1.0)
            .plotWidth(1.777*2)
            .plotHeight(2.0)
            .imageWidth(800)
            .imageHeight(800)
            .iterations(1_000_000)
            .functions(f)
            .build();


        Image i = g.generate();
        new LogarithmicGammaCorrector(0.7).accept(i);
        ImageUtil.write(i);


    }
}
