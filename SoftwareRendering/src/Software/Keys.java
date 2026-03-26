package Software;

import java.awt.event.KeyEvent;

public enum Keys {
    W(KeyEvent.VK_W, 0),
    S(KeyEvent.VK_S, 1),
    A(KeyEvent.VK_A, 2),
    D(KeyEvent.VK_D, 3),
    Q(KeyEvent.VK_Q, 4),
    E(KeyEvent.VK_E, 5);

    private final int keyCode;
    private final int index;

    Keys(int keyCode, int index) {
        this.keyCode = keyCode;
        this.index = index;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getIndex() {
        return index;
    }
}