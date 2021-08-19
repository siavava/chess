package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Rook extends AbstractPiece {

    public Rook(Suit suit, Point position)  {
        super(ID.ROOK, suit, position);

        // Initialize move behavior
        posMoves = Arrays.asList(-8, -1, 1, 8);

        // set value
        this.value = 5;
    }

    public Rook (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/R-white.png";
        }
        else {
            return "./img/R-black.png";
        }
    }

    public List<Move> getMoves() {
        List<Move> possibleMoves;

        return null;
    }
}
