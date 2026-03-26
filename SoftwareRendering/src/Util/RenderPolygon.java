package Util;

import org.joml.Vector4f;

import java.awt.Color;

public class RenderPolygon {
    public Vector4f v0, v1, v2;
    public Vector4f normal;
    public Color color;
    public float depth;

    public RenderPolygon(Vector4f v0, Vector4f v1, Vector4f v2, Vector4f normal, Color color, float depth) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.normal = normal;
        this.color = color;
        this.depth = depth;
    }
}
