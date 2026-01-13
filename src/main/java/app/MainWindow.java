package main.java.app;

import main.java.app.view.MainPanel;
import main.java.app.view.ControlPanel;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    private ControlPanel controlPanel;

    public MainWindow() {
        initWindow();
        initComponents();
        layoutComponents();
    }

    private void initWindow() {
        setTitle("3D Viewer - Ваша фамилия");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        setMinimumSize(new Dimension(800, 600));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        mainPanel = new MainPanel();
        controlPanel = new ControlPanel();
    }

    private void layoutComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Панель управления слева
        container.add(controlPanel, BorderLayout.WEST);

        // Основная панель отрисовки по центру
        container.add(mainPanel, BorderLayout.CENTER);

        // Статус бар внизу
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Статус: Готово"));
        container.add(statusPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }
}