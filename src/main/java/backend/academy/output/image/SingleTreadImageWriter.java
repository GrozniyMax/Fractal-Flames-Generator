package backend.academy.output.image;

import backend.academy.model.image.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SingleTreadImageWriter implements ImageWriter {

    @Override
    public void writeToFile(Image imageToWrite, Path fileToWrite, ImageMode mode) throws IOException {
        BufferedImage image = new BufferedImage(imageToWrite.width(), imageToWrite.height(), mode.magicConstant());

        for (int i = 0; i < imageToWrite.height(); i++) {
            for (int j = 0; j < imageToWrite.width(); j++) {
                image.setRGB(j, i, mode.getColorInt(imageToWrite.get(j, i)));
            }
        }

        image = addBackground(image);

        ImageIO.write(image, getFileFormat(fileToWrite), fileToWrite.toFile());
    }

}
