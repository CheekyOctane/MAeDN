import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Board {
    private List<Piece> circles;
    // private List<Point> piecePositions;
    private JLabel boardLabel, diceLabel;
    private JFrame frame;
    private ImageIcon boardIcon, diceIcon;
    private JTextField infoField1, infoField2;
    public PositionManager positionManager;
    public Dice dice;
    private Timer timer;
    private int loops;
    public HashMap<Integer, Integer> piecePositions;
    private Game game;

    public Board(String gameMode) {
        circles = new ArrayList<>();    //adds list for the circles
        piecePositions = new HashMap<Integer, Integer>(); //creates a HashMap that saves the positions of all pieces
        positionManager = new PositionManager();    //adds the positionManager
        dice = new Dice();      //adds a dice
        loops = 0;      //sets the loops for the dice animation to zero
        //adds the two InfoFields and makes them uneditable
        infoField1 = new JTextField();
        infoField1.setEditable(false);
        infoField2 = new JTextField();
        infoField2.setEditable(false);
        frame = new JFrame("Spiel");    //adds the frame for the game
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);      //maximizes the frames size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diceIcon = new ImageIcon("dice_1.png");     //creates a new dice icon and sets it to "dice_1.png"
        diceLabel = new JLabel(diceIcon);       //adds the Label for the dice
        boardIcon = new ImageIcon("board.png");     //creates a new board icon and sets it to "board.png"

        boardLabel = new JLabel(boardIcon) {        //adds label for the board
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
                boardLabel.setBounds((frame.getWidth() - boardIcon.getIconWidth()) / 2, (frame.getHeight() - boardIcon.getIconHeight()) / 2, boardIcon.getIconWidth(), boardIcon.getIconHeight());
                diceLabel.setBounds((3 * frame.getWidth() / 4) - (diceIcon.getIconWidth() / 2), frame.getHeight() / 2, diceIcon.getIconWidth(), diceIcon.getIconHeight());
            }
        });

        boardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Piece circle : circles) {
                    if (circle.isVisible() && isPieceWithinBounds(e.getX(), e.getY(), circle)) {
                        //System.out.println("Clicked on circle number " + circle.getNumber());
                        game.handlePieceInput(circle);
                    }
                }
            }
        });

        diceLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                game.handleDiceInput();
            }
        });

        // Position the images
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());
        diceLabel.setBounds(frame.getWidth() - diceIcon.getIconWidth(), frame.getHeight() / 2, diceIcon.getIconWidth(), diceIcon.getIconHeight());

        // Add the images to the frame
        frame.add(boardLabel);
        frame.add(diceLabel);
        frame.add(infoField1);
        frame.add(infoField2);

        frame.setLayout(null);
        frame.setSize(800, 600);    //sets the frame size
        frame.setVisible(true);     //makes the frame visible
        setInfoFieldText("Red to move", 1);     //sets the Info field text to "Red to move" and therefore initializes the text
        setInfoFieldText("Available moves: 0", 2); //initializes the second Info field text

        //movePiece(circles.get(0), 500, 500);    //test move
        setPath();
        addAllCircle();
    }

    private void setPath() {
        positionManager.addPosition(645, 645);
        positionManager.addPosition(645, 710);
        positionManager.addPosition(710, 710);
        positionManager.addPosition(710, 645);
        positionManager.addPosition(64, 645);
        positionManager.addPosition(64, 710);
        positionManager.addPosition(129, 710);
        positionManager.addPosition(129, 645);
        positionManager.addPosition(64, 64);
        positionManager.addPosition(64, 129);
        positionManager.addPosition(129, 129);
        positionManager.addPosition(129, 64);
        positionManager.addPosition(645, 64);
        positionManager.addPosition(645, 129);
        positionManager.addPosition(710, 129);
        positionManager.addPosition(710, 64);
        positionManager.addPosition(645, 387);
        positionManager.addPosition(581, 387);
        positionManager.addPosition(516, 387);
        positionManager.addPosition(452, 387);
        positionManager.addPosition(387, 645);
        positionManager.addPosition(387, 581);
        positionManager.addPosition(387, 516);
        positionManager.addPosition(387, 452);
        positionManager.addPosition(129, 387);
        positionManager.addPosition(193, 387);
        positionManager.addPosition(257, 387);
        positionManager.addPosition(322, 387);
        positionManager.addPosition(387, 129);
        positionManager.addPosition(387, 193);
        positionManager.addPosition(387, 257);
        positionManager.addPosition(387, 322);
        positionManager.addPosition(710, 452);
        positionManager.addPosition(645, 452);
        positionManager.addPosition(580, 452);
        positionManager.addPosition(515, 452);
        positionManager.addPosition(452, 452);
        positionManager.addPosition(452, 516);
        positionManager.addPosition(452, 581);
        positionManager.addPosition(452, 646);
        positionManager.addPosition(452, 710);
        positionManager.addPosition(387, 710);
        positionManager.addPosition(322, 710);
        positionManager.addPosition(322, 646);
        positionManager.addPosition(322, 581);
        positionManager.addPosition(322, 516);
        positionManager.addPosition(322, 452);
        positionManager.addPosition(257, 452);
        positionManager.addPosition(193, 452);
        positionManager.addPosition(128, 452);
        positionManager.addPosition(64, 452);
        positionManager.addPosition(64, 387);
        positionManager.addPosition(64, 322);
        positionManager.addPosition(129, 322);
        positionManager.addPosition(193, 322);
        positionManager.addPosition(257, 322);
        positionManager.addPosition(322, 322);
        positionManager.addPosition(322, 257);
        positionManager.addPosition(322, 193);
        positionManager.addPosition(322, 129);
        positionManager.addPosition(322, 64);
        positionManager.addPosition(387, 64);
        positionManager.addPosition(452, 64);
        positionManager.addPosition(452, 129);
        positionManager.addPosition(452, 193);
        positionManager.addPosition(452, 258);
        positionManager.addPosition(452, 322);
        positionManager.addPosition(516, 322);
        positionManager.addPosition(581, 322);
        positionManager.addPosition(646, 322);
        positionManager.addPosition(710, 322);
        positionManager.addPosition(710, 387);
    }

    public void addCircle(int x, int y, Color color) {
        circles.add(new Piece(x, y, color));    //creates a new piece with the arguments and adds it to the circles list
        frame.repaint();    // Repaints the frame to reflect the new circle
    }

    public void addAllCircle() {
        Color currentColor = Color.RED;
        for (int i = 1; i < 17; i++) {
            circles.add(new Piece((int)positionManager.getPosition(i).getX(), (int)positionManager.getPosition(i).getY(), currentColor));
            piecePositions.put(circles.get(i-1).getNumber(), i);
            if (i > 3 && i < 8) {
                currentColor = Color.BLUE;
            } else if (i > 7 && i < 12) {
                currentColor = Color.YELLOW;
            } else if (i > 11) {
                currentColor = Color.GREEN;
            }
        } 
        frame.repaint();    // Repaints the frame to reflect the new circle
    }

    public void movePiece(Piece piece, int newX, int newY) {
        piece.move(newX, newY);     //moves the piece to the position set in the arguments of the function
        frame.repaint();    // Redraws the frame to reflect the new position of the circle
    }

    public void showDice(int eyes) {
        diceIcon = new ImageIcon(getDiceIcon(eyes));    //replaces the old dice icon with an icon matching the eyes on it
        diceLabel.setIcon(diceIcon);    //sets the dice label's icon to the new one 
        frame.repaint();    // Redraws the frame to reflect the new image
    }

    //generates the dice file path from the amount of eyes on the requested dice
    private static String getDiceIcon(int eyes){
        return "dice_" + String.valueOf(eyes) + ".png";
    }

    //starts the dice Animation
    public void animateDice(int finalNumber, Runnable onAnimationEnd) { //Runnable is a callback that I can give code to and that I can execute at any time
        if (dice.isInAnimation) {return;}   //checks if isInAnimation is true or false. False --> return is not executed --> code below is executed 
        dice.isInAnimation = true;  //sets the variable isInAnimation to true so animateDice() can not be used again 

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dice.roll();    
                showDice(dice.eyes);
                loops++;    //adds 1 to the loops variable  
                if (loops == 10) {      //checks if the loops = 10, if that's the case
                    timer.stop();       //it stops the timer
                    loops = 0;          //sets the loops to 0
                    dice.isInAnimation = false;     //sets the isInAnimation variable to false so the dice Animation can be started again
                    showDice(finalNumber);  //shows the final dice number
                    onAnimationEnd.run();   //runs code that should be executed after animation
                } else {
                    timer.setDelay(loops * 100);    //makes the delay 0.1 seconds longer after every loop
                }
            }
        });
        timer.start();      //starts the timer
    }



    private boolean isPieceWithinBounds(int x, int y, Piece circle) {
        int centerX = circle.getX() + 25; // Center of the circle
        int centerY = circle.getY() + 25; // Center of the circle
        // Check if the point is within the circle
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(25, 2);
    }

    //sets the info field text 
    public void setInfoFieldText(String text, int fieldNumber) {
        if (fieldNumber == 1) {     
            infoField1.setText(text);
        } else if (fieldNumber == 2) {
            infoField2.setText(text);
        }
    }

    public void setGameObject(Game game) {
        this.game = game;
    }
}
