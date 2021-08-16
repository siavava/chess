package ChessErrors;

public class OccupiedCellException extends Exception {
    private final ChessErrors instance;

    public OccupiedCellException(Throwable e) {
        super(e);
        this.instance = ChessErrors.OCCUPIED_CELL;
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
