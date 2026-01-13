package main.java.app.model;

import java.util.List;

public class Model3D {
    private String name;
    private List<float[]> vertices;
    private List<int[]> faces;
    private boolean visible;

    public Model3D(String name) {
        this.name = name;
        this.visible = true;
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<float[]> getVertices() { return vertices; }
    public void setVertices(List<float[]> vertices) { this.vertices = vertices; }
    public List<int[]> getFaces() { return faces; }
    public void setFaces(List<int[]> faces) { this.faces = faces; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
}