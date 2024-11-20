package backend.academy.processing;

public class GeneratingError extends RuntimeException {
    public GeneratingError(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getCause().getMessage();
    }
}
