package Pieces;

import java.awt.*;

public class Queen extends AbstractPiece {

    public Queen(Suit suit, Point position) {
        super(ID.QUEEN, suit, position);
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
}
