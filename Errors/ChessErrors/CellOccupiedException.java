package ChessErrors;

public class CellOccupiedException extends Exception {
    ChessErrors instance;

    public CellOccupiedException(Throwable e) {
        super(e);
        this.instance = ChessErrors.OCCUPIED_CELL;
    }

    public String toString() {
        return instance.toString();
    }
}
