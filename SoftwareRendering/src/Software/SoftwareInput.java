package Software;

import Util.Input;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class SoftwareInput extends Input implements KeyListener {
    private final boolean[] keys = new boolean[Keys.values().length];

    public SoftwareInput(JFrame frame) {
        frame.addKeyListener(this);
    }

    public boolean isKeyPressed(Keys key) {
        return keys[key.getIndex()];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Keys key : Keys.values()) {
            if (key.getKeyCode() == keyCode) {
                keys[key.getIndex()] = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Keys key : Keys.values()) {
            if (key.getKeyCode() == keyCode) {
                keys[key.getIndex()] = false;
                break;
            }
        }
    }
}
