package backend.academy.correction.stupidCorrection;

import backend.academy.correction.Corrector;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StupidCorrection implements Corrector {

    protected final int hitsCount;


}
