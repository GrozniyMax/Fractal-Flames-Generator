package backend.academy.generating.functions;

import backend.academy.transformations.symmenry.SymmetryTransformation;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Functions {

    private final List<Function> functions = new ArrayList<>();

    public Functions add(Function f){
        functions.add(f);
        return this;
    }

    public Function getRandom(){
        SecureRandom r = new SecureRandom();
        return functions.get(r.nextInt(functions.size()));
    }

    public void addSymmetry(SymmetryTransformation transformation){
        Symmetry s = new Symmetry(transformation);
        for (int i = 0; i < functions.size(); i++) {
            functions.set(i, s.wrap(functions.get(i)));
        }
    }

}
