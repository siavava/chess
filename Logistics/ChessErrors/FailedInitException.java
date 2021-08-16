package ChessErrors;

public class FailedInitException extends Exception {
    private final ChessErrors instance;

    public FailedInitException(Throwable e) {
        super(e);
        this.instance = ChessErrors.FAILED_INIT;
    }

    @Override
    public String toString() {
        return instance.toString();
    }

    @Override
    public String getMessage() {
        return instance.getMessage();
    }
}
