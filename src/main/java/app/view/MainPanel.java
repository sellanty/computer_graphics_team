package main.java.app.view;

import main.java.app.model.Model3D;
import main.java.app.model.SceneModel;
import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainPanel extends JPanel {
    private SceneModel sceneModel;

    public MainPanel(SceneModel sceneModel) {
        this.sceneModel = sceneModel;
        setBackground(ThemeManager.getBackgroundColor());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Фон
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Сетка
        drawGrid(g2d);

        // Модели
        drawModels(g2d);

        // Инфо
        drawInfo(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(ThemeManager.getGridColor());
        int step = 50;
        for (int x = 0; x < getWidth(); x += step) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += step) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawModels(Graphics2D g2d) {
        List<Model3D> models = sceneModel.getModels();
        if (models.isEmpty()) {
            g2d.setColor(ThemeManager.getTextColor());
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            String msg = "Загрузите модель (Ctrl+O)";
            int w = g2d.getFontMetrics().stringWidth(msg);
            g2d.drawString(msg, getWidth()/2 - w/2, getHeight()/2);
            return;
        }

        Model3D active = sceneModel.getActiveModel();
        int y = 100;

        for (Model3D model : models) {
            boolean isActive = model == active;
            drawModelPreview(g2d, model, 100, y, isActive);
            y += 150;
        }
    }

    private void drawModelPreview(Graphics2D g2d, Model3D model, int x, int y, boolean active) {
        // Прямоугольник модели
        g2d.setColor(ThemeManager.getModelColor(active));
        g2d.fillRect(x, y, 200, 100);

        // Рамка для активной
        if (active) {
            g2d.setColor(ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ?
                    Color.YELLOW : Color.ORANGE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(x, y, 200, 100);
            g2d.setStroke(new BasicStroke(1));
        }

        // Текст
        g2d.setColor(ThemeManager.getTextColor());
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        String name = model.getName() + (model.isModified() ? " *" : "");
        g2d.drawString(name, x + 10, y + 25);

        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String info = String.format("Вершин: %d, Полигонов: %d",
                model.getVertexCount(), model.getFaceCount());
        g2d.drawString(info, x + 10, y + 50);

        // Статус
        String status = active ? "АКТИВНА" : "неактивна";
        g2d.drawString("Статус: " + status, x + 10, y + 75);
    }

    private void drawInfo(Graphics2D g2d) {
        g2d.setColor(ThemeManager.getTextColor());
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String info = String.format("Моделей: %d | Тема: %s",
                sceneModel.getModels().size(),
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? "Тёмная" : "Светлая");
        g2d.drawString(info, 10, 20);
    }
}