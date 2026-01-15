package main.java.app.model;

public class Camera {
    private String name;
    private float[] position;
    private float[] target;
    private float[] up;

    public Camera() {
        this.name = "Камера 1";
        this.position = new float[]{0, 0, 5};
        this.target = new float[]{0, 0, 0};
        this.up = new float[]{0, 1, 0};
    }

    public Camera(String name, float[] position, float[] target, float[] up) {
        this.name = name;
        this.position = position;
        this.target = target;
        this.up = up;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public float[] getTarget() {
        return target;
    }

    public void setTarget(float[] target) {
        this.target = target;
    }

    public float[] getUp() {
        return up;
    }

    public void setUp(float[] up) {
        this.up = up;
    }

    public void move(float dx, float dy, float dz) {
        position[0] += dx;
        position[1] += dy;
        position[2] += dz;
    }

    public void rotate(float angleX, float angleY, float angleZ) {
        // Простая реализация вращения
        // В реальном приложении здесь должна быть матрица вращения
    }

    public void reset() {
        position = new float[]{0, 0, 5};
        target = new float[]{0, 0, 0};
        up = new float[]{0, 1, 0};
    }
}