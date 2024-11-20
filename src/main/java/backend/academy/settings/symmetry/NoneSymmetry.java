package backend.academy.settings.symmetry;

import backend.academy.transformations.symmenry.SymmetryTransformation;

public class NoneSymmetry extends SymmetryObject<SymmetryTransformation> {
    @Override
    public SymmetryTransformation getRealType() {
        return null;
    }
}
