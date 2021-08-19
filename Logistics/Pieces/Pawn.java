package Pieces;

import java.awt.*;

public class Pawn extends AbstractPiece {

    public Pawn(Suit suit, Point position) {
        super(ID.PAWN, suit, position);
    }

    public Pawn (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/P-white.png";
        }
        else {
            return "./img/P-black.png";
        }
    }
}
