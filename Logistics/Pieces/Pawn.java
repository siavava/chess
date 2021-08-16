package Pieces;

import ChessLib.ChessLib;
import ChessLib.Move;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pawn extends AbstractPiece {

    public Pawn(Suit suit, Point position) throws IOException {
        super('P', suit, position);

        // Initialize move behavior
        posMoves = Collections.singletonList(8);

        // Set value
        value = 1;
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/P-white.png";
        }
        else {
            return "./img/P-black.png";
        }
    }

    public List<Move> getMoves() {
        List<Integer> validMoves = new ArrayList<>();
        int current = ChessLib.refToInt(this.position.x, this.position.y);
        for (int step : posMoves) {
            int next = current + step;
//            if (this.chessboard.get(next))
        }

        return null;
    }
}
