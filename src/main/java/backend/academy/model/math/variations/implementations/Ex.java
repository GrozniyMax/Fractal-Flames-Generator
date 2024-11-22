package backend.academy.model.math.variations.implementations;

import backend.academy.model.math.variations.SimpleVariation;
import backend.academy.model.plot.Point;

public final class Ex implements SimpleVariation {
    // Number 12

    private double p0(Point p){
        return Math.sin(SimpleVariation.calculateTheta(p) + SimpleVariation.calculateR(p));
    }

    private double p1(Point p){
        return Math.cos(SimpleVariation.calculateTheta(p) - SimpleVariation.calculateR(p));
    }

    @Override
    public Point apply(Point point) {
        double p0Cubed = Math.pow(p0(point), 3);
        double p1Cubed = Math.pow(p1(point), 3);

        return new Point(
            p0Cubed + p1Cubed,
            p0Cubed - p1Cubed
        ).multiply(SimpleVariation.calculateR(point));
    }
}
