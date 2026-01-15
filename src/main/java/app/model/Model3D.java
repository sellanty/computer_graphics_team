package main.java.app.model;

import java.util.ArrayList;
import java.util.List;

public class Model3D {
    private String name;
    private List<float[]> vertices;
    private List<int[]> faces;
    private boolean modified;
    private String filePath;

    public Model3D(String name) {
        this.name = name;
        this.vertices = new ArrayList<>();
        this.faces = new ArrayList<>();
        this.modified = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
            this.modified = true;
        }
    }

    public List<float[]> getVertices() {
        return vertices;
    }

    public void setVertices(List<float[]> vertices) {
        this.vertices = vertices != null ? vertices : new ArrayList<>();
        this.modified = true;
    }

    public List<int[]> getFaces() {
        return faces;
    }

    public void setFaces(List<int[]> faces) {
        this.faces = faces != null ? faces : new ArrayList<>();
        this.modified = true;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getFaceCount() {
        return faces.size();
    }

    public Model3D copy() {
        Model3D copy = new Model3D(this.name + " (копия)");

        // Глубокое копирование вершин
        List<float[]> copiedVertices = new ArrayList<>();
        for (float[] vertex : this.vertices) {
            copiedVertices.add(new float[]{vertex[0], vertex[1], vertex[2]});
        }
        copy.setVertices(copiedVertices);

        // Глубокое копирование полигонов
        List<int[]> copiedFaces = new ArrayList<>();
        for (int[] face : this.faces) {
            int[] copiedFace = new int[face.length];
            System.arraycopy(face, 0, copiedFace, 0, face.length);
            copiedFaces.add(copiedFace);
        }
        copy.setFaces(copiedFaces);

        copy.setModified(false);
        return copy;
    }

    public String getStats() {
        return String.format("Имя: %s | Вершин: %d | Полигонов: %d | Изменена: %s",
                name, getVertexCount(), getFaceCount(), modified ? "Да" : "Нет");
    }
}