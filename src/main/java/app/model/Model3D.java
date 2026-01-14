package main.java.app.model;

import java.util.List;

public class Model3D {
    private String name;
    private List<float[]> vertices;
    private List<int[]> faces;
    private boolean modified;

    public Model3D(String name) {
        this.name = name;
        this.modified = false;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        this.modified = true;
    }

    public List<float[]> getVertices() { return vertices; }
    public void setVertices(List<float[]> vertices) {
        this.vertices = vertices;
        this.modified = true;
    }

    public List<int[]> getFaces() { return faces; }
    public void setFaces(List<int[]> faces) {
        this.faces = faces;
        this.modified = true;
    }

    public boolean isModified() { return modified; }
    public void setModified(boolean modified) { this.modified = modified; }

    public int getVertexCount() {
        return vertices != null ? vertices.size() : 0;
    }

    public int getFaceCount() {
        return faces != null ? faces.size() : 0;
    }

    public Model3D copy() {
        Model3D copy = new Model3D(this.name + " (копия)");
        copy.setVertices(this.vertices);
        copy.setFaces(this.faces);
        return copy;
    }
}