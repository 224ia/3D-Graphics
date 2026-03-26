package Engine;

import Software.Keys;
import Util.Input;
import Util.MouseProcessing;
import org.joml.*;

public final class Camera {
    private final Vector3f pos;

    public final Input input;
    public final MouseProcessing mouse;

    private final Matrix3f dirMat = new Matrix3f();

    public float speed;

    public Camera(Vector3f pos, Input input, MouseProcessing mouse, float speed) {
        this.pos = pos;
        this.input = input;
        this.mouse = mouse;
        this.speed = speed;
    }

    public Matrix4f update() {
        Matrix4f view = new Matrix4f().rotateX(-mouse.getPitch()).rotateY(-mouse.getYaw());

        Vector3f dir = new Vector3f(0, 0, 0);

        if (input.isKeyPressed(Keys.W)) dir.z += 1;
        if (input.isKeyPressed(Keys.S)) dir.z -= 1;
        if (input.isKeyPressed(Keys.A)) dir.x -= 1;
        if (input.isKeyPressed(Keys.D)) dir.x += 1;

        if (dir.length() > 0) dir.normalize();

        view.get3x3(dirMat);
        // For movement, we need rotate(yaw, pitch), so flip the signs
        dirMat.m02 *= -1;
        dirMat.m20 *= -1;
        dir.mul(dirMat);

        pos.add(dir.mul(speed));

        if (input.isKeyPressed(Keys.Q)) pos.y += speed;
        if (input.isKeyPressed(Keys.E)) pos.y -= speed;

        return view.translate(-pos.x, -pos.y, -pos.z);
    }
}
