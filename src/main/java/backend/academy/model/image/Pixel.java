package backend.academy.model.image;

import backend.academy.Main;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;
import java.awt.PageAttributes;
import java.security.SecureRandom;

@Log4j2
public class Pixel {

    // Поскольку пикселей будет много, то имеет смысл такого рода оптимизация.
    // Это усложнит работу с пикселями
    // но уменьшит потребление памяти
    // на 3 байта на пиксель в сравнении с short
    // на 9 байт на пиксель с сравнении с int
    private int color;

    @Getter
    private int hitCount = 0;

    public Pixel(Color color) {
        this.color = color.getRGB();
    }

    public Color getColor() {
        return new Color(color);
    }

    public void hit(Color color) {
        hitCount++;
        if (hitCount == 1) {
            setColor(color);
            return;
        }
        mixColor(color);
    }

    private void mixColor(Color color) {
        this.color = (this.color + color.getRGB()) /2;
    }

    public void setColor(Color color) {
        this.color = color.getRGB();
    }

    public static Pixel empty() {
        return new Pixel(Color.BLACK);
    }

    public void multiply(double value) {
        this.color = (int) (color*value);
    }

}
