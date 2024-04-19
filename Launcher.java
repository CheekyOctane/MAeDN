import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher {
    private JFrame frame;
    private JButton button1, button2, button3, button4;
    private JLabel label;

    public Launcher() {
        frame = new JFrame("Game Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label = new JLabel("Wähle einen Spielmodus, um das Spiel zu starten.", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add top and bottom margins
        frame.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 30, 30)); // Increase the gap between buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add margins around the button panel
        button1 = createButton("1 Spieler", "1");
        button2 = createButton("2 Spieler", "2");
        button3 = createButton("3 Spieler", "3");
        button4 = createButton("4 Spieler (keine KI)", "4");

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(800, 600)); // Set a preferred size for the frame
        frame.pack();
        frame.setVisible(true);
    }

    private JButton createButton(String value, String attribute) {
        JButton button = new JButton(value);
        button.putClientProperty("attribute", attribute); // Add invisible attribute
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close the launcher window
                new Board((String) button.getClientProperty("attribute")); // open the board window and pass the selected value on
            }
        });
        return button;
    }

    public static void main(String[] args) {
        new Launcher();
    }
}