package main.java.app.view;

import main.java.app.model.Model3D;
import main.java.app.model.SceneModel;
import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainPanel extends JPanel {
    private SceneModel sceneModel;
    private boolean showGrid = true;
    private boolean showInfo = true;
    private boolean showAxes = true;

    private float rotationY = 0.0f;
    private float rotationX = 0.0f;
    private Point lastMousePoint;

    public MainPanel(SceneModel sceneModel) {
        this.sceneModel = sceneModel;
        setBackground(ThemeManager.getBackgroundColor());
        setDoubleBuffered(true);

        // Обработка мыши для ручного вращения
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastMousePoint != null && SwingUtilities.isLeftMouseButton(e)) {
                    int dx = e.getX() - lastMousePoint.x;
                    int dy = e.getY() - lastMousePoint.y;

                    rotationY += dx * 0.01f;
                    rotationX += dy * 0.01f;

                    lastMousePoint = e.getPoint();
                    repaint();
                }
            }
        });

        // Двойной клик - сброс вращения
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    resetRotation();
                }
            }
        });
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

        // Оси
        if (showAxes) {
            drawAxes(g2d);
        }

        // Модели
        drawModels(g2d);

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
    }

    private void drawAxes(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int length = 100;

        // Ось X (красная)
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(centerX, centerY, centerX + length, centerY);
        g2d.fillPolygon(
                new int[]{centerX + length + 5, centerX + length, centerX + length},
                new int[]{centerY, centerY - 5, centerY + 5},
                3
        );
        g2d.drawString("X", centerX + length + 10, centerY + 5);

        // Ось Y (зеленая)
        g2d.setColor(Color.GREEN);
        g2d.drawLine(centerX, centerY, centerX, centerY - length);
        g2d.fillPolygon(
                new int[]{centerX, centerX - 5, centerX + 5},
                new int[]{centerY - length - 5, centerY - length, centerY - length},
                3
        );
        g2d.drawString("Y", centerX - 10, centerY - length - 10);

        // Ось Z (синяя) - проекция
        g2d.setColor(Color.BLUE);
        int zX = (int)(centerX + length * Math.cos(Math.PI/4));
        int zY = (int)(centerY + length * Math.sin(Math.PI/4));
        g2d.drawLine(centerX, centerY, zX, zY);
        g2d.fillPolygon(
                new int[]{zX + 3, zX, zX},
                new int[]{zY + 3, zY - 3, zY + 3},
                3
        );
        g2d.drawString("Z", zX + 5, zY + 5);

        g2d.setStroke(new BasicStroke(1));
    }

    private void drawModels(Graphics2D g2d) {
        List<Model3D> models = sceneModel.getModels();
        if (models.isEmpty()) {
            return;
        }

        Model3D activeModel = sceneModel.getActiveModel();

        // Если только одна модель - рисуем ее на весь экран
        if (models.size() == 1) {
            Model3D model = models.get(0);
            boolean isActive = (model == activeModel);

            // Рамка для активной модели
            if (isActive) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(20, 20, getWidth() - 40, getHeight() - 40);
                g2d.setStroke(new BasicStroke(1));
            }

            // Рендерим модель
            SimpleRenderer.renderModel(g2d, model,
                    20, 20, getWidth() - 40, getHeight() - 40,
                    1.0f, rotationX, rotationY, isActive);

            // Информация о вращении
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            String rotateInfo = String.format("Вращение: X=%.1f° Y=%.1f° | Перетащите мышью для вращения | Двойной клик - сброс",
                    rotationX * 180 / Math.PI, rotationY * 180 / Math.PI);
            g2d.drawString(rotateInfo, 30, getHeight() - 30);

        } else {
            // Несколько моделей - рисуем превьюшки
            int cols = 2;
            int panelWidth = (getWidth() - 60) / cols;
            int panelHeight = 200;
            int spacing = 20;

            for (int i = 0; i < models.size(); i++) {
                Model3D model = models.get(i);
                boolean isActive = (model == activeModel);

                int col = i % cols;
                int row = i / cols;
                int x = 20 + col * (panelWidth + spacing);
                int y = 20 + row * (panelHeight + spacing);

                // Панель для модели
                g2d.setColor(ThemeManager.getPanelColor());
                g2d.fillRoundRect(x, y, panelWidth, panelHeight, 10, 10);

                // Рамка для активной
                g2d.setColor(isActive ? Color.YELLOW : ThemeManager.getBorderColor());
                g2d.setStroke(new BasicStroke(isActive ? 2 : 1));
                g2d.drawRoundRect(x, y, panelWidth, panelHeight, 10, 10);

                // Название модели
                g2d.setColor(ThemeManager.getTextColor());
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                String name = model.getName() + (model.isModified() ? " *" : "");
                g2d.drawString(name, x + 15, y + 25);

                // Информация
                g2d.setFont(new Font("Arial", Font.PLAIN, 11));
                String info = String.format("Вершин: %d, Полигонов: %d",
                        model.getVertexCount(), model.getFaceCount());
                g2d.drawString(info, x + 15, y + 45);

                // Рендерим маленькую модель
                SimpleRenderer.renderModel(g2d, model,
                        x + 15, y + 60,
                        panelWidth - 30, panelHeight - 80,
                        0.5f, rotationY, rotationX, isActive);
            }
        }
    }





    // ============ Методы управления ============

    public void setGridVisible(boolean visible) {
        this.showGrid = visible;
        repaint();
    }

    public void setInfoVisible(boolean visible) {
        this.showInfo = visible;
        repaint();
    }

    public void setAxesVisible(boolean visible) {
        this.showAxes = visible;
        repaint();
    }

    public void resetRotation() {
        rotationX = 0;
        rotationY = 0;
        repaint();
    }

    public void rotateLeft() {
        rotationY -= 0.1f;
        repaint();
    }

    public void rotateRight() {
        rotationY += 0.1f;
        repaint();
    }

    public void rotateUp() {
        rotationX -= 0.1f;
        repaint();
    }

    public void rotateDown() {
        rotationX += 0.1f;
        repaint();
    }
}