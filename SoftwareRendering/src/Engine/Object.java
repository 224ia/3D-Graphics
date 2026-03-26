package Engine;

import Util.Model;
import Util.Polygon;
import Util.RenderPolygon;
import org.joml.*;

import java.awt.*;
import java.lang.Math;
import java.util.List;

public class Object {
    public Vector3f pos;
    public Vector3f rot;
    public Vector3f size;
    public Color color;

    private Matrix4f fullMatrix;

    public List<Util.Polygon> polygons;

    public Object(Vector3f pos, Vector3f rot, Vector3f size, Color color, List<Util.Polygon> polygons) {
        this.pos = pos;
        this.rot = rot;
        this.size = size;
        this.color = color;
        this.polygons = polygons;

        updateMatrix();
    }

    public Object(Vector3f pos, Vector3f rot, Vector3f size, Color color, Model model) {
        this(pos, rot, size, color, model.getPolygons());
    }

    public void rotate(Vector3f angle) {
        rot.add(angle);
        updateMatrix();
    }

    public void updateMatrix() {
        float angleX = (float) Math.toRadians(rot.x);
        float angleY = (float) Math.toRadians(rot.y);
        float angleZ = (float) Math.toRadians(rot.z);

        fullMatrix = new Matrix4f().translate(pos).rotateX(angleX).rotateY(angleY).rotateZ(angleZ).scale(size);
    }

    public void render(Matrix4f view, List<RenderPolygon> renderPolygons) {
        Matrix4f mvp = new Matrix4f(view).mul(fullMatrix);

        for (Polygon polygon : polygons) {
            Vector4f v0 = new Vector4f(polygon.positions[0], 1);
            Vector4f v1 = new Vector4f(polygon.positions[1], 1);
            Vector4f v2 = new Vector4f(polygon.positions[2], 1);

            v0.mul(mvp);
            v1.mul(mvp);
            v2.mul(mvp);

            Vector4f normal = new Vector4f(polygon.normal, 0);
            normal.mul(mvp);
            normal.normalize();

            Vector4f diff = new Vector4f(v0).add(v1).add(v2).div(3).negate();
            if (diff.dot(normal) > 0) {
                float depth = (v0.z + v1.z + v2.z) / 3f;
                renderPolygons.add(new RenderPolygon(v0, v1, v2, normal, this.color, depth));
            }
        }
    }
}
