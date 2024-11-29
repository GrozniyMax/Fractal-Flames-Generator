package backend.academy.processing.generating.functions;

import backend.academy.model.image.Image;
import backend.academy.model.math.MathFucntion;
import backend.academy.model.math.transformations.BasicTransformation;
import backend.academy.model.math.variations.Variations;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import java.awt.Color;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Function {

    @Getter
    protected BasicTransformation transformation;

    @Getter
    protected Color color;

    public Function(BasicTransformation transformation, Color color) {
        this.transformation = transformation;
        this.color = color;
    }

    public Point acceptAndPutToImage(Point point, Plot plot, Image image) {
        Point result = accept(point);
        put(result, plot, image);
        return result;
    }

    public void put(Point point, Plot plot, Image image) {
        int x = convertX(point.x(), plot, image);
        int y = convertY(point.y(), plot, image);
        image.put(
            x,
            y,
            color
        );
    }

    public Point accept(Point point) {
        return transformation.apply(point);
    }

    private int convertX(double x, Plot plot, Image image) {
        return (int) (((x - plot.x()) / plot.width()) * (image.width()));
    }

    private int convertY(double y, Plot plot, Image image) {
        return (int) (((y - plot.y()) / plot.height()) * (image.height()));
    }

    public void andThen(MathFucntion function) {
        transformation = transformation.andThen(function);
    }

    public void setVariations(Variations variations) {
        transformation.variations(variations);
    }
}
