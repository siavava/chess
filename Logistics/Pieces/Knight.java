package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Knight extends AbstractPiece {

    public Knight(Suit suit, Point position) throws IOException {
        super('N', suit, position);

        // Initialize move behavior
        posMoves = Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17);

        // Initialize value of ChessPieces.Piece
        this.value = 3;
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/N-white.png";
        }
        else {
            return "./img/N-black.png";
        }
    }

    public List<Move> getMoves() {
        List<Move> possibleMoves;

        return null;
    }
}
