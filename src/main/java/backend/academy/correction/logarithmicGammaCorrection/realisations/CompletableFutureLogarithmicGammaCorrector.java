package backend.academy.correction.logarithmicGammaCorrection.realisations;

import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.model.image.Image;
import backend.academy.model.image.Pixel;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
public class CompletableFutureLogarithmicGammaCorrector extends AbstractLogarithmicGammaCorrection {

    public CompletableFutureLogarithmicGammaCorrector(double gamma) {
        super(gamma);
    }

    @Override
    public void accept(Image image) {

        double maxLog = Math.log(
            image.rowsStream()
                .map(row ->
                    CompletableFuture.supplyAsync(() ->
                        Arrays.stream(row).map(Pixel::hitCount).max(Integer::compareTo).orElse(Integer.MIN_VALUE)
                    )
                ).map(CompletableFuture::join)
                .max(Integer::compareTo)
                .map(Number::doubleValue)
                .orElse(0.0)
        );

        image.rowsStream()
            .map(row ->
                CompletableFuture.runAsync(() -> {
                        double gammaFactor;
                        for (Pixel pixel : row) {
                            gammaFactor = Math.log(pixel.hitCount()) / maxLog;
                            gammaFactor = gammaFactor * gamma;
                            pixel.multiply(gammaFactor);
                        }
                    }
                ))
            .forEach(this::get);
    }

    private void get(CompletableFuture<?> future) {
        try {
            future.get();
        } catch (InterruptedException e) {
            log.error("Future was interrupted");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
