
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
import ChessErrors.FailedInitException;
import ChessErrors.InvalidCellException;
import ChessLib.*;
import Pieces.*;
import Pieces.Piece.Suit;
import org.jetbrains.annotations.NotNull;

public class ChessGame extends JFrame {
//    private JComponent board;                             // Chess board
    private ChessBoard chessboard;                          // chess board
    private Map<Suit, Map<Integer, Piece>> pieces;        // Map of suit -> position -> piece
    private final boolean toMove = true;                    // Whose turn? true = white, false = black

    /* Remember captured pieces */
    private Map<Suit, List<Piece>> capturedPieces;

    public ChessGame() {
        // Initialize JFrame
        super("Chess Game");

        // Initialize Pieces and Board
        try {
            initPieces();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.chessboard = new ChessBoard();


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
        // Initialize Pieces and Board
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

        this.chessboard = new ChessBoard();

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

    private void handlePress(Point point) {
        Point cell = getCell(point);

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
    private Point getCell(@NotNull Point point) {
        double x = point.getX(), y = point.getY();
        double cellSize = (double) ChessBoard.BOARD_SIZE / 8;

        int xCell = (int) (x / cellSize);
        int yCell = (int) (y / cellSize);
        return new Point(xCell, yCell);
    }

    /**
     * Method to initialize pieces and positions during start of game.
     */
    private void initPieces() throws InvalidCellException {

        // initialize captured pieces
        this.capturedPieces = new HashMap<>();
        capturedPieces.put(Suit.WHITE, new ArrayList<>());
        capturedPieces.put(Suit.BLACK, new ArrayList<>());

        // Initialize vars
        this.pieces = new HashMap<>();
        Map<Integer, Piece> white = new HashMap<>();
        Map<Integer, Piece> black = new HashMap<>();
        pieces.put(Suit.WHITE, white);
        pieces.put(Suit.BLACK, black);

        // Initialize pieces
        for (int rank=1; rank<=8; rank++) {
            Map<Integer, Piece> currentPieces = null;
            Suit suit;
            if (rank == 1 || rank == 2 || rank == 7 || rank == 8) {
                if (rank <= 2) {
                    suit = Suit.WHITE;
                    currentPieces = white;
                }
                else {
                    suit = Suit.BLACK;
                    currentPieces = black;
                }
                try {
                    for (int row=1; row<=8; row++) {
                        int coordinate = ChessLib.refToInt(row, rank);
                        if (rank == 2 || rank == 7) {
                            currentPieces.put(coordinate, new Pawn(suit, new Point(row, rank)));
                        }
                        else {
                            if (row == 1 || row == 8) {
                                currentPieces.put(coordinate, new Rook(suit, new Point(row, rank)));
                            }
                            if (row == 2 || row == 7) {
                                currentPieces.put(coordinate, new Knight(suit, new Point(row, rank)));
                            }
                            if (row == 3 || row == 6) {
                                currentPieces.put(coordinate, new Bishop(suit, new Point(row, rank)));
                            }
                            if (row == 4) {
                                currentPieces.put(coordinate, new Queen(suit, new Point(row, rank)));
                            }
                            if (row == 5) {
                                currentPieces.put(coordinate, new King(suit, new Point(row, rank)));
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
        System.out.println("INITIALIZATION DONE!!");
        for (Suit suit : pieces.keySet()) {
            for (int square : pieces.get(suit).keySet()) {
                Piece piece = pieces.get(suit).get(square);
                System.out.println(suit + " at: " + square + ": " + piece);
                System.out.println("Square: " + ChessLib.intToRef(square));
            }
        }
    }

    public void draw(Graphics g) {

        // First, draw chess board
        chessboard.draw(g);

        // Get each piece and draw it.
        for (Map<Integer, Piece> suit : pieces.values()) {
            for (Piece piece : suit.values()) {
                System.out.println(piece);
                piece.draw(g);
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}
