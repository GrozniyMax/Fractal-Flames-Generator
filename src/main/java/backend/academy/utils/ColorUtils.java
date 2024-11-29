package backend.academy.utils;

import lombok.experimental.UtilityClass;
import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class ColorUtils {
    private static final Random RANDOM = ThreadLocalRandom.current();


    public Color getRandomRGB() {
        return new Color(
            RANDOM.nextInt(0, 255),
            RANDOM.nextInt(0, 255),
            RANDOM.nextInt(0, 255)
            );
    }
}
