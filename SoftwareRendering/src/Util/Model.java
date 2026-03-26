package Util;

import java.util.List;

public class Model {
    private final String name;
    private final List<Polygon> polygons;

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public String getName() {
        return name;
    }

    public Model(String name, List<Polygon> polygons) {
        this.name = name;
        this.polygons = polygons;
    }
}
