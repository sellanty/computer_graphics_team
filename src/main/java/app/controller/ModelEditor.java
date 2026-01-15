package main.java.app.controller;

import main.java.app.model.Model3D;
import java.util.ArrayList;
import java.util.List;

public class ModelEditor {

    public static boolean deleteVertex(Model3D model, int vertexIndex) {
        if (model == null || model.getVertices() == null) return false;
        if (vertexIndex < 0 || vertexIndex >= model.getVertices().size()) return false;

        // Удаляем вершину
        model.getVertices().remove(vertexIndex);

        // Обновляем полигоны
        if (model.getFaces() != null) {
            List<int[]> newFaces = new ArrayList<>();
            for (int[] face : model.getFaces()) {
                List<Integer> newFace = new ArrayList<>();
                boolean valid = true;

                for (int vIdx : face) {
                    if (vIdx == vertexIndex) {
                        valid = false; // Полигон содержит удаляемую вершину
                        break;
                    }
                    newFace.add(vIdx > vertexIndex ? vIdx - 1 : vIdx);
                }

                if (valid && newFace.size() >= 3) {
                    int[] converted = new int[newFace.size()];
                    for (int i = 0; i < newFace.size(); i++) converted[i] = newFace.get(i);
                    newFaces.add(converted);
                }
            }
            model.setFaces(newFaces);
        }

        model.setModified(true);
        return true;
    }

    public static boolean deletePolygon(Model3D model, int polygonIndex) {
        if (model == null || model.getFaces() == null) return false;
        if (polygonIndex < 0 || polygonIndex >= model.getFaces().size()) return false;

        model.getFaces().remove(polygonIndex);
        model.setModified(true);
        return true;
    }

    public static boolean isValidVertexIndex(Model3D model, int index) {
        return model != null && model.getVertices() != null &&
                index >= 0 && index < model.getVertices().size();
    }

    public static boolean isValidPolygonIndex(Model3D model, int index) {
        return model != null && model.getFaces() != null &&
                index >= 0 && index < model.getFaces().size();
    }
}