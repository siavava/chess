package Pieces;

import java.awt.*;

public final class Bishop extends AbstractPiece {
    public Bishop(Suit suit, Point position) {
        super(ID.BISHOP, suit, position);
    }

    public Bishop (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit.equals(Suit.WHITE)) {
            return "./img/B-white.png";
        }
        else {
            return "./img/B-black.png";
        }
    }
}
