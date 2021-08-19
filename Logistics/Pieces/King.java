package Pieces;

import ChessLib.ChessUtilities;
import ChessLib.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends AbstractPiece {

    public King(Suit suit, Point position) {
        super(ID.KING, suit, position);

        // Initialize move behavior
        this.posMoves = Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9);

        // Set value
        this.value = ChessUtilities.KING_VALUE;
    }

    public King (Suit suit, int file, int rank) {
        this (suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/K-white.png";
        }
        else {
            return "./img/K-black.png";
        }
    }

    public List<Move> getMoves() {
        List<Integer> validMoves = new ArrayList<>();
        int current = ChessUtilities.refToNumber(this.position.x, this.position.y);
        for (int step : posMoves) {
            int next = current + step;
//            if (this.chessboard.get(next))
        }

        return null;
    }
}
