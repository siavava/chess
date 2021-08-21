package Pieces;

import java.awt.*;
import java.util.List;

/**
 * Interface for Chess Pieces
 */
public interface Piece {

    int getX();

    int getY();

    void moveTo(int row, int rank);

    void moveTo(Point p);

    /** BLACK or WHITE */
    enum Suit {
        WHITE,
        BLACK
    }

    enum ID {
        KING('K'),
        QUEEN('Q'),
        ROOK('R'),
        BISHOP('B'),
        KNIGHT('N'),
        PAWN('P');

        char name;

        ID(char c) {
            name = c;
        }

        public String toString() {
            return String.valueOf(name);
        }
    }

    Point getPosition();


    /**
     * Get ID of ChessPieces.Piece
     * @return String ID
     */
    ID getID();

    Suit getSuit();

    List<Integer> getMoves();

    /**
     * Draw a ChessPieces.Piece
     * @param g Graphics instance
     */
    void draw(Graphics g);

}
