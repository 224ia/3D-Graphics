package com.engine.Scene;

import org.joml.Vector3f;

public class Light {
    public Vector3f pos;
    public Vector3f dir;

    public Light(Vector3f pos, Vector3f dir) {
        this.pos = pos;
        this.dir = dir;
    }
}
