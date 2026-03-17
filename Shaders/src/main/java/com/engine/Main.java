package com.engine;

import com.engine.Scene.Camera;
import com.engine.Scene.Light;
import com.engine.Scene.Scene;
import com.engine.Scene.Objects.Box;
import com.engine.Scene.Objects.Plane;
import com.engine.Scene.Objects.Sphere;
import org.joml.Vector3f;

public class Main {
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    private final float SPEED = 0.1f;

    Render filename = Render.RAY_MARCHING;

    static void main(String[] args) {
        new Main();
    }

    private Main() {
        Input input = new Input();
        Camera camera = new Camera(new Vector3f(0, 0, 0), input, SPEED);

        Light light = new Light(new Vector3f(0, 0, 0),
                new Vector3f(0.45f, -1f, 1f).normalize());

        Scene scene = new Scene(camera, light);

        scene.objects.add(new Plane(new Vector3f(0, -5f, 0),
                new Vector3f(0.21f, 0.45f, 0.91f)));

        scene.objects.add(new Sphere(new Vector3f(1f, -4.5f, -15f),
                new Vector3f(0.9f, 0.91f, 0.33f), 2f));
        scene.objects.add(new Sphere(new Vector3f(2f, -5f, -12f),
                new Vector3f(1.0f, 0.27f, 0.27f), 3f));

        scene.objects.add(new Box(new Vector3f(-2f, -4f, -15f),
                new Vector3f(0.12f, 1f, 0.51f),
                new Vector3f(2f, 2f, 2f)));


        ShaderProcessing shader = new ShaderProcessing(WIDTH, HEIGHT, scene, input);
        shader.init();
        shader.loadShader(filename);
        shader.run();
    }
}