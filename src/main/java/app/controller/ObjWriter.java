package main.java.app.controller;

import main.java.app.model.Model3D;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter {

    public void write(Model3D model, File file) throws IOException {
        if (model == null) {
            throw new IOException("Модель не определена");
        }

        if (file == null) {
            throw new IOException("Файл не определен");
        }

        if (file.exists() && !file.canWrite()) {
            throw new IOException("Нет прав на запись в файл: " + file.getName());
        }

        if (!file.getName().toLowerCase().endsWith(".obj")) {
            throw new IOException("Файл должен иметь расширение .obj");
        }

        List<float[]> vertices = model.getVertices();
        List<int[]> faces = model.getFaces();

        if (vertices == null || vertices.isEmpty()) {
            throw new IOException("Модель не содержит вершин");
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);

            // Заголовок
            writer.println("# 3D Model exported from 3D Viewer");
            writer.println("# Vertices: " + vertices.size());
            writer.println("# Faces: " + (faces != null ? faces.size() : 0));
            writer.println();

            // Вершины
            for (float[] vertex : vertices) {
                writer.printf("v %.6f %.6f %.6f%n", vertex[0], vertex[1], vertex[2]);
            }

            writer.println();

            // Полигоны
            if (faces != null) {
                for (int[] face : faces) {
                    writer.print("f");
                    for (int vertexIndex : face) {
                        // OBJ индексы начинаются с 1
                        writer.print(" " + (vertexIndex + 1));
                    }
                    writer.println();
                }
            }

            writer.flush();

        } catch (Exception e) {
            throw new IOException("Ошибка при записи файла: " + e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void write(Model3D model, File file, boolean applyTransformations) throws IOException {
        // В этой версии всегда сохраняем исходные координаты
        // В будущем можно добавить преобразование координат
        write(model, file);
    }
}