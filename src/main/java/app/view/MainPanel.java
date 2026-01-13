package main.java.app.view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setBackground(new Color(30, 30, 35));
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Заглушка для отрисовки
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Временно: рисуем сетку
        drawGrid(g2d);

        // Временно: текст по центру
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String message = "3D Viewer - Панель отрисовки";
        int textWidth = g2d.getFontMetrics().stringWidth(message);
        g2d.drawString(message, getWidth()/2 - textWidth/2, getHeight()/2);
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(60, 60, 70));

        // Вертикальные линии
        for (int x = 0; x < getWidth(); x += 50) {
            g2d.drawLine(x, 0, x, getHeight());
        }

        // Горизонтальные линии
        for (int y = 0; y < getHeight(); y += 50) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }
}