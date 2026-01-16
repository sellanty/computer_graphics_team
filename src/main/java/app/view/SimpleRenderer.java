package main.java.app.view;

import main.java.app.model.Model3D;
import java.awt.*;
import java.util.List;

public class SimpleRenderer {

    // Проекция 3D -> 2D
    public static Point projectPoint(float x, float y, float z,
                                     int width, int height,
                                     float scale, float rotationX, float rotationY) {
        // Вращение вокруг Y
        float cosY = (float) Math.cos(rotationY);
        float sinY = (float) Math.sin(rotationY);

        // Вращение вокруг X
        float cosX = (float) Math.cos(rotationX);
        float sinX = (float) Math.sin(rotationX);

        // Вращение вокруг Y
        float x1 = x * cosY - z * sinY;
        float z1 = x * sinY + z * cosY;

        // Вращение вокруг X
        float y1 = y * cosX - z1 * sinX;
        float z2 = y * sinX + z1 * cosX;

        // Центр экрана
        int centerX = width / 2;
        int centerY = height / 2;

        // Проекция
        int screenX = (int) (centerX + x1 * scale);
        int screenY = (int) (centerY - z2 * scale);

        return new Point(screenX, screenY);
    }

    public static void renderModel(Graphics2D g2d, Model3D model,
                                   int x, int y, int width, int height,
                                   float scale, float rotationX, float rotationY,
                                   boolean active) {

        if (model == null || model.getVertices() == null) return;

        List<float[]> vertices = model.getVertices();
        List<int[]> faces = model.getFaces();

        if (vertices.isEmpty()) return;

        // Сохраняем текущие настройки
        Stroke oldStroke = g2d.getStroke();

        // Находим границы модели
        float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
        float minZ = Float.MAX_VALUE, maxZ = Float.MIN_VALUE;

        for (float[] vertex : vertices) {
            minX = Math.min(minX, vertex[0]);
            maxX = Math.max(maxX, vertex[0]);
            minY = Math.min(minY, vertex[1]);
            maxY = Math.max(maxY, vertex[1]);
            minZ = Math.min(minZ, vertex[2]);
            maxZ = Math.max(maxZ, vertex[2]);
        }

        // Центр модели
        float centerX = (minX + maxX) / 2;
        float centerY = (minY + maxY) / 2;
        float centerZ = (minZ + maxZ) / 2;

        // Масштаб для вписывания в область
        float modelWidth = maxX - minX;
        float modelHeight = maxY - minY;
        float modelDepth = maxZ - minZ;
        float maxSize = Math.max(Math.max(modelWidth, modelHeight), modelDepth);

        if (maxSize == 0) maxSize = 1;
        float autoScale = Math.min(width, height) * 0.7f / maxSize * scale;

        // Простой цвет модели
        Color modelColor = active ?
                new Color(255, 150, 50, 180) :  // Активная - оранжевый
                new Color(50, 120, 255, 180);   // Неактивная - синий

        // Рисуем только полигоны (без вершин-точек)
        if (faces != null && !faces.isEmpty()) {
            for (int[] face : faces) {
                if (face.length < 3) continue;

                // Создаем полигон
                int[] xPoints = new int[face.length];
                int[] yPoints = new int[face.length];

                for (int i = 0; i < face.length; i++) {
                    int vertexIndex = face[i];
                    if (vertexIndex >= 0 && vertexIndex < vertices.size()) {
                        float[] vertex = vertices.get(vertexIndex);
                        // Центрируем модель
                        float centeredX = vertex[0] - centerX;
                        float centeredY = vertex[1] - centerY;
                        float centeredZ = vertex[2] - centerZ;

                        Point p = projectPoint(centeredX, centeredY, centeredZ,
                                width, height, autoScale,
                                rotationX, rotationY);
                        xPoints[i] = p.x + x;
                        yPoints[i] = p.y + y;
                    }
                }

                // Рисуем заполненный полигон (немного прозрачный)
                g2d.setColor(new Color(modelColor.getRed(), modelColor.getGreen(),
                        modelColor.getBlue(), 100));
                g2d.fillPolygon(xPoints, yPoints, face.length);

                // Рисуем контур полигона (полностью непрозрачный)
                g2d.setColor(modelColor);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawPolygon(xPoints, yPoints, face.length);
            }
        }

        // Восстанавливаем настройки
        g2d.setStroke(oldStroke);
    }
}