package Pieces;

import java.awt.*;

public class Knight extends AbstractPiece {

    public Knight(Suit suit, Point position) {
        super(ID.KNIGHT, suit, position);
    }

    public Knight (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/N-white.png";
        }
        else {
            return "./img/N-black.png";
        }
    }
}
