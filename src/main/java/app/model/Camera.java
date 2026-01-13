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

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float[] getPosition() { return position; }
    public void setPosition(float[] position) { this.position = position; }
    public float[] getTarget() { return target; }
    public void setTarget(float[] target) { this.target = target; }
    public float[] getUp() { return up; }
    public void setUp(float[] up) { this.up = up; }
}