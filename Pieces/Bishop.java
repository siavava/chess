import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Bishop extends PieceAbstract {


    public Bishop(String suit, Point position, ChessGame chessgame) throws IOException {
        super('B', suit, position, chessgame);

        // Initialize move behavior
        this.posMoves = Arrays.asList(-9, -7, 7, 9);

        // Set value of piece
        this.value = 3.11;
    }

    public String getImageFile() {
        if (this.suit == PieceAbstract.Suit.WHITE) {
            return "./img/B-white.png";
        }
        else {
            return "./img/B-black.png";
        }
    }

    public List<Move> getMoves() {
        List<Move> possibleMoves;

        return null;
    }
}
