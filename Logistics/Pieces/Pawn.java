package Pieces;

import ChessLib.ChessUtilities;
import ChessLib.ChessBoard;
import ChessLib.ChessBoard.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Pawn extends AbstractPiece {
    private boolean enPassant = false;
    private boolean starting = true;

    public Pawn(Suit suit, Point position) {
        super(ID.PAWN, suit, position);
    }

    public Pawn (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/P-white.png";
        }
        else {
            return "./img/P-black.png";
        }
    }

    public void checkCapture(ChessBoard board, List<Integer> moves) {
        int ref = ChessUtilities.refToNumber((int) position.getX(), (int) position.getY());

        int multiplier = (suit == Suit.WHITE) ? 1 : -1;
        int a = ref + multiplier * 7;
        int b = ref + multiplier * 9;
        Cell toCheck1 = board.getCell(a);
        Cell toCheck2 = board.getCell(b);
        if (toCheck1.hasOccupant()) {
            if (toCheck1.getOccupant().getSuit() != this.suit) {
                moves.add(multiplier * 7);
            }
        }
        if (toCheck2.hasOccupant()) {
            if (toCheck2.getOccupant().getSuit() != this.suit) {
                moves.add(multiplier * 9);
            }
        }

    }

    @Override
    public List<Integer> getMoves() {
        switch (this.suit) {
            case WHITE -> {
                List<Integer> moves = new ArrayList<>();
                moves.add(ChessUtilities.ALL_MOVES.get(this.identity).get(0));
                if (starting) {
                    moves.add(2 * moves.get(0));
                }
                return moves;
            }
            case BLACK -> {
                List<Integer> rev = new ArrayList<>();
                rev.add(-1 * ChessUtilities.ALL_MOVES.get(this.identity).get(0));
                if (starting) {
                    rev.add(2 * rev.get(0));
                }
                return rev;
            }
        }
        return ChessUtilities.ALL_MOVES.get(this.identity);
    }

    @Override
    public void moveTo(int row, int rank) {
        super.moveTo(row, rank);
        starting = false;
    }

    @Override
    public void moveTo (Point p) {
        super.moveTo(p);
        starting = false;
    }
}
