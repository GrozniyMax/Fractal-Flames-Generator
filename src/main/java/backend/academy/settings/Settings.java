package backend.academy.settings;

import backend.academy.settings.functions.FunctionObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Settings {

    @JsonProperty("functions")
    private List<FunctionObject<?>> functions;

    @JsonProperty("pipeline")
    private PipelineObject pipeline;

}
