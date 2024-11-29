package backend.academy.output.image;

import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

public interface ImageWriter {

    public static final Color backgroundColor = Color.BLACK;

    @RequiredArgsConstructor
    public enum ImageMode {
        RGB(BufferedImage.TYPE_INT_RGB, Pixel::asRGB),
        ARGB(BufferedImage.TYPE_INT_ARGB, Pixel::asARGB);

        @Getter
        private final int magicConstant;

        private final Function<Pixel, Color> mapper;

        public Color getColor(Pixel pixel) {
            return this.mapper.apply(pixel);
        }

        public int getColorInt(Pixel pixel) {
            return getColor(pixel).getRGB();
        }
    }

    void writeToFile(Image imageToWrite, Path fileToWrite, ImageMode mode) throws IOException;

    default String getFileFormat(Path fileToWrite) {
        String fileName = fileToWrite.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    default BufferedImage addBackground(BufferedImage src) {
        BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        result.getGraphics().clearRect(0, 0, result.getWidth(), result.getHeight());
        result.getGraphics().drawImage(src, 0, 0, null);
        return result;
    }

}
