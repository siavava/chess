import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class PieceAbstract implements Piece {

    private boolean isActive = false;   // track if piece is active on board
    private final String id;            // Identifier

    protected Suit suit;                // Suit
    protected BufferedImage image;      // Visual representation of Piece
    protected double value;             // value of Piece
    protected ChessGame chessgame;      // current game instance

    public Point position;              // piece position on board
    public List<Integer> posMoves;      // List of possible moves

    /**
     * BLACK or WHITE
     */
    public enum Suit {
        WHITE,
        BLACK
    }

    /**
     * Constructor with name, suit, position
     * @param name K, Q, R, B, N, P
     * @param suit white or black
     * @param position current position square
     * @throws IOException Error loading piece image
     */
    public PieceAbstract(char name, String suit, Point position, ChessGame chessgame) throws IOException {

        // Set Suit
        this.suit = switch (suit) {
            case "black" -> Suit.BLACK;
            default -> Suit.WHITE;
        };

        // Set Point (row and rank)
        this.position = position;

        // Set ID
        id = name +" @ " + this.position.getX() + " , " + this.position.getY();

        // Set image
        this.setImage();

        // Assign to game
        this.chessgame = chessgame;
    }

    /**
     * Method to get ID of Piece
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
            System.err.println(filename);
            throw new IOException();
        }
    }

    public boolean isActive() {
        return this.isActive;
    }

//    public abstract boolean canMove(ChessBoard board, )

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
        assert this.posMoves != null;
        return this.posMoves;
    }

    public String getSuit() {
        return switch (this.suit) {
            case BLACK -> "black";
            case WHITE -> "white";
        };
    }
}
