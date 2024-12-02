package input.json.deserializers;

import backend.academy.input.configuration.deserializers.AffineTransformationDeserialization;
import backend.academy.model.math.transformations.inheritors.AffineTransformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class AffineDeserializerTest {
    @Getter
    static class AffineSample {
        @JsonProperty("affine")
        @JsonDeserialize(using = AffineTransformationDeserialization.class)
        private AffineTransformation affine;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void validAffineAsObject_shouldGiveExpectedAffine() throws JsonProcessingException {
        String json = """
            { "affine": {
                "a": 125,
                "b": 45,
                "c": 254,
                "d": 474,
                "e": 45,
                "f": 15
               }
            }
            """;
        AffineTransformation expected = new AffineTransformation(125, 45, 254, 474, 45, 15);

        AffineSample actual = objectMapper.readValue(json, AffineSample.class);

        Assertions.assertThat(actual.affine).isEqualTo(expected);
    }

    @Test
    void validAffineAsArray_shouldGiveExpectedAffine() throws JsonProcessingException {
        String json = """
            { "affine": [1,1,1,1,1,1]
            }
            """;
        AffineTransformation expected = new AffineTransformation(1, 1, 1, 1, 1, 1);

        AffineSample actual = objectMapper.readValue(json, AffineSample.class);

        Assertions.assertThat(actual.affine).isEqualTo(expected);
    }

    @RepeatedTest(5)
    void randomAffine_shouldNotThrowException() {
        String json = """
            { "affine": "random" }
            """;

        Assertions.assertThatNoException().isThrownBy(()->objectMapper.readValue(json, AffineSample.class));
    }

    @Test
    void missingAffinePartAsObject_shouldThrowException() {
        String content = """
            {"affine": {
                "a": 125,
                "b": 45,
                "c": 254,
                "d": 474,
                "e": 45
                }
            }
            """;

        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, AffineSample.class));
    }

    @Test
    void missingAffinePartAsArray_shouldThrowException() {
        String content = """
            {"affine": [10,2]}
            """;
        Assertions.assertThatThrownBy(() -> objectMapper.readValue(content, AffineSample.class));
    }
}
