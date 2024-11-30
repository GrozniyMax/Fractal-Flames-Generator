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
            return new Color(node.get("r").asInt(), node.get("g").asInt(), node.get("b").asInt());
        } else if (node.isArray()) {
            return new Color(node.get(0).asInt(), node.get(1).asInt(), node.get(2).asInt());
        } else {
            throw new IllegalArgumentException("Invalid color format");
        }
    }
}
