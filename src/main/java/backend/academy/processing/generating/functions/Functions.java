package backend.academy.processing.generating.functions;

import backend.academy.model.math.MathFunction;
import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.math.variations.Variations;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Functions {

    private final List<Function> functions = new ArrayList<>();

    public Functions add(Function f) {
        functions.add(f);
        return this;
    }

    public Function getRandom() {
        Random r = ThreadLocalRandom.current();
        return functions.get(r.nextInt(functions.size()));
    }

    public void addSymmetry(Symmetry symmetry) {
        functions.replaceAll(function -> SymmetrizedFunction.wrapper().symmetry(symmetry).wrap(function));
    }

    public void andThenForAll(MathFunction fucntion) {
        functions.forEach(function -> function.andThen(fucntion));
    }

    public void setVariationsForAll(Variations variations) {
        functions.forEach(function -> function.setVariations(variations));
    }

}
