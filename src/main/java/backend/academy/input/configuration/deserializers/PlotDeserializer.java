package backend.academy.input.configuration.deserializers;

import backend.academy.model.plot.Plot;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

/**
 * Десериализатор для афaинных преобразований {@link Plot}. Сделан, чтобы учитывать "default"
 */
public class PlotDeserializer extends JsonDeserializer<Plot> {

    private static final Plot DEFAULT = new Plot(-2, -2, 4, 4);

    @SuppressWarnings("checkstyle:MagicNumber") //
    @Override
    public Plot deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual() && node.asText().strip().equalsIgnoreCase("default")) {
            return DEFAULT;
        } else if (node.isObject()) {
            return new Plot(
                node.get("x").asDouble(),
                node.get("y").asDouble(),
                node.get("width").asDouble(),
                node.get("height").asDouble()
            );
        } else if (node.isArray()) {
            return new Plot(
                node.get(0).asDouble(),
                node.get(1).asDouble(),
                node.get(2).asDouble(),
                node.get(3).asDouble()
            );
        } else {
            throw new IllegalArgumentException("Invalid color format");
        }
    }
}
