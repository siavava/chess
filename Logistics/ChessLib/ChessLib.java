package ChessLib;

import ChessErrors.InvalidCellException;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A Library of utility functions for the Chess project.
 */
public class ChessLib {

    public static Map<Character, List<Integer>> ALL_MOVES = Map.of (
        'K', Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9),
        'Q', Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9),
        'B', Arrays.asList(-9, -7, 7, 9),
        'N', Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17),
        'R', Arrays.asList(-8, -1, 1, 8),
        'P', Collections.singletonList(8)
    );

    /**
     * Static function to get an int and return its position on board
     * @param pos position int
     * @return position Point carrying  and y coordinates
     */
    public static Point intToRef(int pos) throws InvalidCellException {
        try {
            assert 0<pos && pos <= 64;
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
    public static int refToInt(int file, int rank) {
        return (8 * (rank-1) + file);
    }

    public static Point realToRef(@NotNull Point p) {

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

    public static Point refToReal(@NotNull Point cell) {
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
}


