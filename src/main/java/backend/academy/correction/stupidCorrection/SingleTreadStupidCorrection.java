package backend.academy.correction.stupidCorrection;

import backend.academy.correction.Corrector;
import backend.academy.model.image.Image;
import java.awt.Color;
import java.util.Arrays;

public class SingleTreadStupidCorrection extends StupidCorrection {
    public SingleTreadStupidCorrection(int hitsCount) {
        super(hitsCount);
    }

    @Override
    public void accept(Image image) {
        image.rowsStream()
            .flatMap(Arrays::stream)
            .filter(pixel -> pixel.hitCount() < hitsCount)
            .forEach(pixel -> pixel.setColor(Color.BLACK));
    }
}
