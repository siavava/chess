package ChessErrors;

public class InvalidCellException extends Exception {
    private final ChessErrors instance;

    public InvalidCellException(Throwable e) {
        super(e);
        this.instance = ChessErrors.INVALID_CELL;
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
