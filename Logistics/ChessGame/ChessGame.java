package ChessGame;
/* language dependencies */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/* local dependencies */
import ChessErrors.IllegalMoveException;
import ChessErrors.InvalidCellException;
import ChessLib.*;
import Pieces.*;
import Pieces.Piece.Suit;
import ChessLib.ChessBoard.Cell;
import Pieces.Piece.ID;
import org.jetbrains.annotations.NotNull;

public class ChessGame extends JFrame {
    private ChessBoard chessboard;                          // chess board
    public Suit TURN;                                    // Whose turn? Suit.WHITE or Suit.BLACK
    private Map<Suit, Map<ID, Boolean>> enPassant;

    /* Remember captured pieces */
    private Map<Suit, List<Piece>> capturedPieces;
    private Piece selected = null;

    public ChessGame() {
        // Initialize JFrame
        super("Chess Game");

        setUpCanvas();

        setupChessBoard();
    }

    public ChessGame(String state) {
        this();
        ChessUtilities.loadFEN(this, state);
    }

    private void setUpCanvas() {
        // Helper to create Board JComponent
        JComponent board = setupBoard();

        // Put board into window
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);

        // Usual initialization
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void setupChessBoard() {
        // Initialize Pieces and Board
        this.chessboard = new ChessBoard();
        this.TURN = Suit.WHITE;
        for (int trial = 0; trial <= 5; trial++) {
            try {
                initPieces();
                break;
            }
            catch (InvalidCellException e) {
                e.printStackTrace();
                if (trial == 5) {
                    System.exit(-1);
                }
            }
        }
    }

    /**
     * Creates a component to draw into
     */
    private JComponent setupBoard() {
        JComponent board = new JComponent() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };

        int width = ChessBoard.BOARD_SIZE;
        int height = ChessBoard.BOARD_SIZE;

        board.setPreferredSize(new Dimension(width, height));

        board.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent event) {
                handlePress(event.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                handleRelease();
            }

        });

        board.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                handleDrag(event.getPoint());
            }
        });

        return board;
    }

    private void capture (Piece target) {
        Suit capturedSuit = target.getSuit();
        this.capturedPieces.get(capturedSuit).add(target);
    }

    public void activateCastling(char status) {
        if (this.enPassant == null) {
            this.enPassant = new HashMap<>();
        }
        switch (status) {
            case 'K' -> {
                this.enPassant.getOrDefault(Suit.WHITE, new HashMap<>())
                        .put(ID.KING, true);
            }
            case 'Q' -> {
                this.enPassant.getOrDefault(Suit.WHITE, new HashMap<>())
                        .put(ID.QUEEN, true);
            }
            case 'k' -> {
                this.enPassant.getOrDefault(Suit.BLACK, new HashMap<>())
                        .put(ID.KING, true);

            }
            case 'q' -> {
                this.enPassant.getOrDefault(Suit.BLACK, new HashMap<>())
                        .put(ID.QUEEN, true);
            }
        }
    }

    private void handlePress(Point point) {
        Point ref = ChessUtilities.realToRef(point);
        if (this.selected != null) {
            try {
                System.out.println(this.selected);
                System.out.println("source: " + ref);

                chessboard.moveTo(ref, selected);
                capture(selected);
                this.selected = null;
                repaint();
            }
            catch (IllegalMoveException e) {
                this.selected = null;
                e.printStackTrace();
            }
        }
        else {
            Cell cell = chessboard.getCell(ref);
            if (cell.hasOccupant()) {
                this.selected = cell.getOccupant();
            }
        }

    }

    private void handleRelease() {

    }

    private void handleDrag(Point point) {

    }

    /**
     * Given a Point in real coordinates, return the cell of the point
     * @param point point to check
     * @return Point representing x and y of cell (1 to 8)
     */
    private @NotNull Point getCell(@NotNull Point point) {
        double x = point.getX(), y = point.getY();
        double cellSize = (double) ChessBoard.BOARD_SIZE / 8;

        int xCell = (int) (x / cellSize);
        int yCell = (int) (y / cellSize);
        return new Point(xCell, yCell);
    }

    public ChessBoard getChessBoard() {
        return this.chessboard;
    }

    public Iterable<Piece> getPieces() {
        return this.chessboard.iter();
    }

    /**
     * Method to initialize pieces and positions during start of game.
     */
    private void initPieces() throws InvalidCellException {

        // initialize captured pieces
        this.capturedPieces = new HashMap<>();
        capturedPieces.put(Suit.WHITE, new ArrayList<>());
        capturedPieces.put(Suit.BLACK, new ArrayList<>());

        // Initialize pieces
        ChessUtilities.loadStartingFEN(this);

        System.out.println("INITIALIZATION DONE!!");
        for (Piece piece : this.getPieces()) {
            System.out.println(piece);
        }
    }

    public void draw(Graphics g) {

        // First, draw chess board
        chessboard.draw(g);

        // Get each piece and draw it.
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}
