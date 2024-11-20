package backend.academy.settings.symmetry;

import backend.academy.transformations.symmenry.SymmetryTransformation;
import backend.academy.transformations.symmenry.YSymmetryTransformation;

public class YSymmetryObject extends SymmetryObject{
    @Override
    public SymmetryTransformation getRealType() {
        return new YSymmetryTransformation();
    }
}
