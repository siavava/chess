package ChessErrors;

public class IllegalMoveException extends Exception {
    private final ChessErrors instance;

    public IllegalMoveException(Throwable e) {
        super(e);
        this.instance = ChessErrors.ILLEGAL_MOVE;
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
