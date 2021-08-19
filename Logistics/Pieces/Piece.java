package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.util.List;

/**
 * Interface for Chess Pieces
 */
public interface Piece {

    int getX();

    int getY();

    void moveTo(int row, int rank);

    /** BLACK or WHITE */
    enum Suit {
        WHITE,
        BLACK
    }

    enum ID {
        KING, QUEEN, ROOK,
        BISHOP, KNIGHT, PAWN
    }

    Point getPosition();


    /**
     * Get ID of ChessPieces.Piece
     * @return String ID
     */
    ID getID();

    Suit getSuit();

    List<Integer> getPosMoves();

    /**
     * Draw a ChessPieces.Piece
     * @param g Graphics instance
     */
    void draw(Graphics g);

}
