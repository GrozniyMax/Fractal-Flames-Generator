package backend.academy.processing.correction.stupidCorrection;

import backend.academy.processing.correction.Corrector;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StupidCorrection implements Corrector {

    protected final int hitsCount;


}
