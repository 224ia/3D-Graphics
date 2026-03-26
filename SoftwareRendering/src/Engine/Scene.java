package Engine;

import Util.*;
import Util.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Scene {
    private final ArrayList<Object> objects = new ArrayList<>();
    private final Camera camera;

    public Scene(Renderer renderer, float cameraSpeed) {
        camera = new Camera(new Vector3f(0, 0, 0), renderer.getInput(), renderer.getMouse(), cameraSpeed);
    }

    public Camera getCamera() {
        return camera;
    }

    public void addObject(Vector3f position, Vector3f rotation, Vector3f scale, Color color, String modelName) {
        Model model = LoadingModel.getModel(modelName);
        if (model != null) {
            objects.add(new Object(position, rotation, scale, color, model));
        } else {
            System.out.println("No loaded model named: " + modelName);
        }
    }

    public List<RenderPolygon> setRenderPolygons() {
        Matrix4f view = camera.update();
        List<RenderPolygon> renderPolygons = new ArrayList<>();
        for(Object obj : objects) {
            obj.render(view, renderPolygons);
        }

        return renderPolygons;
    }
}
