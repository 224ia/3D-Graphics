package com.engine;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private float yaw = 0.0f;
    private float pitch = 0.0f;

    private double lastMouseX = 400;
    private double lastMouseY = 400;

    private float scroll = 1.0f;

    private final boolean[] keys = new boolean[GLFW_KEY_LAST + 1];

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getScroll() {
        return scroll;
    }

    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    public void setupMouse(long window) {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetScrollCallback(window, (_, _, yoffset) ->
                scroll = (float) Math.clamp(scroll + yoffset / 3.5f, 1f, 4f));

        glfwSetCursorPosCallback(window, (_, xpos, ypos) -> {
            double deltaX = xpos - lastMouseX;
            double deltaY = ypos - lastMouseY;

            yaw += (float) (deltaX * 0.005);
            pitch += (float) (-deltaY * 0.005);

            if (pitch > 1.5f) pitch = 1.5f;
            if (pitch < -1.5f) pitch = -1.5f;

            lastMouseX = xpos;
            lastMouseY = ypos;
        });

        glfwSetKeyCallback(window, (win, key, _, action, _) -> {
            if (key >= 0 && key < keys.length) keys[key] = action != GLFW_RELEASE;

            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(win, true);
            }
        });
    }
}
