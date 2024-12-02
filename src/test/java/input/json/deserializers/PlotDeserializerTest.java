package input.json.deserializers;

import backend.academy.input.configuration.deserializers.PlotDeserializer;
import backend.academy.model.math.transformations.inheritors.AffineTransformation;
import backend.academy.model.plot.Plot;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class PlotDeserializerTest {

    @Getter
    static class PlotSample {
        @JsonProperty("plot")
        @JsonDeserialize(using = PlotDeserializer.class)
        private Plot plot;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void validPlotAsObject_shouldGiveExpectedPlot() throws JsonProcessingException {
        String json = """
            { "plot": {
                "x": 125,
                "y": 45,
                "width": 254,
                "height": 474
               }
            }
            """;
        Plot expected = new Plot(125, 45, 254, 474);

        PlotSample actual = objectMapper.readValue(json, PlotSample.class);

        Assertions.assertThat(actual.plot).isEqualTo(expected);
    }

    @Test
    void validColorAsArray_shouldGiveExpectedColor() throws JsonProcessingException {
        String json = """
            { "plot": [1,1,1,1]
            }
            """;
        Plot expected = new Plot(1, 1, 1, 1);

        PlotSample actual = objectMapper.readValue(json, PlotSample.class);

        Assertions.assertThat(actual.plot).isEqualTo(expected);
    }

    @RepeatedTest(5)
    void randomColor_shouldNotThrowException() {
        String json = """
            { "plot": "default" }
            """;

        Assertions.assertThatNoException().isThrownBy(()->objectMapper.readValue(json, PlotSample.class));
    }

    @Test
    void missingColorPartAsObject_shouldThrowException() {
        String content = """
            { "plot": {
                "x": 125,
                "y": 45
               }
            }
            """;

        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, AffineDeserializerTest.AffineSample.class));
    }

    @Test
    void missingColorPartAsArray_shouldThrowException() {
        String content = """
            {"plot": [10,2]}
            """;
        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, AffineDeserializerTest.AffineSample.class));
    }
}
