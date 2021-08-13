package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Rook extends PieceAbstract {

    public Rook(String suit, Point position) throws IOException {
        super('R', suit, position);

        // Initialize move behavior
        posMoves = Arrays.asList(-8, -1, 1, 8);

        // set value
        this.value = 5;
    }

    public String getImageFile() {
        if (this.suit == PieceAbstract.Suit.WHITE) {
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
