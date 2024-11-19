package backend.academy.settings.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual()) {
            return Color.decode(node.asText());
        } else if (node.isObject()) {
            return new Color(node.get("r").asInt(), node.get("g").asInt(), node.get("b").asInt());
        } else if (node.isArray()) {
            return new Color(node.get(0).asInt(), node.get(1).asInt(), node.get(2).asInt());
        }else {
            throw new IllegalArgumentException("Invalid color format");
        }
    }
}
