package backend.academy.processing.generating.functions;

import backend.academy.model.image.Image;
import backend.academy.model.math.symmetry.Symmetry;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import java.awt.Color;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class SymmetrizedFunction extends Function {

    private Symmetry symmetry;

    public SymmetrizedFunction(BasicTransformation transformation, Color color, Symmetry symmetry) {
        super(transformation, color);
        this.symmetry = symmetry;
    }

    public SymmetrizedFunction(Function function, Symmetry symmetry) {
        super(function.transformation, function.color);
        this.symmetry = symmetry;
    }

    @Override
    public void put(Point point, Plot plot, Image image) {
        symmetry.apply(point)
            .forEach(point1 -> super.put(point1, plot, image));
    }

    public static Wrapper wrapper() {
        return new Wrapper();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wrapper {
        private Symmetry symmetry;

        public Wrapper symmetry(Symmetry symmetry) {
            this.symmetry = symmetry;
            return this;
        }

        public SymmetrizedFunction wrap(Function function) {
            return new SymmetrizedFunction(function, symmetry);
        }
    }
}
