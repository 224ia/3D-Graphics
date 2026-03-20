import org.joml.*;

import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;

public class Object {
    public Vector3f pos;
    public Vector3f rot;
    public Vector3f size;
    public Color color;

    private Matrix3f sizeMatrix;
    private Matrix3f rotateMatrix;

    public ArrayList<Polygon> polygons;

    public Object(Vector3f pos, Vector3f rot, Vector3f size, Color color, ArrayList<Polygon> polygons) {
        this.pos = pos;
        this.rot = rot;
        this.size = size;
        this.color = color;
        this.polygons = polygons;

        updateMatrix();
    }

    public Object(Vector3f pos, Vector3f rot, Vector3f size, Color color, String path) {
        this(pos, rot, size, color, LoadingModel.loadModel(path));
    }

    public void rotate(Vector3f angle) {
        rot.add(angle);
        updateMatrix();
    }

    public void updateMatrix() {
        float angleX = (float) Math.toRadians(rot.x);
        float angleY = (float) Math.toRadians(rot.y);
        float angleZ = (float) Math.toRadians(rot.z);

        float sinX = (float) Math.sin(angleX);
        float cosX = (float) Math.cos(angleX);
        float sinY = (float) Math.sin(angleY);
        float cosY = (float) Math.cos(angleY);
        float sinZ = (float) Math.sin(angleZ);
        float cosZ = (float) Math.cos(angleZ);

        Matrix3f rotX = new Matrix3f(
                1, 0, 0,
                0, cosX, -sinX,
                0, sinX, cosX
        );

        Matrix3f rotY = new Matrix3f(
                cosY, 0, sinY,
                0, 1, 0,
                -sinY, 0, cosY
        );

        Matrix3f rotZ = new Matrix3f(
                cosZ, -sinZ, 0,
                sinZ, cosZ, 0,
                0, 0, 1
        );

        rotateMatrix = rotZ.mul(rotY).mul(rotX);

        sizeMatrix = new Matrix3f(size.x, 0, 0,
                0, size.y, 0,
                0, 0, size.z);

    }

    public void render(Graphics2D g2, Matrix3f view, Vector3f cameraPos) {
        for (Polygon polygon : polygons) {
            Vector3f v0 = new Vector3f(polygon.positions[0]);
            Vector3f v1 = new Vector3f(polygon.positions[1]);
            Vector3f v2 = new Vector3f(polygon.positions[2]);

            transformVertic(v0, cameraPos, view);
            transformVertic(v1, cameraPos, view);
            transformVertic(v2, cameraPos, view);

            Vector3f normal = new Vector3f(polygon.normal);
            normal.mul(sizeMatrix);
            normal.mul(rotateMatrix);
            normal.mul(view);
            normal.normalize();

            Vector3f diff = new Vector3f(v0).add(v1).add(v2).div(3);

            if (diff.dot(normal) < 0) {
                drawTriangle(g2, v0, v1, v2, normal);
            }
        }
    }

    private void transformVertic(Vector3f v, Vector3f cameraPos, Matrix3f view) {
        v.mul(sizeMatrix);
        v.mul(rotateMatrix);
        v.add(pos);
        v.sub(cameraPos);
        v.mul(view);
    }

    private void drawTriangle(Graphics2D g2, Vector3f v0, Vector3f v1, Vector3f v2, Vector3f normal) {
        if (v0.z <= 0 || v1.z <= 0 || v2.z <= 0) return;

        Vector2f p0 = project(v0);
        Vector2f p1 = project(v1);
        Vector2f p2 = project(v2);

        Vector3f lightDir = new Vector3f(1, 1, 1).normalize();
        float dot = normal.dot(lightDir.negate()) * 0.5f + 0.5f;
        g2.setColor(new Color(color.getRed() / 255f * dot,
                color.getGreen() / 255f * dot,
                color.getBlue() / 255f * dot));

        g2.fillPolygon(new int[]{(int) p0.x, (int) p1.x, (int) p2.x},
                new int[]{(int) p0.y, (int) p1.y, (int) p2.y}, 3);
    }

    private Vector2f project(Vector3f v) {
        float x = v.x * Main.D * Main.ASPECT / v.z;
        float y = v.y * Main.D / v.z;
        x += (float) Main.WIDTH / 2;
        y += (float) Main.HEIGHT / 2;
        return new Vector2f(x, y);
    }
}
