package ChessLib;

import ChessErrors.InvalidCellException;
import ChessGame.ChessGame;
import Pieces.King;
import Pieces.Piece.ID;
import Pieces.*;
import Pieces.Piece.Suit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A `static` Library of utility functions and shared data for the Chess project.
 */
public class ChessUtilities {

    public static final int BOARD_SIZE;
    public static final int CELL_SIZE;
    public static final int DARK_COLOR;
    public static final int LIGHT_COLOR;

    public static final String startingFEN;

    public static final Map<ID, List<Integer>> ALL_MOVES;

    public static final Map<ID, Integer> ALL_VALUES;

    public static final Map<ID, Boolean> GLIDE_STATUS;

    /* static block to initialize all variables,
     * so they are not recomputed every other time
     * the value is accessed.
     */
    static {

        /* constant values */
        BOARD_SIZE = 800;
        CELL_SIZE = BOARD_SIZE / 8;
        DARK_COLOR = Color.GRAY.getRGB();
        LIGHT_COLOR = Color.PINK.getRGB();

        startingFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        ALL_MOVES = Map.of (
                ID.KING, Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9),
                ID.QUEEN, Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9),
                ID.ROOK, Arrays.asList(-8, -1, 1, 8),
                ID.BISHOP, Arrays.asList(-9, -7, 7, 9),
                ID.KNIGHT, Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17),
                ID.PAWN, Collections.singletonList(8)
        );

        ALL_VALUES = Map.of (
                ID.KING, Integer.MAX_VALUE,
                ID.QUEEN, 9,
                ID.ROOK, 5,
                ID.BISHOP, 3,
                ID.KNIGHT, 3,
                ID.PAWN, 1
        );

        GLIDE_STATUS = Map.of (
                ID.KING, false,
                ID.QUEEN, true,
                ID.ROOK, true,
                ID.BISHOP, true,
                ID.KNIGHT, false,
                ID.PAWN, false
        );
    }

    public static void loadFEN(@NotNull ChessGame gameInstance, String fen) {

        parseFenPieces(gameInstance, fen);
        parsePlayerTurn(gameInstance, fen);
        checkCastling(gameInstance, fen);
    }

    public static void loadStartingFEN(ChessGame gameInstance) {
        loadFEN(gameInstance, startingFEN);
    }

    private static void parsePlayerTurn(ChessGame gameInstance, @NotNull String fen) {
        boolean status = false;
        for (char c : fen.toCharArray()) {
            if (status) {
                if (c == 'w') {
                    gameInstance.TURN = Suit.WHITE;
                    break;
                }
                else if (c == 'b') {
                    gameInstance.TURN = Suit.BLACK;
                    break;
                }
            }
            else if (c == ' ') {
                status = true;
            }
        }
    }

    private static void checkCastling(ChessGame gameInstance, @NotNull String fen) {
        int spaces = 0;
        for (char c : fen.toCharArray()) {
            if (c == ' ') spaces++;
            else if (spaces == 2) gameInstance.activateCastling(c);
            else if (spaces > 2) break;
        }
    }

    private static void parseFenPieces(@NotNull ChessGame gameInstance, @NotNull String fen) {
        ChessBoard board = gameInstance.getChessBoard();
        int file = 1, rank = 8;
        boolean pieces = true;
        Suit suit;
        for (char c : fen.toCharArray()) {

            if (pieces) {
                /* check if empty space*/
                if (Character.isDigit(c)) {
                    file += c;
                }
                else {

                    /* determine suit depending on the upper or lower case status*/
                    if (Character.isUpperCase(c)) {
                        suit = Suit.WHITE;
                    } else {
                        suit = Suit.BLACK;
                    }

                    /* Add the piece to the board */
                    switch (Character.toLowerCase(c)) {
                        case 'k' -> board.addPiece(new King(suit, file, rank));
                        case 'q' -> board.addPiece(new Queen(suit, file, rank));
                        case 'r' -> board.addPiece(new Rook(suit, file, rank));
                        case 'b' -> board.addPiece(new Bishop(suit, file, rank));
                        case 'n' -> board.addPiece(new Knight(suit, file, rank));
                        case 'p' -> board.addPiece(new Pawn(suit, file, rank));
                        case '/' -> { file = 0; rank--; }
                        case ' ' -> pieces = false;
                    }
                }
            }
            file++;
            /*
             * Ignore the last characters encoding player turn,  and en-passant.
             */
        }
    }

    /**
     * Static function to get an int and return its position on board
     * @param pos position int
     * @return position Point carrying  and y coordinates
     */
    @Contract("_ -> new")
    public static @NotNull Point numberToRef(int pos) throws InvalidCellException {
        try {
            assert 0 < pos && pos <= 64;
            int row = pos % 8;
            if (row == 0) row += 8;
            int rank = boolToInt(row != 8) + (pos / 8);
            return new Point(row, rank);
        }
        catch (AssertionError e) {
            throw new InvalidCellException(e);
        }
    }

    /**
     * Static Method to convert a reference (row and rank) to a single int coordinate
     * @param file File -> x var
     * @param rank Rank -> y var
     * @return int depending on x and y
     */
    public static int refToNumber(int file, int rank) {
        return (8 * (rank-1) + file);
    }

    public static @NotNull Point realToRef(@NotNull Point p) {

        // Get point coordinates (row and rank)
        double px = p.getX(), py = p.getY();

        // Get size of ChessLib.ChessBoard
        int step = ChessBoard.CELL_SIZE;

        // Check range of x and convert to appropriate cell
        int x = (int) Math.ceil(px/step);

        // Check range of y and convert to appropriate cell
        // TODO: NOTE THAT y GOES IN REVERSE ORDER!
        int y = (int) (8 - Math.floor(py/step));

        // Return Point carrying info of cell
        return new Point(x, y);
    }

    public static @NotNull Point refToReal(@NotNull Point cell) {
        double row = cell.getX(), rank = cell.getY();

        // return Point at top-left corner of cell
        int x = (int) (row - 1) * ChessBoard.CELL_SIZE;
        int y = 800 - (int) (rank) * ChessBoard.CELL_SIZE;
        return new Point (x, y);
    }

    /**
     * Helper method to conveniently convert a boolean to int
     * @param bool boolean value
     * @return false -> 0, true -> 1
     */
    public static int boolToInt(boolean bool) {
        return bool? 1: 0;
    }

    public static int numSteps(Point source, Point dest) {
        int dx = (int) (dest.getX() - source.getX());
        int dy = (int) (dest.getY() - source.getY());
        return 8 * dy + dx;
    }
}
