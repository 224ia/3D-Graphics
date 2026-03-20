import org.joml.Vector3f;

public class Polygon {
    public Vector3f[] positions;
    public Vector3f normal;

    public Polygon(Vector3f[] positions, Vector3f normal) {
        this.positions = positions;
        this.normal = normal;
    }
}
