package backend.academy.input.configuration.deserializers;

import backend.academy.utils.ColorUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Десериализатор для афaинных преобразований {@link Color}. Сделан, чтобы учитывать параметр рандом
 */
public class ColorDeserializer extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual() && node.asText().strip().equalsIgnoreCase("random")) {
            Random r = ThreadLocalRandom.current();
            return ColorUtils.getRandomRGB();
        } else if (node.isObject()) {
            int r = node.get("r").asInt();
            int g = node.get("g").asInt();
            int b = node.get("b").asInt();
            checkBounds(r);
            checkBounds(g);
            checkBounds(b);
            return new Color(r, g, b);
        } else if (node.isArray()) {
            int r = node.get(0).asInt();
            int g = node.get(1).asInt();
            int b = node.get(2).asInt();
            checkBounds(r);
            checkBounds(g);
            checkBounds(b);
            return new Color(r, g, b);
        } else {
            throw new IllegalArgumentException("Invalid color format");
        }
    }

    private void checkBounds(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Invalid color part: It must be from 0 to 255");
        }
    }
}
