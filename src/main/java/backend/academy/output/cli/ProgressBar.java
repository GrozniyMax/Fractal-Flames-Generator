package backend.academy.output.cli;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.io.PrintStream;

@Log4j2
public class ProgressBar {
    private static final int LENGTH = 50;
    private static final int UPDATE_EVERY_N = 20;

    private final int maxIterations;
    private final PrintStream out;

    @Builder
    public ProgressBar(int maxIterations, PrintStream out) {
        this.maxIterations = maxIterations;
        this.out = out;
    }

    private int currentN = 0;

    public void update(int currentIteration) {

        // Обновили счетчик
        currentN++;
        if (currentN != UPDATE_EVERY_N) {
            return;
        }
        currentN = 0;

        StringBuilder builder = new StringBuilder();
        builder.append(' ').repeat("-", LENGTH).append('\n');

        int currentLength = translate(currentIteration);

        builder.append('|')
            .repeat("=", currentLength)
            .repeat(" ", LENGTH - currentLength)
            .append('|').append("\n");

        builder.append(" ").repeat("-", LENGTH).append("\n");

        out.println(builder);
    }

    private int translate(double current) {
        return (int) (current / maxIterations * LENGTH);

    }

}
