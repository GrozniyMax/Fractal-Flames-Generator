package backend.academy.output.cli;

import java.io.PrintStream;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProgressBar {
    private static final int LENGTH = 50;
    private static final int UPDATE_EVERY_N = 20;

    private final int maxIterations;
    private final PrintStream out;
    private final int updateEveryN;
    private final int length;
    private int currentN = 0;

    @Builder
    public ProgressBar(int maxIterations, PrintStream out, int updateEveryN, int length) {
        this.maxIterations = maxIterations;
        this.out = out;
        this.updateEveryN = updateEveryN;
        this.length = length;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public void update(int currentIteration) {

        // Обновили счетчик
        currentN++;
        if (currentN != updateEveryN) {
            return;
        }
        currentN = 0;

        StringBuilder builder = new StringBuilder();
        builder.append(' ').repeat("-", length).append('\n');

        int currentLength = translate(currentIteration);

        builder.append('|')
            .repeat("=", (currentLength))
            .repeat(" ", length - currentLength)
            .append('|')
            .append("%.2f".formatted(((double) currentIteration) / maxIterations * 100) + "%")
            .append("\n");

        builder.append(" ").repeat("-", length).append("\n");

        out.println(builder);
    }

    private int translate(double current) {
        return (int) (current / maxIterations * length);
    }

    public static ProgressBar createDefault(int maxIterations, PrintStream out) {
        return createWithDefaultLength(maxIterations, out, UPDATE_EVERY_N);
    }

    public static ProgressBar createWithDefaultLength(int maxIterations, PrintStream out, int updateEveryN) {
        return new ProgressBar(maxIterations, out, updateEveryN, LENGTH);
    }

}
