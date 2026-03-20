import org.joml.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final float ASPECT = (float) WIDTH / HEIGHT;
    public static final float D = 200f;  // масштаб проекції

    private final BufferedImage gameImage;

    private final ArrayList<Object> objects = new ArrayList<>();

    private final Input input;
    private final MouseProcessing mouse;

    private final Camera camera;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        JFrame frame = new JFrame("Software Rendering");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        input = new Input();
        mouse = new MouseProcessing(frame);
        frame.addKeyListener(input);

        camera = new Camera(new Vector3f(0, 0, 0), input, mouse, 0.1f);

        gameImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // Test Objects
        // Params: position, rotation, size, color, ArrayList of polygons or path to obj file
        objects.add(new Object(new Vector3f(3, 3, 5), new Vector3f(0, 0, 180), new Vector3f(1, 1, 1), new Color(0.9f, 0.9f, 0.9f), "Gun.obj"));
        objects.add(new Object(new Vector3f(8, 3, 5), new Vector3f(0, 0, 180), new Vector3f(1, 1, 1), new Color(0.9f, 0.9f, 0.9f), "Player.obj"));

        loop();
    }

    void loop() {
        Timer timer = new Timer(16, _ -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw();
        g.drawImage(gameImage, 0, 0, null);
    }

    private void draw() {
        Graphics2D g2 = gameImage.createGraphics();
        g2.setColor(new Color(0, 72, 147, 255));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        g2.setStroke(new BasicStroke(1));

        for(Object obj : objects) {
            Matrix3f view = camera.update();
            Vector3f cameraPos = camera.getPos();
            obj.rotate(new Vector3f(1, 0, 1));
            obj.render(g2, view, cameraPos);
        }
        g2.dispose();
    }
}
