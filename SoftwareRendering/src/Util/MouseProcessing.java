package Util;

public abstract class MouseProcessing {
    protected float yaw = 0;
    protected float pitch = 0;
    protected int scroll = 0;

    protected static final float SENSITIVITY = 0.005f;

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public int getScroll() {
        int scr = scroll;
        scroll = 0;
        return scr;
    }
}
