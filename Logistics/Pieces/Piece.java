package Pieces;

import ChessLib.Move;

import java.awt.*;
import java.util.List;

/**
 * Interface for Chess Pieces
 */
public interface Piece {

    /** BLACK or WHITE */
    enum Suit {
        WHITE,
        BLACK
    }

    /**
     * move a piece
     * @param newMove move to apply!
     */
    void move(Move newMove);

    /**
     * Get ID of ChessPieces.Piece
     * @return String ID
     */
    String getID();

    String getSuit();

    List<Integer> getPosMoves();

    /**
     * Draw a ChessPieces.Piece
     * @param g Graphics instance
     */
    void draw(Graphics g);

}
