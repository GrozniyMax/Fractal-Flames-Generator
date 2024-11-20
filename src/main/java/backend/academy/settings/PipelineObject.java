package backend.academy.settings;

import backend.academy.model.plot.Plot;
import backend.academy.settings.correction.CorrectionObject;
import backend.academy.settings.deserializers.PlotDeserializer;
import backend.academy.settings.symmetry.SymmetryObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter
public class PipelineObject {

    @JsonProperty("plot")
    @JsonDeserialize(using = PlotDeserializer.class)
    private Plot plot;

    @JsonProperty("mode")
    private Modes mode;

    @JsonProperty("symmetry")
    private SymmetryObject<?> symmetry;

    @JsonProperty("correction")
    private CorrectionObject<?> correctionObject;

}
