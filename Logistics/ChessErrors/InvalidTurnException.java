package ChessErrors;

public class InvalidTurnException extends Exception {
    private final ChessErrors instance;

    public InvalidTurnException(Throwable e) {
        super(e);
        this.instance = ChessErrors.INVALID_TURN;
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
