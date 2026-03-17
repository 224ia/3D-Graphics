package com.engine.Scene;

import java.util.ArrayList;

public class Scene {
    public final ArrayList<com.engine.Scene.Objects.Object> objects;
    public final Light light;
    public final Camera camera;

    public Scene(Camera camera, Light light) {
        objects = new ArrayList<>();
        this.light = light;
        this.camera = camera;
    }

    public float[] getSceneData() {
        int objectCount = objects.size();
        float[] sceneData = new float[objectCount * 8];
        int index = 0;
        for (com.engine.Scene.Objects.Object object : objects) {
            if (object instanceof com.engine.Scene.Objects.Sphere sphere) {
                sceneData[index++] = 0;
                sceneData[index + 6] = sphere.r;
            } else if (object instanceof com.engine.Scene.Objects.Box box) {
                sceneData[index++] = 1;
                sceneData[index + 6] = box.size.x;
            } else if (object instanceof com.engine.Scene.Objects.Plane) {
                sceneData[index++] = 2;
                sceneData[index + 6] = -1f;
            }
            sceneData[index++] = object.pos.x;
            sceneData[index++] = object.pos.y;
            sceneData[index++] = object.pos.z;

            sceneData[index++] = object.color.x;
            sceneData[index++] = object.color.y;
            sceneData[index++] = object.color.z;
            index++;
        }
        return sceneData;
    }
}
