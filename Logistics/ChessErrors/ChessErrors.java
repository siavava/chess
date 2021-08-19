package ChessErrors;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public enum ChessErrors implements Serializable {
    OCCUPIED_CELL(0, "The cell is occupied."),
    ILLEGAL_MOVE(1, "The movement is invalid"),
    INVALID_CELL(2, "The cell reference is invalid."),
    FAILED_INIT(3, "Initialization of pieces failed."),
    INVALID_TURN(4, "It's not your turn to play.");

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

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return getErrorCode() + ": " + description;
    }
}
