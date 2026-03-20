import org.joml.Matrix3f;
import org.joml.Vector3f;

public class Camera {
    private final Vector3f pos;
    private final Input input;
    private final MouseProcessing mouse;

    public float speed;

    public Camera(Vector3f pos, Input input, MouseProcessing mouse, float speed) {
        this.pos = pos;
        this.input = input;
        this.mouse = mouse;
        this.speed = speed;
    }

    public Matrix3f update() {
        Vector3f dir = new Vector3f(0, 0, 0);

        if (input.isKeyPressed(Keys.W)) dir.add(new Vector3f(0, 0, 1));
        if (input.isKeyPressed(Keys.S)) dir.add(new Vector3f(0, 0, -1));

        if (input.isKeyPressed(Keys.A)) dir.add(new Vector3f(-1, 0, 0));
        if (input.isKeyPressed(Keys.D)) dir.add(new Vector3f(1, 0, 0));

        if (dir.length() > 0) dir.normalize();

        rotateDir(dir);

        pos.add(dir.mul(speed));

        if (input.isKeyPressed(Keys.Q)) pos.add(new Vector3f(0, speed, 0));
        if (input.isKeyPressed(Keys.E)) pos.add(new Vector3f(0, -speed, 0));

        float sinY = (float) Math.sin(mouse.getYaw());
        float cosY = (float) Math.cos(mouse.getYaw());

        float sinP = (float) Math.sin(mouse.getPitch());
        float cosP = (float) Math.cos(mouse.getPitch());

        Matrix3f view = new Matrix3f(cosY, sinY * sinP, sinY * cosP,
                0, cosP, -sinP,
                -sinY, cosY * sinP, cosY * cosP);
        return view;
    }

    public Vector3f getPos() {
        return new Vector3f(pos);
    }

    private void rotateDir(Vector3f dir) {
        float sinP = (float) Math.sin(mouse.getPitch());
        float cosP = (float) Math.cos(mouse.getPitch());

        float y1 = dir.y;
        float z1 = dir.z;
        dir.y = y1 * cosP - z1 * sinP;
        dir.z = y1 * sinP + z1 * cosP;

        float sinY = (float) Math.sin(-mouse.getYaw());
        float cosY = (float) Math.cos(-mouse.getYaw());

        float x2 = dir.x;
        float z2 = dir.z;
        dir.x = x2 * cosY - z2 * sinY;
        dir.z = x2 * sinY + z2 * cosY;
    }
}
