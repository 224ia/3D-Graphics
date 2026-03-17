package com.engine;

public enum Render {
    RAY_CASTING ("RayCasting"),
    RAY_TRACING ("RayTracing"),
    RAY_MARCHING ("RayMarching");

    private final String filename;
    Render (String filename) {
        this.filename = filename;
    }

    String getFilename() {
        return this.filename + ".fraq";
    }
}
