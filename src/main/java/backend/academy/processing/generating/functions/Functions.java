package backend.academy.processing.generating.functions;

import backend.academy.model.math.MathFucntion;
import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.math.variations.Variations;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Functions {

    private final List<Function> functions = new ArrayList<>();

    public Functions add(Function f){
        functions.add(f);
        return this;
    }

    public Function getRandom(){
        Random r = ThreadLocalRandom.current();
        return functions.get(r.nextInt(functions.size()));
    }

    public void addSymmetry(Symmetry symmetry){
        for (int i = 0; i < functions.size(); i++) {
            functions.set(i, SymmetrizedFunction.wrapper().symmetry(symmetry).wrap(functions.get(i)));
        }
    }

    public void andThenForAll(MathFucntion fucntion) {
        functions.forEach(function -> function.andThen(fucntion));
    }

    public void setVariationsForAll(Variations variations) {
        functions.forEach(function -> function.setVariations(variations));
    }

}
