package backend.academy.utils;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtils {
    private static final Random RANDOM = ThreadLocalRandom.current();
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    public Color getRandomRGB() {
        return new Color(
            RANDOM.nextInt(MIN_VALUE, MAX_VALUE),
            RANDOM.nextInt(MIN_VALUE, MAX_VALUE),
            RANDOM.nextInt(MIN_VALUE, MAX_VALUE)
        );
    }
}
