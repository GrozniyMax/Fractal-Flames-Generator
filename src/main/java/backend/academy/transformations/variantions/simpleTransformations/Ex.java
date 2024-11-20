package backend.academy.transformations.variantions.simpleTransformations;

import backend.academy.Main;
import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import backend.academy.transformations.variantions.SimpleVariation;

public class Ex implements SimpleVariation {

    private double p0(Point p){
        return Math.sin(AbstractTransformation.calculateTheta(p) + AbstractTransformation.calculateR(p));
    }

    private double p1(Point p){
        return Math.cos(AbstractTransformation.calculateTheta(p) - AbstractTransformation.calculateR(p));
    }

    @Override
    public Point apply(Point point) {
        double p0Cubed = Math.pow(p0(point), 3);
        double p1Cubed = Math.pow(p1(point), 3);

        return new Point(
            p0Cubed + p1Cubed,
            p0Cubed - p1Cubed
        ).multiply(AbstractTransformation.calculateR(point));
    }
}
