package Engine;

import Util.LoadingModel;
import Util.Projection;
import Util.RenderPolygon;
import Util.Renderer;

import javax.swing.*;
import java.util.List;

public final class Engine {
    private final Renderer renderer;
    private Scene scene;

    public final int WIDTH;
    public final int HEIGHT;

    public final Projection projection;

    private int fov;
    private final int MIN_FOV = 30;
    private final int MAX_FOV = 150;

    public Engine(Renderer renderer, int width, int height, int fov) {
        this.renderer = renderer;

        this.WIDTH = width;
        this.HEIGHT = height;

        this.fov = fov;

        projection = new Projection(width, height, (float) width / height, fov);

        LoadingModel.init();
    }

    public void setScene(float cameraSpeed) {
        if (scene == null && renderer != null) {
            scene = new Scene(renderer, cameraSpeed);
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        Timer timer = new Timer(16, _ -> {
            mouseScroll();
            draw();
        });
        timer.start();
    }

    private void mouseScroll() {
        int scroll = scene.getCamera().mouse.getScroll();
        if ((scroll < 0 && fov > MIN_FOV) || (scroll > 0 && fov < MAX_FOV)) {
            fov += scroll * 3;
            fov = Math.clamp(fov, MIN_FOV, MAX_FOV);
            System.out.println(fov);
            projection.scroll(fov);
        }
    }

    private void draw() {
        if (scene != null) {
            List<RenderPolygon> renderPolygons = scene.setRenderPolygons();
            renderPolygons.sort((a, b) -> Float.compare(b.depth, a.depth));
            renderer.render(renderPolygons, projection);
        }
    }
}
