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

    @RequiredArgsConstructor
    public enum ImageMode {
        RGB(BufferedImage.TYPE_INT_ARGB, Pixel::asRGB),
        ARGB(BufferedImage.TYPE_INT_ARGB, Pixel::asARGB);

        @Getter
        private final int magicConstant;

        private final Function<Pixel, Color> mapper;

        public Color getColor(Pixel pixel) {
            return this.mapper.apply(pixel);
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

}
