package backend.academy.generating;

import backend.academy.model.image.Image;
import backend.academy.model.plot.Plot;
import backend.academy.model.plot.Point;
import backend.academy.transformations.AbstractTransformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;

@Log4j2
@RequiredArgsConstructor
public class Function {

    private final AbstractTransformation transformation;
    private final Color color;


    public Point acceptAndPutToImage(Point point, Plot plot, Image image) {
        Point result = accept(point);
        int x = convertX(result.x(), plot, image);
        int y = convertY(result.y(), plot, image);
        log.info("Adding point to image: x={}, y={}", x, y);
        image.put(
            convertX(result.x(), plot, image),
            convertY(result.y(), plot, image),
            color
        );
        return result;
    }

    public Point accept(Point point) {
        return transformation.apply(point);
    }

    private int convertX(double x, Plot plot, Image image) {

        return (int) (((x - plot.x()) / plot.width()) * image.width());
    }

    private int convertY(double y, Plot plot, Image image) {
        int newY = (int) (((y - plot.y()) / plot.height()) * image.height());
        return image.height() - newY;
    }
}
