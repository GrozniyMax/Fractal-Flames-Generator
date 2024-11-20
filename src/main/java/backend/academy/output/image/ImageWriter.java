package backend.academy.output.image;

import backend.academy.model.image.Image;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageWriter {

    void writeToFile(Image imageToWrite, Path fileToWrite) throws IOException;

    default String getFileFormat(Path fileToWrite) {
        String fileName = fileToWrite.getFileName().toString();

        // Проверяем, есть ли точка в имени файла
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            // Возвращаем часть строки после последней точки
            return fileName.substring(dotIndex + 1);
        }
        // Если точки нет или она в начале имени файла
        return "";
    }

}
