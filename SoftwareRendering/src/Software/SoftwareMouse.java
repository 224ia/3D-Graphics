package Software;

import Util.MouseProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public final class SoftwareMouse extends MouseProcessing implements MouseMotionListener, MouseWheelListener {
    private final JFrame frame;
    private Robot robot;
    private Point centerPoint;

    private boolean mouseLocked = false;

    public SoftwareMouse(JFrame frame) {
        this.frame = frame;
        frame.addMouseMotionListener(this);
        frame.addMouseWheelListener(this);

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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mouseLocked) {
            int deltaX = e.getX() - centerPoint.x;
            int deltaY = e.getY() - centerPoint.y;

            yaw += deltaX * MouseProcessing.SENSITIVITY;
            pitch -= deltaY * MouseProcessing.SENSITIVITY;

            float maxPitch = (float) (Math.PI / 2 - 0.1);
            pitch = Math.max(-maxPitch, Math.min(maxPitch, pitch));

            centerMouse();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll += e.getWheelRotation();
    }
}
