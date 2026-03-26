package Util;

import java.util.List;

public abstract class Renderer {
    private final Input input;
    private final MouseProcessing mouse;

    public Renderer(Input input, MouseProcessing mouse) {
        this.input = input;
        this.mouse = mouse;
    }

    public Input getInput() {
        return input;
    }

    public MouseProcessing getMouse() {
        return mouse;
    }

    public abstract void render(List<RenderPolygon> renderPolygons, Projection projection);
}
