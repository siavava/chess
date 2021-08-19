package Pieces;
import java.awt.*;

public class Rook extends AbstractPiece {

    public Rook(Suit suit, Point position)  {
        super(ID.ROOK, suit, position);
    }

    public Rook (Suit suit, int file, int rank) {
        this(suit, new Point(file, rank));
    }

    public String getImageFile() {
        if (this.suit == Suit.WHITE) {
            return "./img/R-white.png";
        }
        else {
            return "./img/R-black.png";
        }
    }
}
