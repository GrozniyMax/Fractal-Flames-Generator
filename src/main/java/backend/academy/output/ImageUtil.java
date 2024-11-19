package backend.academy.output;

import backend.academy.model.image.Image;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Log4j2
@UtilityClass
public class ImageUtil {

    public void write(Image imageToWrite) throws IOException {
        BufferedImage image = new BufferedImage(imageToWrite.width(), imageToWrite.height(), BufferedImage.TYPE_INT_RGB);


        for (int i = 0; i < imageToWrite.height(); i++) {
            for (int j = 0; j < imageToWrite.width(); j++) {
                image.setRGB(j, i, imageToWrite.get(j, i).getColor().getRGB());
            }
        }

        ImageIO.write(image, "png", new java.io.File("output.png"));
    }
}
