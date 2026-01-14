package main.java.app.controller;

import main.java.app.model.Model3D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjReader {

    public Model3D read(File file) throws IOException {
        // Проверки файла
        if (!file.exists()) {
            throw new IOException("Файл не существует: " + file.getAbsolutePath());
        }

        if (!file.getName().toLowerCase().endsWith(".obj")) {
            throw new IOException("Файл должен иметь расширение .obj");
        }

        if (file.length() == 0) {
            throw new IOException("Файл пуст");
        }

        // Чтение файла
        Scanner scanner = new Scanner(file);
        List<float[]> vertices = new ArrayList<>();
        List<int[]> faces = new ArrayList<>();

        try {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Пропускаем пустые строки и комментарии
                }

                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    continue;
                }

                switch (parts[0]) {
                    case "v": // vertex
                        if (parts.length >= 4) {
                            try {
                                float x = Float.parseFloat(parts[1]);
                                float y = Float.parseFloat(parts[2]);
                                float z = Float.parseFloat(parts[3]);
                                vertices.add(new float[]{x, y, z});
                            } catch (NumberFormatException e) {
                                throw new IOException("Ошибка в строке " + lineNumber +
                                        ": неверный формат вершины: " + line);
                            }
                        }
                        break;

                    case "f": // face
                        try {
                            int[] face = new int[parts.length - 1];
                            for (int i = 1; i < parts.length; i++) {
                                // Берем только номер вершины (игнорируем текстурные координаты и нормали)
                                String vertexPart = parts[i].split("/")[0];
                                face[i-1] = Integer.parseInt(vertexPart) - 1; // OBJ индексы с 1
                            }
                            faces.add(face);
                        } catch (NumberFormatException e) {
                            throw new IOException("Ошибка в строке " + lineNumber +
                                    ": неверный формат полигона: " + line);
                        }
                        break;
                }
            }

            // Проверяем, что файл содержал данные
            if (vertices.isEmpty()) {
                throw new IOException("Файл не содержит вершин");
            }

        } finally {
            scanner.close();
        }

        // Создаем модель
        String fileName = file.getName();
        String modelName = fileName.substring(0, fileName.lastIndexOf('.'));

        Model3D model = new Model3D(modelName);
        model.setVertices(vertices);
        model.setFaces(faces);

        return model;
    }

    public boolean validateObjFile(File file) throws IOException {
        if (!file.exists()) return false;
        if (file.length() == 0) return false;
        if (!file.getName().toLowerCase().endsWith(".obj")) return false;

        // Проверяем, что файл содержит хотя бы одну вершину
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("v ")) {
                    return true;
                }
            }
        } finally {
            scanner.close();
        }

        return false;
    }
}