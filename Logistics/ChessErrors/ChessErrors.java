package ChessErrors;

import java.io.Serializable;

public enum ChessErrors implements Serializable {
    OCCUPIED_CELL(0, "The cell is occupied."),
    ILLEGAL_MOVE(1, "The movement is invalid");

    private transient final int errCode;
    private transient final String description;

    ChessErrors(int code, String description) {
        this.errCode = code;
        this.description = description;
    }

    public int getErrorCode() {
        return this.errCode;
    }

    public String getMessage() {
        return this.description;
    }

    @Override
    public String toString() {
        return errCode + ": " + description;
    }
}
