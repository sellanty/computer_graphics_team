package main.java.app.model;

import java.util.List;

public class Model3D {
    private String name;
    private List<float[]> vertices;
    private List<int[]> faces;
    private boolean visible;
    private String filePath;
    private boolean modified;

    public Model3D(String name) {
        this.name = name;
        this.visible = true;
        this.modified = false;
    }

    // Геттеры и сеттеры
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

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public boolean isModified() { return modified; }
    public void setModified(boolean modified) { this.modified = modified; }

    /**
     * Получить количество вершин
     */
    public int getVertexCount() {
        return vertices != null ? vertices.size() : 0;
    }

    /**
     * Получить количество полигонов
     */
    public int getFaceCount() {
        return faces != null ? faces.size() : 0;
    }

    /**
     * Создать копию модели
     */
    public Model3D copy() {
        Model3D copy = new Model3D(this.name + " (копия)");
        copy.setVertices(this.vertices); // Внимание: поверхностное копирование
        copy.setFaces(this.faces); // Внимание: поверхностное копирование
        copy.setVisible(this.visible);
        return copy;
    }
}