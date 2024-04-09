import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board {
    public Board() {
        JFrame f = new JFrame("Spiel");
        ImageIcon icon = new ImageIcon("test.png");
        JLabel label = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.RED.darker());
            g2d.fillOval(1250, 880, 50, 50); // Position und Größe des gefüllten Kreises

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2)); // Dicke der Border
            g2d.drawOval(1250, 880, 50, 50); // Position und Größe der Border
            }
        };
    f.add(label);
    f.pack();
    f.setVisible(true);
    f.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
public static void main(String args[])
{
new Board();
}
}