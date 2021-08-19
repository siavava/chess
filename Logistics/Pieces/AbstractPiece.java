package Pieces;

import ChessLib.ChessBoard;
import ChessLib.ChessUtilities;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractPiece implements Piece {

    private final ID identity;

    protected Suit suit;                // Suit
    protected BufferedImage image;      // Visual representation of ChessPieces.Piece

    public Point position;              // piece position on board


    /**
     * Constructor with name, suit, position
     * @param identity K, Q, R, B, N, P
     * @param suit white or black
     * @param position current position square
     */
    public AbstractPiece(ID identity, Suit suit, Point position) {

        // Set Suit
        this.suit = suit;

        // Set Point (row and rank)
        this.position = position;

        // Set ID
        this.identity = identity;

        // Set image
        try {
            this.setImage();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to get ID of ChessPieces.Piece
     * @return String ID
     */
    @Override
    public ID getID() {
        return this.identity;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public int getValue() {
        return ChessUtilities.ALL_VALUES.get(this.identity);
    }

    public void setImage() throws IOException {
        String filename = this.getImageFile();
        try {
            this.image = ImageIO.read(new File((filename)));
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
            System.err.println(filename + " could not be read, or an Error occurred.");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String toString() {
        return this.getSuit() + " : " + this.getID();
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
    public List<Integer> getMoves() {
        return ChessUtilities.ALL_MOVES.get(this.identity);
    }

    public void moveTo(int row, int rank) {
        this.position = new Point(row, rank);
    }

    @Override
    public void draw(@NotNull Graphics g) {
        Point currentPos = ChessUtilities.refToReal(position);
        int px = (int) currentPos.getX() + ChessBoard.CELL_SIZE /5;
        int py = (int) currentPos.getY() + ChessBoard.CELL_SIZE /5;
        g.drawImage(this.image, px, py, null, null);
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Point getPosition() {
        return this.position;
    }
}
