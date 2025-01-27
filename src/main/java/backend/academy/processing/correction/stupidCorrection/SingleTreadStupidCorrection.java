package backend.academy.processing.correction.stupidCorrection;

import backend.academy.model.image.Image;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Color;
import java.util.Arrays;

public class SingleTreadStupidCorrection extends StupidCorrection {

    @JsonCreator
    public SingleTreadStupidCorrection(@JsonProperty("hits-count") int hitsCount) {
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
