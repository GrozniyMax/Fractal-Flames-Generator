package backend.academy.generating.functions;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;

@Log4j2
public class Function {

    @Getter
    protected AbstractTransformation transformation;

    @Getter
    protected Color color;

    public Function(AbstractTransformation transformation, Color color) {
        this.transformation = transformation;
        this.color = color;
    }

    public Point acceptAndPutToImage(Point point, Plot plot, Image image) {
        Point result = accept(point);
        put(result, plot, image);
        return result;
    }

    protected void put(Point point, Plot plot, Image image) {
        point = point.multiply(0.90);
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

        int pixel = (int) (((x - plot.x()) / plot.width()) * (image.width()));
        log.info("Converted x: from {} to {}", x, pixel);
        return pixel;
    }

    private int convertY(double y, Plot plot, Image image) {
        int pixel = (int) (((y - plot.y()) / plot.height()) * (image.height()));
        log.info("Converted y: from {} to {}", y, pixel);
        return pixel;
    }
}
