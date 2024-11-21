package backend.academy.input.configuration.deserializers;

import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.correction.logarithmicGammaCorrection.GammaCorrectionFactory;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class LogarithmicCorrectionObjectDeserializer extends JsonDeserializer<AbstractLogarithmicGammaCorrection> {
    @Override
    public AbstractLogarithmicGammaCorrection deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        if (node.isObject()){
            double gamma = node.get("gamma").asDouble();
            String option = node.get("thread-options").asText();
            return GammaCorrectionFactory.create(option, gamma);
        }
        throw new IllegalArgumentException("Unable to parse AbstractLogarithmicGammaCorrection");
    }
}
