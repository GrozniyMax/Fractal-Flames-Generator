package input.json.deserializers;

import backend.academy.input.configuration.deserializers.ColorDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.awt.Color;

public class ColorDeserializerTest {

    @Getter
    static class ColorSample {
        @JsonProperty("color")
        @JsonDeserialize(using = ColorDeserializer.class)
        private Color color;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void validColorAsObject_shouldGiveExpectedColor() throws JsonProcessingException {
        String json = """
            { "color": {
                "r": 125,
                "g": 45,
                "b": 254
               }
            }
            """;
        Color expected = new Color(125, 45, 254);

        ColorSample actual = objectMapper.readValue(json, ColorSample.class);

        Assertions.assertThat(actual.color).isEqualTo(expected);
    }

    @Test
    void validColorAsArray_shouldGiveExpectedColor() throws JsonProcessingException {
        String json = """
            { "color": [125, 45, 25]
            }
            """;
        Color expected = new Color(125, 45, 25);

        ColorSample actual = objectMapper.readValue(json, ColorSample.class);

        Assertions.assertThat(actual.color).isEqualTo(expected);
    }

    @RepeatedTest(5)
    void randomColor_shouldNotThrowException() {
        String json = """
            { "color": "random" }
            """;

        Assertions.assertThatNoException().isThrownBy(()->objectMapper.readValue(json, ColorSample.class));
    }

    @Test
    void missingColorPartAsObject_shouldThrowException() {
        String content = """
            {"color": {
                "r":14}
                }
            """;

        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, ColorSample.class));
    }

    @Test
    void missingColorPartAsArray_shouldThrowException() {
        String content = """
            {"color": [10,2]}
            """;
        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, ColorSample.class));
    }

    @Test
    void illegalPropertyValue_shouldThrowException() {
        String content = """
            {
            "color": [300, 14, 25]
            }
            """;

        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, ColorSample.class));
    }


}
