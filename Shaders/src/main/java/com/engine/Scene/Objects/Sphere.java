package com.engine.Scene.Objects;

import org.joml.Vector3f;

public class Sphere extends Object {
    public float r;

    public Sphere(Vector3f pos, Vector3f color, float r) {
        super(pos, color);
        this.r = r;
    }
}
