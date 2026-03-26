package Engine;

import Util.Renderer;
import Util.RendererType;
import org.joml.*;

import java.awt.*;

public class Main {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public static void main(String[] args) {
        Renderer renderer = RendererType.SOFTWARE.create(WIDTH, HEIGHT);
        Engine engine = new Engine(renderer, WIDTH, HEIGHT, 110);
        engine.setScene(0.1f);
        Scene scene = engine.getScene();
        // Test Objects
        // Params: position, rotation, size, color, modelName
        scene.addObject(new Vector3f(8, 3, 5), new Vector3f(0, 0, 180), new Vector3f(1, 1, 1), new Color(0.1f, 0.9f, 0.5f), "Player");
        scene.addObject(new Vector3f(-8, 3, 5), new Vector3f(0, 0, 180), new Vector3f(1, 1, 1), new Color(0.9f, 0.9f, 0.9f), "Test");
        scene.addObject(new Vector3f(-8, 3, 15), new Vector3f(0, 90, 180), new Vector3f(1, 1, 1), new Color(0.9f, 0.9f, 0.9f), "Gun");

        engine.start();
    }
}
