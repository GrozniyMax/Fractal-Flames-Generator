package backend.academy.output.image;

import backend.academy.model.image.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Log4j2
public class MultiTreadImageWriter implements ImageWriter {

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * Задача реализующая копирование строки изображения.
     * Данный подход позволяет избежать лишних синхронизаций, так как каждый поток будет работать со своей строкой
     */
    @RequiredArgsConstructor
    private static class CopyTask implements Runnable {

        private final BufferedImage copyTo;
        private final Image copyFrom;
        private final int rowIndex;

        @Override
        public void run() {
            for (int i = 0; i < copyFrom.width(); i++) {
                copyTo.setRGB(i, rowIndex, copyFrom.get(i, rowIndex).getColor().getRGB());
            }
        }
    }

    @Override
    public void writeToFile(Image imageToWrite, Path fileToWrite) throws IOException {
        BufferedImage image = new BufferedImage(imageToWrite.width(),
            imageToWrite.height(),
            BufferedImage.TYPE_INT_RGB);
        Queue<Future<?>> futures = new LinkedList<>();
        for (int i = 0; i < imageToWrite.height(); i++) {
            futures.add(
                executor.submit(new CopyTask(image, imageToWrite, i))
            );
        }

        // По сути время копирования изображения обуславливается временем копирования всех строк
        // Поэтому ждем самую долгую
        while (!futures.isEmpty()) {
            try {
                futures.poll().get();
            } catch (InterruptedException e) {
                log.error("Unable to copy due to interruption");
            } catch (ExecutionException e) {
                log.error("Exception in image copying {}:{}",
                    e.getCause().getClass(),
                    e.getCause().getMessage());
            }
        }

        ImageIO.write(image, getFileFormat(fileToWrite), fileToWrite.toFile());
    }
}
