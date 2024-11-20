package backend.academy.generating.functions;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import backend.academy.transformations.symmenry.SymmetryTransformation;
import java.awt.Color;

class Symmetry extends Function {

    private SymmetryTransformation symmetry;

    private Symmetry(AbstractTransformation transformation, Color color) {
        super(transformation, color);
    }

    public Symmetry(SymmetryTransformation transformation) {
        super(AbstractTransformation.IDENTITY, Color.BLACK);
        symmetry = transformation;
    }

    public Symmetry wrap(Function function) {
        this.transformation = function.transformation();
        this.color = function.color();
        return this;
    }

    @Override
    public Point acceptAndPutToImage(Point point, Plot plot, Image image) {
        Point result = super.acceptAndPutToImage(point, plot, image);
        symmetry.apply(result)
                .forEach(p->put(p, plot, image));
        return result;
    }


}
