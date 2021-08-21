package Pieces;

import java.awt.*;

public final class King extends AbstractPiece {

    public King(Suit suit, Point position) {
        super(ID.KING, suit, position);
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
}
