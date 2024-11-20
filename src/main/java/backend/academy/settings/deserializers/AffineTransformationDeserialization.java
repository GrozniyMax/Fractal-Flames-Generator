package backend.academy.settings.deserializers;

import backend.academy.settings.functions.AffineFunctionObject;
import backend.academy.transformations.AffineTransformation;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.io.IOException;
import java.util.Objects;

public class AffineTransformationDeserialization extends JsonDeserializer<AffineFunctionObject> {

    @Override
    public AffineFunctionObject deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual()) {
            if (node.asText().strip().equalsIgnoreCase("random")) {
                return new AffineFunctionObject(0, 0, 0, 0, 0, 0) {
                    {
                        //todo remove to random
                        this.color(Color.CYAN);
                    }

                    @Override
                    public AffineTransformation getRealType() {
                        return AffineTransformation.random();
                    }
                };
            }

            throw new IllegalArgumentException("Unable to parse");
        }else if (node.isObject()){
            if (Objects.nonNull(node.get("generated"))) {
                if (node.get("generated").asText().strip().equalsIgnoreCase("random")) {

                    return new AffineFunctionObject(0, 0, 0, 0, 0, 0) {
                        {
                            //todo remove to random
                            this.color(Color.CYAN);
                        }

                        @Override
                        public AffineTransformation getRealType() {
                            return AffineTransformation.random();
                        }
                    };
                }
            }
            return new AffineFunctionObject(
                node.get("a").asDouble(),
                node.get("b").asDouble(),
                node.get("c").asDouble(),
                node.get("d").asDouble(),
                node.get("e").asDouble(),
                node.get("f").asDouble()
            );
        }else if (node.isArray()) {
            return new AffineFunctionObject(
                node.get(0).asDouble(),
                node.get(1).asDouble(),
                node.get(2).asDouble(),
                node.get(3).asDouble(),
                node.get(4).asDouble(),
                node.get(5).asDouble()
            );
        }else {
            throw new IllegalArgumentException("Unable to parse");
        }
    }
}
