package com.engine.Scene.Objects;

import org.joml.Vector3f;

public class Box extends Object {
    public Vector3f size;

    public Box(Vector3f pos, Vector3f color, Vector3f size) {
        super(pos, color);
        this.size = size;
    }
}
