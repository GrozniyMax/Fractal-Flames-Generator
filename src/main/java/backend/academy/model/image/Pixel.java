package backend.academy.model.image;

import backend.academy.Main;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;

@Log4j2
public class Pixel {

    // Поскольку пикселей будет много, то имеет смысл такого рода оптимизация.
    // Это усложнит работу с пикселями, но уменьшит потребление памяти на 3 байта на пиксель
    private byte r;
    private byte g;
    private byte b;

    @Getter
    private int hitCount = 0;

    public Pixel(byte r, byte g, byte b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color getColor() {
        return new Color(
            convertByteToIntColorPart(r),
            convertByteToIntColorPart(g),
            convertByteToIntColorPart(b)
        );
    }

    public void hit(Color color) {
        hitCount++;
        if (hitCount == 0) {
            setColor(color);
            return;
        }
        mixColor(color);
    }

    private void mixColor(Color color) {
        r = (byte) ((r + convertColorToByte(color.getRed())) / 2);
        g = (byte) ((g + convertColorToByte(color.getGreen())) / 2);
        b = (byte) ((b + convertColorToByte(color.getGreen())) / 2);
    }

    public void setColor(Color color) {
        r = convertColorToByte(color.getRed());
        g = convertColorToByte(color.getGreen());
        b = convertColorToByte(color.getGreen());
    }

    private int convertByteToIntColorPart(byte value) {
        return value+128;
    }

    private byte convertColorToByte(int color) {
        //todo protect from magic numbers
        return (byte) Math.min(color - 128, 128);
    }

    public void hit() {
        hitCount++;
    }

    public static Pixel empty() {
        return new Pixel(Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE);
    }

}
