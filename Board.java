import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Board {
    private List<Piece> circles;
    private List<Point> piecePositions;
    private JLabel imageLabel1, imageLabel2;
    private JFrame frame;
    private ImageIcon icon1, icon2;
    private JTextField infoField1, infoField2;
    private PositionManager positionManager;

    public Board() {
        circles = new ArrayList<>();
        piecePositions = new ArrayList<>();
        positionManager = new PositionManager();
        infoField1 = new JTextField();
        infoField1.setEditable(false);
        infoField2 = new JTextField();
        infoField2.setEditable(false);
        // Example: Add positions
        positionManager.addPosition(200, 200);
        frame = new JFrame("Spiel");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        icon1 = new ImageIcon("board.png");
        icon2 = new ImageIcon("dice_1.png");

        imageLabel1 = new JLabel(icon1) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Piece circle : circles) {
                    if (circle.isVisible()) {
                        g.setColor(circle.getColor());
                        g.fillOval(circle.getX(), circle.getY(), 50, 50);
                    }
                }
            }
        };
        imageLabel2 = new JLabel(icon2);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                infoField1.setBounds((frame.getWidth() - 300) / 2, 10, 300, 50);
                infoField1.setFont(new Font("Arial", Font.PLAIN, 20));
                infoField1.setHorizontalAlignment(JTextField.CENTER);
                infoField2.setBounds((frame.getWidth() - 300) / 2, 70, 300, 50);
                infoField2.setFont(new Font("Arial", Font.PLAIN, 20));
                infoField2.setHorizontalAlignment(JTextField.CENTER);
                // Position the images
                imageLabel1.setBounds((frame.getWidth() - icon1.getIconWidth()) / 2, (frame.getHeight() - icon1.getIconHeight()) / 2, icon1.getIconWidth(), icon1.getIconHeight());
                imageLabel2.setBounds((3 * frame.getWidth() / 4) - (icon2.getIconWidth() / 2), frame.getHeight() / 2, icon2.getIconWidth(), icon2.getIconHeight());
            }
        });

        imageLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Piece circle : circles) {
                    if (circle.isVisible() && isPieceWithinBounds(e.getX(), e.getY(), circle)) {
                        System.out.println("Clicked on circle number " + circle.getNumber());
                    }
                }
            }
        });

        imageLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Clicked on the second image");
                replaceDiceImage("dice_2.png");
            }
        });

        // Position the images
        imageLabel1.setBounds(0, 0, icon1.getIconWidth(), icon1.getIconHeight());
        imageLabel2.setBounds(frame.getWidth() - icon2.getIconWidth(), frame.getHeight() / 2, icon2.getIconWidth(), icon2.getIconHeight());

        // Add the images to the frame
        frame.add(imageLabel1);
        frame.add(imageLabel2);
        frame.add(infoField1);
        frame.add(infoField2);

        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setVisible(true);
        setInfoFieldText("Info Field 1", 1);
    }

    public void addCircle(int x, int y, Color color) {
        circles.add(new Piece(x, y, color));
        frame.repaint(); // Repaint the frame to reflect the new circle
    }

    public void movePiece(Piece piece, int newX, int newY) {
        piece.move(newX, newY);
        // Redraw the frame to reflect the new position of the circle
        frame.repaint();
    }

    public void replaceDiceImage(String newImagePath) {
        icon2 = new ImageIcon(newImagePath);
        imageLabel2.setIcon(icon2);
        // Redraw the frame to reflect the new image
        frame.repaint();
    }

    private boolean isPieceWithinBounds(int x, int y, Piece circle) {
        int centerX = circle.getX() + 25; // Center of the circle
        int centerY = circle.getY() + 25; // Center of the circle
        // Check if the point is within the circle
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(25, 2);
    }

    public void setInfoFieldText(String text, int fieldNumber) {
        if (fieldNumber == 1) {
            infoField1.setText(text);
        } else if (fieldNumber == 2) {
            infoField2.setText(text);
        }
    }

    public static void main(String args[]) {
        Board board = new Board();
        // Example: Add circles
        board.addCircle(200, 200, Color.RED);
        board.addCircle(300, 300, Color.BLUE);
        board.movePiece(board.circles.get(0), 400, 400);
    }
}
