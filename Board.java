import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> circles;
    private List<Point> piecePositions;
    private JLabel imageLabel1, diceLabel;
    private JFrame frame;
    private ImageIcon icon1, diceIcon;
    private JTextField infoField1, infoField2;
    private PositionManager positionManager;
    private Dice dice;
    private Timer timer;
    private int loops;

    public Board(String gameMode) {
        circles = new ArrayList<>();
        piecePositions = new ArrayList<>();
        positionManager = new PositionManager();
        dice = new Dice();
        loops = 0;
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
        diceIcon = new ImageIcon("dice_1.png");

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
        diceLabel = new JLabel(diceIcon);
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
                diceLabel.setBounds((3 * frame.getWidth() / 4) - (diceIcon.getIconWidth() / 2), frame.getHeight() / 2, diceIcon.getIconWidth(), diceIcon.getIconHeight());
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

        diceLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                animateDice();
            }
        });

        // Position the images
        imageLabel1.setBounds(0, 0, icon1.getIconWidth(), icon1.getIconHeight());
        diceLabel.setBounds(frame.getWidth() - diceIcon.getIconWidth(), frame.getHeight() / 2, diceIcon.getIconWidth(), diceIcon.getIconHeight());

        // Add the images to the frame
        frame.add(imageLabel1);
        frame.add(diceLabel);
        frame.add(infoField1);
        frame.add(infoField2);

        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setVisible(true);
        setInfoFieldText("Info Field 1", 1);

        addCircle(200, 200, Color.RED);
        addCircle(300, 300, Color.BLUE);
        movePiece(circles.get(0), 400, 400);
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

    public void showDice(int eyes) {
        diceIcon = new ImageIcon(getDiceIcon(eyes));
        diceLabel.setIcon(diceIcon);
        // Redraw the frame to reflect the new image
        frame.repaint();
    }

    //generates the dice file path from the amount of eyes on the requested dice
    private static String getDiceIcon(int eyes){
        String iconName = "dice_" + String.valueOf(eyes) + ".PNG";
        return iconName;
    }

    public void animateDice() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dice.roll();
                showDice(dice.eyes);
                loops++;
                if (loops == 10) {
                    timer.stop();
                    loops = 0;
                } else {
                    timer.setDelay(loops * 100);
                }
            }
        });
        timer.start();
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
}
