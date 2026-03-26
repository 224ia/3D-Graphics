package Util;

import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class LoadingModel {
    public static final String MODEL_FOLDER_PATH = "src/Models/";

    public static final List<Model> models = new ArrayList<>();

    public static void init() {
        if (models.isEmpty()) {
            File folder = new File(MODEL_FOLDER_PATH);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".obj"));
                if (files != null) {
                    for (File file : files) {
                        String name = file.getName().replace(".obj", "");
                        models.add(new Model(name, loadModel(file.getName())));
                        System.out.println("Loaded model: " + name);
                    }
                }
            }
        }
    }

    public static ArrayList<Polygon> loadModel(String path) {
        ArrayList<Vector3f> vertices = new ArrayList<>();
        ArrayList<Vector3f> normals = new ArrayList<>();
        ArrayList<Polygon> polygons = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(MODEL_FOLDER_PATH + path));
            for (String line : lines) {
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "v" -> vertices.add(new Vector3f(Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    case "vn" -> normals.add(new Vector3f(Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    case "f" -> {
                        Vector3f[] positions = new Vector3f[3];
                        int normalIndex = -1;
                        for (int i = 0; i < 3; i++) {
                            String[] indexes = parts[1 + i].split("/");
                            positions[i] = vertices.get(Integer.parseInt(indexes[0]) - 1);
                            normalIndex = Integer.parseInt(indexes[2]) - 1;
                        }
                        polygons.add(new Polygon(positions, normals.get(normalIndex)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return polygons;
    }

    public static Model getModel(String name) {
        return models.stream()
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
