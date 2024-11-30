package backend.academy.processing.correction;

import backend.academy.model.image.Image;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Corrector extends Consumer<Image>, Function<Image, Image> {
    static Corrector identity() {
        return image -> {
            return;
        };
    }

    @Override
    default Image apply(Image image) {
        accept(image);
        return image;
    }
}
