package Software;

import Util.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public final class SoftwareRenderer extends Renderer {
    private final Frame frame;
    private final BufferedImage image;
    private final Graphics2D g2;

    private SoftwareRenderer(Frame frame) {
        super(new SoftwareInput(frame.getFrame()),
                new SoftwareMouse(frame.getFrame()));
        this.frame = frame;
        this.image = new BufferedImage(frame.WIDTH, frame.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g2 = image.createGraphics();
    }

    public SoftwareRenderer(int width, int height) {
        this(createFrame(width, height));
    }

    private static Frame createFrame(int width, int height) {
        return new Frame(width, height);
    }

    @Override
    public void render(List<RenderPolygon> renderPolygons, Projection projection) {
        g2.setColor(new Color(147, 147, 147, 255));
        g2.fillRect(0, 0, frame.WIDTH, frame.HEIGHT);

        for (RenderPolygon polygon : renderPolygons) {
            drawTriangle(g2, polygon.v0, polygon.v1, polygon.v2, polygon.normal, polygon.color, projection);
        }

        frame.updateImage(image);
    }

    public void drawTriangle(Graphics2D g2, Vector4f v0, Vector4f v1, Vector4f v2, Vector4f normal, Color color, Projection projection) {
        if (v0.z <= 0 || v1.z <= 0 || v2.z <= 0) return;

        Vector2f p0 = projection.project(v0);
        Vector2f p1 = projection.project(v1);
        Vector2f p2 = projection.project(v2);

        Vector4f lightDir = new Vector4f(1, 1, 1, 0).normalize();
        float dot = normal.dot(lightDir.negate()) * 0.5f + 0.5f;
        g2.setColor(new Color(color.getRed() / 255f * dot,
                color.getGreen() / 255f * dot,
                color.getBlue() / 255f * dot));

        g2.fillPolygon(new int[]{(int) p0.x, (int) p1.x, (int) p2.x},
                new int[]{(int) p0.y, (int) p1.y, (int) p2.y}, 3);
    }
}
