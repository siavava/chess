package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Queen extends AbstractPiece {

    public Queen(Suit suit, Point position) {
        super(ID.QUEEN, suit, position);

        // Initialize move behavior
        this.posMoves = Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9);

        // Set value
        this.value = 9;
    }

    public Queen (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/Q-white.png";
        }
        else {
            return "./img/Q-black.png";
        }
    }

    public List<Move> getMoves() {
        Set<Move> possibleMoves;

        return null;
    }
}
