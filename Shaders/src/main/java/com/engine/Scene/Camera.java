package com.engine.Scene;

import com.engine.Input;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;

public class Camera {
    private final Vector3f pos;
    private final Input input;

    public float speed;

    public Camera(Vector3f pos, Input input, float speed) {
        this.pos = pos;
        this.input = input;
        this.speed = speed;
    }

    public void update() {
        Vector3f dir = new Vector3f(0, 0, 0);

        if (input.isKeyPressed(GLFW_KEY_W)) dir.add(new Vector3f(0, 0, -1));
        if (input.isKeyPressed(GLFW_KEY_S)) dir.add(new Vector3f(0, 0, 1));

        if (input.isKeyPressed(GLFW_KEY_A)) dir.add(new Vector3f(-1, 0, 0));
        if (input.isKeyPressed(GLFW_KEY_D)) dir.add(new Vector3f(1, 0, 0));

        if (dir.length() > 0) dir.normalize();

        rotateDir(dir);

        pos.add(dir.mul(speed));

        if (input.isKeyPressed(GLFW_KEY_Q)) pos.add(new Vector3f(0, -speed, 0));
        if (input.isKeyPressed(GLFW_KEY_E)) pos.add(new Vector3f(0, speed, 0));
    }

    public Vector3f getPos() {
        return new Vector3f(pos);
    }

    private void rotateDir(Vector3f dir) {
        float sinP = (float) Math.sin(input.getPitch());
        float cosP = (float) Math.cos(input.getPitch());

        float y1 = dir.y;
        float z1 = dir.z;
        dir.y = y1 * cosP - z1 * sinP;
        dir.z = y1 * sinP + z1 * cosP;

        float sinY = (float) Math.sin(input.getYaw());
        float cosY = (float) Math.cos(input.getYaw());

        float x2 = dir.x;
        float z2 = dir.z;
        dir.x = x2 * cosY - z2 * sinY;
        dir.z = x2 * sinY + z2 * cosY;
    }
}
