package backend.academy.output.image;

import backend.academy.model.image.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;

@Log4j2 public class MultiTreadImageWriter implements ImageWriter {

    private ExecutorService executor;

    public MultiTreadImageWriter(int treadCount) {
        executor = Executors.newFixedThreadPool(treadCount);
        log.debug("Thread-count: {}", treadCount);
    }

    @Override
    public void writeToFile(Image imageToWrite, Path fileToWrite, ImageMode mode) throws IOException {
        BufferedImage image = new BufferedImage(imageToWrite.width(), imageToWrite.height(), mode.magicConstant());
        for (int i = 0; i < imageToWrite.height(); i++) {
            executor.submit(new CopyTask(image, imageToWrite, i, mode));
        }
        executor.close();
        ImageIO.write(addBackground(image), getFileFormat(fileToWrite), fileToWrite.toFile());
    }

    /**
     * Задача реализующая копирование строки изображения.
     * Данный подход позволяет избежать лишних синхронизаций, так как каждый поток будет работать со своей строкой
     */
    private final static class CopyTask implements Runnable {

        private final BufferedImage copyTo;
        private final Image copyFrom;
        private final int rowIndex;
        private final ImageMode mode;

        private CopyTask(BufferedImage copyTo, Image copyFrom, int rowIndex, ImageMode mode) {
            this.copyTo = copyTo;
            this.copyFrom = copyFrom;
            this.rowIndex = rowIndex;
            this.mode = mode;
        }

        @Override
        public void run() {
            for (int i = 0; i < copyFrom.width(); i++) {
                copyTo.setRGB(i, rowIndex, mode.getColorInt(copyFrom.get(i, rowIndex)));
            }
        }
    }
}
