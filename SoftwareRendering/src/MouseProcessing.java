import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class MouseProcessing implements MouseMotionListener {
    private float yaw = 0;
    private float pitch = 0;

    private static final float SENSITIVITY = 0.005f;

    private boolean mouseLocked = false;

    private final JFrame frame;
    private Robot robot;
    private Point centerPoint;

    public MouseProcessing(JFrame frame) {
        this.frame = frame;
        frame.addMouseMotionListener(this);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    toggleMouseLock();
                }
            }
        });

        centerMouse();
    }

    public void toggleMouseLock() {
        mouseLocked = !mouseLocked;
        if (mouseLocked) {
            centerMouse();
            setCursorHidden(true);
        } else {
            setCursorHidden(false);
        }
    }

    private void setCursorHidden(boolean hidden) {
        frame.setCursor(hidden ?
                frame.getToolkit().createCustomCursor(
                        new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0), "invisible") :
                Cursor.getDefaultCursor()
        );
    }

    private void centerMouse() {
        centerPoint = new Point(
                frame.getX() + frame.getWidth() / 2,
                frame.getY() + frame.getHeight() / 2
        );
        if (robot != null) {
            robot.mouseMove(centerPoint.x, centerPoint.y);
        }
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mouseLocked) {
            int deltaX = e.getX() - centerPoint.x;
            int deltaY = e.getY() - centerPoint.y;

            yaw += deltaX * SENSITIVITY;
            pitch -= deltaY * SENSITIVITY;

            float maxPitch = (float) (Math.PI / 2 - 0.1);
            pitch = Math.max(-maxPitch, Math.min(maxPitch, pitch));

            centerMouse();
        }
    }
}
