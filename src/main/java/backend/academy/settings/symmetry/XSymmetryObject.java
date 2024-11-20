package backend.academy.settings.symmetry;

import backend.academy.transformations.symmenry.SymmetryTransformation;
import backend.academy.transformations.symmenry.XSymmetryTransformation;

public class XSymmetryObject extends SymmetryObject{
    @Override
    public SymmetryTransformation getRealType() {
        return new XSymmetryTransformation();
    }
}
