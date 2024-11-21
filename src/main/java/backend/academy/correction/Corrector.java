package backend.academy.correction;

import backend.academy.model.image.Image;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface Corrector extends Consumer<Image> {
    static Corrector identity() {
        return new Corrector() {
            @Override
            public void accept(Image image) {
                return;
            }
        };
    }
}
