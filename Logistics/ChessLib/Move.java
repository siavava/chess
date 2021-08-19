package ChessLib;

/* local dependencies */
import ChessErrors.UnknownPieceException;
import ChessGame.ChessGame;
import Pieces.*;
import ChessLib.ChessBoard.Cell;

import java.util.List;

/**
 * Handler for chess moves
 */
public class Move {
    private int steps, dx, dy;
    protected int target;
    protected double reward;
    private final char type;
    protected boolean valid = true;
    ChessGame parent;
    Cell destination;

    public Move (ChessGame parent, Piece currentPiece) throws UnknownPieceException {
        if (currentPiece instanceof Pawn) {
            this.type = 'P';
        }
        else if (currentPiece instanceof King) {
            this.type = 'K';
        }
        else if (currentPiece instanceof Queen) {
            this.type = 'Q';
        }
        else if (currentPiece instanceof Rook) {
            this.type = 'R';
        }
        else if (currentPiece instanceof Bishop) {
            this.type = 'B';
        }
        else if (currentPiece instanceof Knight) {
            this.type = 'N';
        }
        else {
            throw new UnknownPieceException(new Throwable());
        }
    }

    public Move(ChessGame parent, Piece currentPiece, int steps) throws UnknownPieceException {
        this(parent, currentPiece);
        dy = steps % 8;      // rank
        dx = steps / 8;      // row

        int oldX = currentPiece.getX();
        int oldY = currentPiece.getY();


        currentPiece.moveTo(oldX + dx, oldY + dy);
    }


    public void check(int x, int y) {
        List<Integer> posMoves = ChessUtilities.ALL_MOVES.get(this.type);
        boolean glide_status = ChessUtilities.GLIDE_STATUS.get(this.type);

        if (glide_status) {

        }



    }


    public int getMove() {
        return this.target;
    }

    public double getReward() {
        return reward;
    }

    private boolean checkMove() {
        return false;
    }
}