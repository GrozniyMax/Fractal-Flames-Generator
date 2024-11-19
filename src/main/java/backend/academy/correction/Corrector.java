package backend.academy.correction;

import backend.academy.model.image.Image;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Corrector extends Consumer<Image> {
}
