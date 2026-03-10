package org.example;

import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private int shaderProgram;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    private double lastMouseX = 400;
    private double lastMouseY = 400;

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    private final boolean[] keys = new boolean[6];

    private final Vector3f pos = new Vector3f(0, 0, 0);
    private final float SPEED = 0.1f;

    private float scroll = 1.0f;

    static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        initGLFW();
        initOpenGL();
        loadShader();
        setupMouse();
        gameLoop();
        cleanup();
    }

    private void initGLFW() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "Ray Casting", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void initOpenGL() {
        GL.createCapabilities();
        glViewport(0, 0, WIDTH, HEIGHT);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    private void setupMouse() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetScrollCallback(window, (_, _, yoffset) ->
                scroll = (float) Math.clamp(scroll + yoffset / 3.5f, 1f, 4f));

        glfwSetCursorPosCallback(window, (_, xpos, ypos) -> {
            double deltaX = xpos - lastMouseX;
            double deltaY = ypos - lastMouseY;

            yaw += (float) (deltaX * 0.005);
            pitch += (float) (-deltaY * 0.005);

            // Обмежити pitch
            if (pitch > 1.5f) pitch = 1.5f;
            if (pitch < -1.5f) pitch = -1.5f;

            lastMouseX = xpos;
            lastMouseY = ypos;
        });

        glfwSetKeyCallback(window, (win, key, _, action, _) -> {
            int i = switch (key) {
                case GLFW_KEY_W -> 0;
                case GLFW_KEY_S -> 1;
                case GLFW_KEY_A -> 2;
                case GLFW_KEY_D -> 3;
                case GLFW_KEY_Q -> 4;
                case GLFW_KEY_E -> 5;
                default -> -1;
            };
            if (i >= 0) keys[i] = action != GLFW_RELEASE;

            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(win, true);
            }
        });
    }

    private String readShader(String filename) {
        try {
            InputStream is = getClass().getClassLoader()
                    .getResourceAsStream("shaders/" + filename);
            if (is == null) {
                System.err.println("File not found: " + filename);
                return "";
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private int compileShader(String source, int type) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(shader);
            throw new RuntimeException("Shader compilation failed: " + log);
        }
        return shader;
    }

    private void loadShader() {
        String vertexSource = readShader("Vertex.vert");
        String fragmentSource = readShader("RayMarching.fraq");

        if (vertexSource.isEmpty() || fragmentSource.isEmpty()) {
            throw new RuntimeException("Failed to load shaders");
        }

        int vertexShader = compileShader(vertexSource, GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentSource, GL_FRAGMENT_SHADER);

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(shaderProgram);
            throw new RuntimeException("Shader linking failed: " + log);
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private void gameLoop() {
        float[] vertices = {-1f, -1f, 1f, -1f, -1f, 1f, 1f, 1f};

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        double startTime = glfwGetTime();

        while (!glfwWindowShouldClose(window)) {
            double currentTime = glfwGetTime() - startTime;

            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // чорний фон
            glClear(GL_COLOR_BUFFER_BIT);

            glUseProgram(shaderProgram);

            glBindVertexArray(vao);

            int resLoc = glGetUniformLocation(shaderProgram, "iResolution");
            glUniform2f(resLoc, WIDTH, HEIGHT);

            int timeLoc = glGetUniformLocation(shaderProgram, "iTime");
            glUniform1f(timeLoc, (float) currentTime);

            Vector3f dir = new Vector3f(0, 0, 0);
            if (keys[0]) dir.add(new Vector3f(0, 0, -1));
            else if (keys[1]) dir.add(new Vector3f(0, 0, 1));

            if (keys[2]) dir.add(new Vector3f(-1, 0, 0));
            else if (keys[3]) dir.add(new Vector3f(1, 0, 0));

            if (dir.length() > 0) dir.normalize();

            float sin = (float) Math.sin(pitch);
            float cos = (float) Math.cos(pitch);
            dir = new Vector3f(dir.x, dir.y * cos - dir.z * sin, dir.y * sin + dir.z * cos);
            sin = (float) Math.sin(yaw);
            cos = (float) Math.cos(yaw);
            dir = new Vector3f(dir.x * cos - dir.z * sin,  dir.y, dir.x * sin + dir.z * cos);
            pos.add(dir.mul(SPEED));

            if (keys[4]) pos.add(new Vector3f(0, -SPEED, 0));
            else if (keys[5]) pos.add(new Vector3f(0, SPEED, 0));

            int posLoc = glGetUniformLocation(shaderProgram, "pos");
            glUniform3f(posLoc, pos.x, pos.y, pos.z);

            int yawLoc = glGetUniformLocation(shaderProgram, "uYaw");
            glUniform1f(yawLoc, yaw);

            int pitchLoc = glGetUniformLocation(shaderProgram, "uPitch");
            glUniform1f(pitchLoc, pitch);

            int scrollLoc = glGetUniformLocation(shaderProgram, "uScroll");
            glUniform1f(scrollLoc, scroll);

            glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}