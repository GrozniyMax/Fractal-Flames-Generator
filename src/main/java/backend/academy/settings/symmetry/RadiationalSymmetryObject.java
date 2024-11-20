package backend.academy.settings.symmetry;

import backend.academy.transformations.symmenry.RadialSymmetryTransformation;
import backend.academy.transformations.symmenry.SymmetryTransformation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RadiationalSymmetryObject extends SymmetryObject{

    @JsonProperty("sectors")
    private int sectors;

    @Override
    public SymmetryTransformation getRealType() {
        return new RadialSymmetryTransformation(sectors);
    }
}
