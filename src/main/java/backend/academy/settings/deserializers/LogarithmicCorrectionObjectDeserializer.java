package backend.academy.settings.deserializers;

import backend.academy.settings.correction.CompletableFutureCorrectionObject;
import backend.academy.settings.correction.LogarithmicCorrectionObject;
import backend.academy.settings.correction.SingleTreadCorrectorObject;
import backend.academy.settings.correction.TreadPoolLogarithmicCorrector;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.io.IOException;

public class LogarithmicCorrectionObjectDeserializer extends JsonDeserializer<LogarithmicCorrectionObject<?>> {
    @Override
    public LogarithmicCorrectionObject<?> deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        if (node.isObject()){
            double gamma = node.get("gamma").asDouble();
            switch (node.get("thread-options").asText()) {
                case "single" -> {
                    return new SingleTreadCorrectorObject(gamma);
                }
                case "completable-future"->{
                    return new CompletableFutureCorrectionObject(gamma);
                }
                case "tread-pool"->{
                    return new TreadPoolLogarithmicCorrector(gamma);
                }
            }
        }
        throw new IllegalArgumentException("Unable to parse");
    }
}
