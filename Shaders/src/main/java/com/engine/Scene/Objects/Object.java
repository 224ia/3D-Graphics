package com.engine.Scene.Objects;

import org.joml.Vector3f;

public abstract class Object {
    public Vector3f pos;
    public Vector3f color;

    public Object(Vector3f pos, Vector3f color) {
        this.pos = pos;
        this.color = color;
    }
}
