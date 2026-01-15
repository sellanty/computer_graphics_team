package main.java.app.view;

import main.java.app.model.Model3D;
import main.java.app.model.SceneModel;
import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainPanel extends JPanel {
    private SceneModel sceneModel;
    private boolean showGrid = true;
    private boolean showInfo = true;

    public MainPanel(SceneModel sceneModel) {
        this.sceneModel = sceneModel;
        setBackground(ThemeManager.getBackgroundColor());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Включаем сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Фон
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Сетка
        if (showGrid) {
            drawGrid(g2d);
        }

        // Модели
        drawModels(g2d);

        // Информация
        if (showInfo) {
            drawInfo(g2d);
        }
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(ThemeManager.getGridColor());
        g2d.setStroke(new BasicStroke(0.5f));

        int step = 50;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Вертикальные линии
        for (int x = centerX; x < getWidth(); x += step) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int x = centerX; x >= 0; x -= step) {
            g2d.drawLine(x, 0, x, getHeight());
        }

        // Горизонтальные линии
        for (int y = centerY; y < getHeight(); y += step) {
            g2d.drawLine(0, y, getWidth(), y);
        }
        for (int y = centerY; y >= 0; y -= step) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        // Центральные оси
        g2d.setColor(ThemeManager.getTextColor());
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(centerX, 0, centerX, getHeight()); // Y ось
        g2d.drawLine(0, centerY, getWidth(), centerY); // X ось
    }

    private void drawModels(Graphics2D g2d) {
        List<Model3D> models = sceneModel.getModels();
        if (models.isEmpty()) {
            drawNoModelMessage(g2d);
            return;
        }

        Model3D active = sceneModel.getActiveModel();
        int spacing = 20;
        int startY = 50;

        for (int i = 0; i < models.size(); i++) {
            Model3D model = models.get(i);
            boolean isActive = model == active;
            int panelHeight = 120;
            int y = startY + i * (panelHeight + spacing);

            drawModelPreview(g2d, model, 50, y, getWidth() - 100, panelHeight, isActive);
        }
    }

    private void drawModelPreview(Graphics2D g2d, Model3D model, int x, int y, int width, int height, boolean active) {
        // Фон панели модели
        g2d.setColor(ThemeManager.getPanelColor());
        g2d.fillRoundRect(x, y, width, height, 15, 15);

        // Обводка
        g2d.setColor(active ? Color.YELLOW : ThemeManager.getBorderColor());
        g2d.setStroke(new BasicStroke(active ? 3 : 1));
        g2d.drawRoundRect(x, y, width, height, 15, 15);
        g2d.setStroke(new BasicStroke(1));

        // Имя модели
        g2d.setColor(ThemeManager.getTextColor());
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String name = model.getName() + (model.isModified() ? " *" : "");
        g2d.drawString(name, x + 20, y + 30);

        // Информация
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String info = String.format("Вершин: %d | Полигонов: %d | %s",
                model.getVertexCount(),
                model.getFaceCount(),
                active ? "АКТИВНА" : "неактивна");
        g2d.drawString(info, x + 20, y + 50);

        // Простая визуализация модели
        drawModelVisualization(g2d, model, x + width - 150, y + 20, 100, 80, active);
    }

    private void drawModelVisualization(Graphics2D g2d, Model3D model, int x, int y, int width, int height, boolean active) {
        // Фон визуализации
        g2d.setColor(active ? new Color(60, 60, 70) : new Color(50, 50, 60));
        g2d.fillRect(x, y, width, height);

        // Простая сетка внутри
        g2d.setColor(active ? new Color(100, 100, 110) : new Color(80, 80, 90));
        for (int i = 0; i <= 4; i++) {
            int posX = x + i * (width / 4);
            int posY = y + i * (height / 4);
            g2d.drawLine(x, posY, x + width, posY);
            g2d.drawLine(posX, y, posX, y + height);
        }

        // Примерные вершины
        List<float[]> vertices = model.getVertices();
        if (vertices != null && !vertices.isEmpty()) {
            g2d.setColor(ThemeManager.getModelColor(active));
            int maxPoints = Math.min(20, vertices.size());

            for (int i = 0; i < maxPoints; i++) {
                float[] vertex = vertices.get(i);
                int pointX = x + (int)((vertex[0] + 1) * width / 2);
                int pointY = y + (int)((vertex[1] + 1) * height / 2);

                if (pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height) {
                    g2d.fillOval(pointX - 3, pointY - 3, 6, 6);
                }
            }
        }
    }

    private void drawNoModelMessage(Graphics2D g2d) {
        g2d.setColor(ThemeManager.getTextColor());
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        String msg = "3D Viewer";
        int w = g2d.getFontMetrics().stringWidth(msg);
        g2d.drawString(msg, getWidth()/2 - w/2, getHeight()/2 - 50);

        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String subMsg = "Загрузите модель через меню Файл → Открыть";
        int subW = g2d.getFontMetrics().stringWidth(subMsg);
        g2d.drawString(subMsg, getWidth()/2 - subW/2, getHeight()/2);

        String hint = "Или перетащите файл .obj в это окно";
        int hintW = g2d.getFontMetrics().stringWidth(hint);
        g2d.drawString(hint, getWidth()/2 - hintW/2, getHeight()/2 + 30);
    }

    private void drawInfo(Graphics2D g2d) {
        g2d.setColor(ThemeManager.getTextColor());
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        // Статус в верхнем левом углу
        String status = String.format("Моделей: %d | Тема: %s",
                sceneModel.getModels().size(),
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? "Темная" : "Светлая");
        g2d.drawString(status, 10, 20);

        // Информация об активной модели в нижнем правом углу
        Model3D active = sceneModel.getActiveModel();
        if (active != null) {
            String info = String.format("Активная: %s | В: %d | П: %d",
                    active.getName(), active.getVertexCount(), active.getFaceCount());
            int w = g2d.getFontMetrics().stringWidth(info);
            g2d.drawString(info, getWidth() - w - 10, getHeight() - 10);
        }
    }

    public void setGridVisible(boolean visible) {
        this.showGrid = visible;
        repaint();
    }

    public void setInfoVisible(boolean visible) {
        this.showInfo = visible;
        repaint();
    }
}