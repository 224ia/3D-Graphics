package Util;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public final class Projection {
    public final float WIDTH;
    public final float HEIGHT;
    public final float ASPECT;

    public Matrix4f PROJ_MATRIX;

    public Projection(float width, float height, float aspect, int fov) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ASPECT = aspect;
        createMatrix(fov);
    }

    public void scroll(int fov) {
        createMatrix(fov);
    }

    private void createMatrix(int fov) {
        float ctg = (float) (1f / Math.tan(Math.toRadians(fov / 2f)));
        this.PROJ_MATRIX = new Matrix4f(ctg / ASPECT, 0, 0, 0,
                0, ctg, 0, 0,
                0, 0, 1, 0,
                0, 0, 1, 0).transpose();
    }

    public Vector2f project(Vector4f v) {
        v.mul(PROJ_MATRIX);
        float divW = 1f / v.w;
        float x = (v.x * divW * 0.5f + 0.5f) * WIDTH;
        float y = (v.y * divW * 0.5f + 0.5f) * HEIGHT;
        return new Vector2f(x, y);
    }
}
