package backend.academy.correction;

import backend.academy.model.image.Image;

public class NoCorrection implements Corrector{
    @Override
    public void accept(Image image) {
        return;
    }
}
