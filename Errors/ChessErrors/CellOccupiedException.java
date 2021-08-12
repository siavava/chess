package ChessErrors;

public class CellOccupiedException extends Exception {
    private final ChessErrors instance;

    public CellOccupiedException(Throwable e) {
        super(e);
        this.instance = ChessErrors.OCCUPIED_CELL;
    }

    @Override
    public String toString() {
        return instance.toString();
    }
}
