package Software;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends JPanel {
    public final int WIDTH;
    public final int HEIGHT;

    private final JFrame frame;

    private final BufferedImage gameImage;

    public Frame(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        frame = new JFrame("Software Rendering");
        this.setPreferredSize(new Dimension(width, height));
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("Frame was created");

        gameImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateImage(BufferedImage image) {
        Graphics2D g2 = gameImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(gameImage, 0, 0, null);
    }
}
