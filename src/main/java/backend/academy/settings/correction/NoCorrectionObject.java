package backend.academy.settings.correction;

import backend.academy.correction.NoCorrection;

public class NoCorrectionObject extends CorrectionObject<NoCorrection> {
    @Override
    public NoCorrection getRealType() {
        return new NoCorrection();
    }
}
