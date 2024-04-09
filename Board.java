import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> circles;
    private List<Point> piecePositions;
    private JLabel imageLabel;

    public Board() {
        circles = new ArrayList<>();
        piecePositions = new ArrayList<>();
        JFrame frame = new JFrame("Spiel");
        ImageIcon icon = new ImageIcon("board.png");
        JLabel label = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) { // Override for a paint method to create custom figures on the board
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                for (Piece circle : circles) {
                    if (circle.isVisible()) {
                        g2d.setColor(circle.getColor().darker());
                        g2d.fillOval(circle.getX(), circle.getY(), 50, 50);

                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawOval(circle.getX(), circle.getY(), 50, 50);
                    }
                }
            }
        };
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) { //Overrides the event and checks if the mouse is clicked on a piece
                for (Piece circle : circles) { //Checks for every circle if it is the one that got clicked (if any)
                    if (circle.isVisible()) {
                        if (event.getX() >= circle.getX() && event.getX() <= circle.getX() + 50 && event.getY() >= circle.getY() && event.getY() <= circle.getY() + 50) {
                            System.out.println("Clicked on circle " + circle.getNumber()); // Can be replaced with any other action
                            replaceImage("dice_2.PNG");
                        }
                    }
                }
            }
        });
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        initializePieces();
        /*addCircle(1250, 880, Color.RED); // Adding a red piece
        addCircle(1300, 880, Color.BLUE); // Adding a blue piece
        addCircle(1350, 880, Color.GREEN); // Adding a green piece */
        moveCircle(circles.get(0), 1000, 500); // Moving the red piece, example
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.RIGHT);
        imageLabel.setVerticalAlignment(JLabel.BOTTOM);
        addImage("dice_2.PNG"); // add an image to the JLabel

        frame.add(imageLabel, BorderLayout.SOUTH);
    }

    private void initializePieces() { //supposed to map all the fields for the pieces and place them on the board
        piecePositions.add(new Point(1250, 880));
        piecePositions.add(new Point(1300, 880));
        addCircle(piecePositions.get(0).x, piecePositions.get(0).y, Color.RED); // Example of how to add a piece using the piecePositions list
        addCircle(piecePositions.get(1).x, piecePositions.get(1).y, Color.BLUE);

    }

    public void addCircle(int x, int y, Color color) {
        circles.add(new Piece(x, y, color));
    }

    public void moveCircle(Piece circle, int newX, int newY) {
        circle.move(newX, newY);
    }

    public void addImage(String imagePath) {
        imageLabel.setIcon(null); // remove the previous icon
        ImageIcon imageIcon = new ImageIcon(imagePath);
        imageLabel.setIcon(imageIcon);
    }

    public void replaceImage(String newImagePath) {
        addImage(newImagePath);
    }

    public static void main(String args[]) {
    Board board = new Board();
    }
}