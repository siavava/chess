package ChessLib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* custom packages */
import ChessErrors.*;
import Pieces.*;
import Pieces.Piece.Suit;
import org.jetbrains.annotations.NotNull;

public class ChessBoard {
    public static final int BOARD_SIZE = ChessUtilities.BOARD_SIZE;
    public static final int CELL_SIZE = ChessUtilities.CELL_SIZE;
    public static final int DARK_COLOR = ChessUtilities.DARK_COLOR;
    public static final int LIGHT_COLOR = ChessUtilities.LIGHT_COLOR;

    private final BufferedImage board;
    private final Map<Integer, Cell> cells;
    private Map<Integer, Piece> indexOfPieces;

    // track King positions for easy validation.
    private int whiteKingPosition, blackKingPosition;

    public static class Cell {

        private final Point position;
        private Piece occupant = null;

        public Cell(int file, int rank) {
            this.position = new Point(file, rank);
        }

        public Cell (int file, int rank, Piece piece) {
            this (file, rank);
            try {
                addOccupant(piece);
            }
            catch (OccupiedCellException e) {
                e.printStackTrace();
            }
        }

        public boolean hasOccupant() {
            return this.occupant != null;
        }

        public Piece getOccupant() {
            return this.occupant;
        }

        public void addOccupant(Piece occupant) throws OccupiedCellException {

            if (this.occupant != null) {
                throw new OccupiedCellException(new Throwable());
            }
            this.occupant = occupant;
        }

        public void forceAddOccupant(Piece occupant) {
            if (this.occupant != null) {
                removeOccupant();
            }
            this.occupant = occupant;
        }

        public void removeOccupant() {
            this.occupant = null;
        }

        public boolean isPoint(int rank, int file) {
            return isPoint(new Point(rank, file));
        }

        public boolean isPoint(Point pos) {
            return this.position.equals(pos);
        }

        public List<Integer> nextMoves() {
            return occupant != null ? occupant.getMoves() : null;
        }
    }


    public ChessBoard() {
        // Create image of chess board
        board = new BufferedImage(BOARD_SIZE, BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
        this.cells = new HashMap<>();

        // Loop over columns in board
        for (int file=1; file<=8; file++) {

            // Loop over rows in board
            for (int rank=1; rank<=8; rank++) {

                // Determine whether to color Black or white
                boolean isLightSquare = (file + rank) % 2 != 0;
                int color = isLightSquare? LIGHT_COLOR : DARK_COLOR;

                // index the cell
                cells.put(ChessUtilities.refToNumber(file, rank), new Cell(file, rank));


                Point currentDims = ChessUtilities.refToReal(new Point(file, rank));
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

    public boolean isInBoard(@NotNull Point p) {
        return 0 < p.getX() && p.getX() <= 8 && 0 < p.getY() && p.getY() <= 8;
    }

    private void checkPawnCapture(Piece piece, List<Integer> moves) {
        if (piece instanceof Pawn) {
            Pawn pawn = (Pawn) piece;
            pawn.checkCapture(this, moves);
        }
    }

    private boolean checkKingProtection(Suit suit) {
        int pos = switch(suit) {
            case BLACK -> blackKingPosition;
            case WHITE -> whiteKingPosition;
        };

        return true;
    }

    private void indexPieces() {

        // initialize index if needed
        if (indexOfPieces == null) indexOfPieces = new HashMap<>();

        // loop over cells in chessboard
        for (int index : cells.keySet()) {

            // index piece positions
            if (cells.get(index).hasOccupant()) {
                indexOfPieces.put(index, cells.get(index).getOccupant());
            }
        }
    }

    private void updateIndex(int oldPos, int newPos) {
        if (indexOfPieces == null) {
            indexPieces();
        }
        else {
            Piece piece = indexOfPieces.remove(oldPos);
            indexOfPieces.put(newPos, piece);
        }
    }

    public boolean moveTo(Suit TURN, Point dest, @NotNull Piece piece) throws IllegalMoveException {

        // check that it's player's turn to move
        if (TURN == piece.getSuit()) {

            // calculate steps
            int steps = ChessUtilities.numSteps(piece.getPosition(), dest);

            // get moves associated with piece
            List<Integer> moves = piece.getMoves();
            checkPawnCapture(piece, moves);
            if (!ChessUtilities.GLIDE_STATUS.get(piece.getID()) &&
                    moves.contains(steps)) {

                // if attempted movement is valid, make move
                move(piece, steps);
                return true;
            }
            else if (ChessUtilities.GLIDE_STATUS.get(piece.getID())) {
                for (int dir : piece.getMoves()) {
                    if (steps % dir == 0) {

                        // if attempted move is valid, make move
                        move(piece, steps);
                        return true;
                    }
                }
            }
            else {
                System.out.println("steps: " + steps + "was found to be invalid.");
                IllegalMoveException e = new IllegalMoveException(new Throwable());
                e.printStackTrace();
            }
        }
        else {
            Exception e = new InvalidTurnException(new Throwable());
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void move(Piece p, int steps) throws IllegalMoveException {
        int current = ChessUtilities.refToNumber(p.getX(), p.getY());
        int target = current + steps;
        Point targetPoint;
        try {
            targetPoint = ChessUtilities.numberToRef(target);

            System.out.println("target: " + targetPoint);
            if (isInBoard(targetPoint)) {
                removePiece(p);
                p.moveTo(targetPoint);
                addPiece(p);
                updateIndex(current, target);
            }
            else {
                System.out.println("steps : " + steps);
                Exception e = new IllegalMoveException(new Throwable());
                e.printStackTrace();
            }

        }
        catch (InvalidCellException e) {
            e.printStackTrace();
            throw new IllegalMoveException(e);
        }

    }

    public Iterable<Piece> iter() {
        if (indexOfPieces == null) {
            indexPieces();
        }
        return indexOfPieces.values();

        /*
        List<Piece> all = new ArrayList<>();
        for (Cell cell : this.cells.values()) {
            if (cell.hasOccupant()) {
                all.add(cell.getOccupant());
            }
        }
        return all;
         */
    }

    public void addPiece(@NotNull Piece piece) {
        double file = piece.getPosition().getX();
        double rank = piece.getPosition().getY();

        Cell targetCell;
        if ( (targetCell = this.getCell(file, rank)) == null) {
            return;
        }

        try {
            targetCell.addOccupant(piece);
        }
        catch (OccupiedCellException e) {
            e.printStackTrace();
        }
    }

    public Cell getCell(@NotNull Point p) {
        return getCell(p.getX(), p.getY());
    }

    private Cell getCell(@NotNull Piece p) {
        return getCell(p.getX(), p.getY());
    }
    public Cell getCell(int ref) {
        return cells.getOrDefault(ref, null);
    }

    private Cell getCell(double file, double rank) {
        int reference = ChessUtilities.refToNumber((int) file, (int) rank);
        return getCell(reference);
    }

    private void removePiece(Piece p) {
        Cell c = getCell(p);
        c.occupant = null;
    }

    /**
     * Method to draw board.
     * @param g Graphics window
     */
    public void draw(@NotNull Graphics g) {
        g.drawImage(board, 0, 0, null);

        for (Piece piece : iter()) {
            piece.draw(g);
        }
    }
}
