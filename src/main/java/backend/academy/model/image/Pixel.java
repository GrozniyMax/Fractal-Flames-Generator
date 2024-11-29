package backend.academy.model.image;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.common.value.qual.IntRange;
import java.awt.Color;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Log4j2
public class Pixel {

    // честный, чтобы хоть как то попытаться обеспечить правильный порядок
    private final ReadWriteLock mainLock = new ReentrantReadWriteLock(true);

    private int r;
    private int g;
    private int b;
    private int alpha = 0;

    @Getter
    private int hitCount = 0;

    public Pixel(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        alpha = color.getAlpha();
    }

    public Color asRGB() {
        return new Color(bound(r), bound(g), bound(b));
    }

    public Color asARGB() {
        return new Color(bound(r), bound(g), bound(b), bound(alpha));
    }

    public void hit(Color color) {
        mainLock.writeLock().lock();
        mainLock.readLock().lock();
        try {
            hitCount++;
            if (hitCount == 1) {
                setColor(color);
                return;
            }
            mixColor(color);
        } finally {
            mainLock.writeLock().unlock();
            mainLock.readLock().unlock();
        }

    }

    private void mixColor(Color color) {
        this.r = (this.r + color.getRed()) / 2;
        this.g = (this.g + color.getGreen()) / 2;
        this.b = (this.b + color.getBlue()) / 2;
    }

    public void setColor(Color color) {

        this.r = color.getRed();
        this.b = color.getBlue();
        this.g = color.getGreen();
        this.alpha = color.getAlpha();
    }

    public void multiplyColor(double value) {
        this.r = (int) (r * value);
        this.g = (int) (g * value);
        this.b = (int) (b * value);
    }

    public void multiplyAlpha(double value) {

        alpha = (int) (alpha * value);

    }

    /**
     * Переводит число из диапазона 0-1 в диапазон 0-255 и ставит его вместо
     */
    public void setAlpha(double value) {
        this.alpha = (int) (value * 255);
    }

    public void multiplyAll(double value) {
        mainLock.writeLock().lock();
        mainLock.readLock().lock();
        try {
            multiplyAlpha(value);
            multiplyColor(value);
        } finally {
            mainLock.writeLock().unlock();
            mainLock.readLock().unlock();
        }
    }

    public static Pixel empty() {
        return new Pixel(Color.BLACK);
    }

    private static int bound(int value) {
        return Math.min(Math.max(0, value), 255);

    }

}
