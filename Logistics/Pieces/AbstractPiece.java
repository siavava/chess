package Pieces;

import ChessLib.ChessBoard;
import ChessLib.ChessLib;
import ChessLib.Move;
import org.jetbrains.annotations.NotNull;

//import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPiece implements Piece {

    private boolean isActive = false;   // track if piece is active on board
    private final String id;            // Identifier

    protected Suit suit;                // Suit
    protected BufferedImage image;      // Visual representation of ChessPieces.Piece
    protected double value;             // value of ChessPieces.Piece

    public Point position;              // piece position on board
    public List<Integer> posMoves;      // List of possible moves


    /**
     * Constructor with name, suit, position
     * @param type K, Q, R, B, N, P
     * @param suit white or black
     * @param position current position square
     * @throws IOException Error loading piece image
     */
    public AbstractPiece(char type, Suit suit, Point position) throws IOException {

        // Set Suit
        this.suit = suit;

        // Set Point (row and rank)
        this.position = position;

        // Set ID
        id = type +" @ " + this.position.getX() + " , " + this.position.getY();

        // Set image
        this.setImage();
    }

    /**
     * Method to get ID of ChessPieces.Piece
     * @return String ID
     */
    @Override
    public String getID() {
        return this.id;
    }

    /**
     * Method to move piece
     */
    @Override
    public void move(Move newMove) {

    }

    public void setImage() throws IOException {
        String filename = this.getImageFile();
        try {
            this.image = ImageIO.read(new File((filename)));
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
            System.err.println(filename + " could not be read, or an Error occurred.");
//            e.printStackTrace();
            throw new IOException(e);
        }
    }

    public boolean isActive() {
        return this.isActive;
    }

//    public abstract boolean canMove(ChessLib.ChessBoard board, )

    @Override
    public String toString() {
        return id;
    }

    /**
     * Abstract method implemented in subclasses
     * @return String filename
     */
    public abstract String getImageFile();

    /**
     * Abstract method to get moves, implemented in subclasses
     * @return List of Moves
     */
    public abstract List<Move> getMoves();

    @Override
    public void draw(@NotNull Graphics g) {
        Point currentPos = ChessLib.refToReal(position);
        int px = (int) currentPos.getX() + ChessBoard.STEP/5;
        int py = (int) currentPos.getY() + ChessBoard.STEP/5;
        g.drawImage(this.image, px, py, null, null);

    }

    public List<Integer> getPosMoves() {
        return (posMoves != null) ? posMoves : new LinkedList<>();
    }

    public String getSuit() {
        return switch (this.suit) {
            case BLACK -> "black";
            case WHITE -> "white";
        };
    }
}
