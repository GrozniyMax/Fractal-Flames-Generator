package backend.academy.correction.logarithmicGammaCorrection.realisations;

import backend.academy.correction.logarithmicGammaCorrection.AbstractLogarithmicGammaCorrection;
import backend.academy.model.image.Image;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@Log4j2
public class TreadPoolLogarithmicGammaCorrector extends AbstractLogarithmicGammaCorrection {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public TreadPoolLogarithmicGammaCorrector(double gamma) {
        super(gamma);
    }

    @RequiredArgsConstructor
    private static class ArrayMaxTask implements Callable<Integer> {

        private final int rowIndex;
        private final Image image;

        @Override
        public Integer call() throws Exception {
            int max = image.get(0, rowIndex).hitCount();
            int currentHitCount;
            for (int i = 1; i < image.width(); i++) {
                currentHitCount = image.get(i, rowIndex).hitCount();
                if (max<currentHitCount) {
                    max = currentHitCount;
                }
            }

            return max;
        }
    }

    @RequiredArgsConstructor
    private static class UpdatePixelsTast implements Runnable{
        private final int row;
        private final double maxHitLog;
        private final double gamma;
        private final Image image;

        @Override
        public void run() {
            double gammaFactor;
            for (int i = 0; i < image.width(); i++) {
                gammaFactor = Math.log(image.get(i, row).hitCount()) / maxHitLog;
                gammaFactor = gammaFactor * gamma;
                image.get(i, row).multiply(gammaFactor);
            }
        }
    }

    @Override
    public void accept(Image image) {
        double maxLog  = Math.log(
        IntStream.range(0, image.height())
            .mapToObj(index->new ArrayMaxTask(index, image))
            .map(executorService::submit)
            .map(this::get)
            .max(Integer::compareTo)
            .map(Number::doubleValue)
            .orElse(0.0)
        );
    }

    private Integer get(Future<Integer> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            log.error("Future was interrupted");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("Error in generating");
            throw new RuntimeException(e);
        }
    }
}
