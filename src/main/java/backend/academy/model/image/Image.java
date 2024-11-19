package backend.academy.model.image;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;

@Log4j2
@ToString(exclude = "data")
public class Image {

    private final Pixel[][] data;

    @Getter
    private final int width;

    @Getter
    private final int height;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        data = new Pixel[height][width];
        fillWithEmpty();
    }

    private void fillWithEmpty() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                data[i][j] = Pixel.empty();
            }
        }
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void put(int x, int y, Color color) {
        if (!contains(x, y)) {
            return;
        }
        data[y][x].hit(color);
    }

    public Pixel get(int x, int y) {
        return data[y][x];
    }
}
