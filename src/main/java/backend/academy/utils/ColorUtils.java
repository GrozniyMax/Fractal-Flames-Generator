package backend.academy.utils;

import lombok.experimental.UtilityClass;
import java.awt.Color;
import java.security.SecureRandom;

@UtilityClass
public class ColorUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    public Color getRandomRGB() {
        return new Color(
            RANDOM.nextInt(0, 255),
            RANDOM.nextInt(0, 255),
            RANDOM.nextInt(0, 255)
            );
    }
}
