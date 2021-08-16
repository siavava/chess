package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Queen extends AbstractPiece {

    public Queen(Suit suit, Point position) throws IOException {
        super('Q', suit, position);

        // Initialize move behavior
        this.posMoves = Arrays.asList(-9, -8, -7, -1, 1, 7, 8, 9);

        // Set value
        this.value = 9;
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
