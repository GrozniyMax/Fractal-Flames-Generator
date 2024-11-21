package backend.academy.input.configuration.deserializers;


import backend.academy.model.math.transformations.inheritors.AffineTransformation;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.io.IOException;
import java.util.Objects;

public class AffineTransformationDeserialization extends JsonDeserializer<AffineTransformation> {

    @Override
    public AffineTransformation deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual()) {
            if (node.asText().strip().equalsIgnoreCase("random")) {
                return AffineTransformation.random();
            }
            throw new IllegalArgumentException("Unable to parse AffineTransformation");
        }else if (node.isObject()){
            if (Objects.nonNull(node.get("generated"))) {
                if (node.get("generated").asText().strip().equalsIgnoreCase("random")) {
                    return AffineTransformation.random();
                }
            }
            return new AffineTransformation(
                node.get("a").asDouble(),
                node.get("b").asDouble(),
                node.get("c").asDouble(),
                node.get("d").asDouble(),
                node.get("e").asDouble(),
                node.get("f").asDouble()
            );
        }else if (node.isArray()) {
            return new AffineTransformation(
                node.get(0).asDouble(),
                node.get(1).asDouble(),
                node.get(2).asDouble(),
                node.get(3).asDouble(),
                node.get(4).asDouble(),
                node.get(5).asDouble()
            );
        }else {
            throw new IllegalArgumentException("Unable to parse affine transformation");
        }
    }
}
