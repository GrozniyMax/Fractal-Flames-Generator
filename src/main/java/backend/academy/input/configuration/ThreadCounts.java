package backend.academy.input.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadCounts {

    @JsonProperty("generator")
    private int generator;

    @JsonProperty("pipeline")
    private int pipeline;

    @JsonProperty("image-writer")
    private int imageWriter;
}
