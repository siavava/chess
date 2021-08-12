package ChessErrors;

public enum ChessErrors {
    OCCUPIED_CELL(0, "The cell is occupied."),
    INVALID_MOVEMENT(1, "The movement is invalid");

    private final int code;
    private final String description;

    ChessErrors(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getErrorCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
