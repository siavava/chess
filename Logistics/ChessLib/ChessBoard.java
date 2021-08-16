package ChessLib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* custom packages */
import ChessErrors.*;
import Pieces.*;

public class ChessBoard {
    public static final int BOARD_SIZE = 800;
    public static final int CELL_SIZE = BOARD_SIZE / 8;
    public static final int DARK_COLOR = Color.GRAY.getRGB();
    public static final int LIGHT_COLOR = Color.PINK.getRGB();

    private final BufferedImage board;
    private final Map<Integer, Cell> cells;

    public static class Cell {
        private final Color color;
        private final ChessBoard parentBoard;
        private final Point position;
        private Piece occupant = null;

        public Cell(ChessBoard parentBoard, int file, int rank) {
            this.parentBoard = parentBoard;
            this.position = new Point(file, rank);

            boolean isLightSquare = (file + rank) % 2 != 0;
            this.color = isLightSquare? new Color(LIGHT_COLOR) : new Color(DARK_COLOR);
        }

        public boolean hasOccupant() {
            return this.occupant != null;
        }

        public Piece getOccupant() {
            return this.occupant;
        }

        public void addOccupant(Piece occupant) throws CellOccupiedException {

            try {
                assert this.occupant == null;
                this.occupant = occupant;
            }
            catch (AssertionError e) {
                throw new CellOccupiedException(e);
            }
        }

        public void removeOccupant() {
            this.occupant = null;
        }

        public boolean isPoint(int file, int rank) {
            assert(file > 0 && file < 9);
            assert(rank > 0 && rank < 9);
            return this.position.equals(new Point(file, rank));
        }

        public List<Integer> nextMoves() {
            return occupant != null ? occupant.getPosMoves() : null;
        }
    }


    public ChessBoard() {
        // Create image of chess board
        board = new BufferedImage(BOARD_SIZE, BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
        this.cells = new HashMap<>();

        // Track x coordinate

        // Loop over columns in board
        for (int file=1; file<=8; file++) {

            // Loop over rows in board
            for (int rank=1; rank<=8; rank++) {
                // Determine whether to color Black or white
                boolean isLightSquare = (file + rank) % 2 != 0;
                int color = isLightSquare? LIGHT_COLOR : DARK_COLOR;

                cells.put(ChessLib.refToInt(file, rank), new Cell(this, file, rank));


                Point currentDims = ChessLib.refToReal(new Point(file, rank));
                int x = (int) currentDims.getX();
                int y = (int) currentDims.getY();

                // Loop over each pixel in cell and set color.
                for (int px = x; px < x+ CELL_SIZE; px++) {
                    for (int py = y; py < y+ CELL_SIZE; py++) {
                        try {
                            board.setRGB(px, py, color);
                        }
                        catch (Exception e) {
                            System.err.println("x: " + px + ", y: " + py + " is out of bounds.");
                        }
                    }
                }
                // step to next row
            }
            // step to next column
        }
    }

    public boolean isInBoard(Point p) {
        return 0<=p.getX() && p.getX()< BOARD_SIZE && 0 <= p.getY() && p.getY() < BOARD_SIZE;
    }

    /**
     * Method to draw board.
     * @param g Graphics window
     */
    public void draw(Graphics g) {
        g.drawImage(board, 0, 0, null);
    }
}
