package main.java.app.view;

import main.java.app.MainWindow;
import main.java.app.model.Model3D;
import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private MainWindow mainWindow;
    private JComboBox<String> modelSelector;
    private JLabel modelInfoLabel;
    private JButton themeToggleButton;

    public ControlPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        applyTheme();

        initThemeSection();
        initFileSection();
        initModelSection();
        initRenderSection();
        add(Box.createVerticalGlue());
    }

    private void applyTheme() {
        setBackground(ThemeManager.getPanelColor());
        if (themeToggleButton != null) {
            updateThemeButton();
        }
    }

    private void initThemeSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor()),
                "Тема"
        ));
        panel.setBackground(ThemeManager.getPanelColor());

        themeToggleButton = new JButton();
        updateThemeButton();

        themeToggleButton.addActionListener(e -> {
            ThemeManager.toggleTheme();
            updateThemeButton();
            applyTheme();
            repaint();
            mainWindow.getMainPanel().repaint();
        });

        panel.add(themeToggleButton);
        add(panel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void updateThemeButton() {
        if (ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK) {
            themeToggleButton.setText("🌙 Тёмная");
            themeToggleButton.setBackground(new Color(60, 60, 70));
            themeToggleButton.setForeground(Color.WHITE);
        } else {
            themeToggleButton.setText("☀️ Светлая");
            themeToggleButton.setBackground(new Color(240, 240, 250));
            themeToggleButton.setForeground(Color.BLACK);
        }
    }

    private void initFileSection() {
        JPanel panel = createStyledPanel("Файл");

        JButton loadBtn = new JButton("Загрузить модель");
        JButton saveBtn = new JButton("Сохранить");
        JButton closeBtn = new JButton("Закрыть");

        loadBtn.addActionListener(e -> mainWindow.openModel());
        saveBtn.addActionListener(e -> mainWindow.saveModel());
        closeBtn.addActionListener(e -> mainWindow.closeModel());

        panel.add(loadBtn);
        panel.add(saveBtn);
        panel.add(closeBtn);
        add(panel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initModelSection() {
        JPanel panel = createStyledPanel("Модели");
        panel.setLayout(new BorderLayout());

        modelSelector = new JComboBox<>();
        modelSelector.addActionListener(e -> {
            int index = modelSelector.getSelectedIndex();
            if (index >= 0) {
                mainWindow.selectModel(index);
                updateModelInfo();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton duplicateBtn = new JButton("Дублировать");
        JButton renameBtn = new JButton("Переименовать");

        duplicateBtn.addActionListener(e -> mainWindow.duplicateModel());
        renameBtn.addActionListener(e -> renameCurrentModel());

        buttonPanel.add(duplicateBtn);
        buttonPanel.add(renameBtn);

        panel.add(new JLabel("Выбор модели:"), BorderLayout.NORTH);
        panel.add(modelSelector, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Панель информации
        JPanel infoPanel = createStyledPanel("Информация");
        infoPanel.setLayout(new BorderLayout());
        modelInfoLabel = new JLabel("Нет модели");
        modelInfoLabel.setForeground(ThemeManager.getTextColor());
        infoPanel.add(modelInfoLabel);
        add(infoPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initRenderSection() {
        JPanel panel = createStyledPanel("Отрисовка");

        JCheckBox wireframe = new JCheckBox("Сетка");
        JCheckBox texture = new JCheckBox("Текстура");
        JCheckBox lighting = new JCheckBox("Освещение");

        // Стилизуем чекбоксы
        wireframe.setForeground(ThemeManager.getTextColor());
        texture.setForeground(ThemeManager.getTextColor());
        lighting.setForeground(ThemeManager.getTextColor());

        panel.add(wireframe);
        panel.add(texture);
        panel.add(lighting);

        add(panel);
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor()),
                title
        ));
        panel.setBackground(ThemeManager.getPanelColor());
        return panel;
    }

    private void renameCurrentModel() {
        Model3D model = mainWindow.getSceneModel().getActiveModel();
        if (model != null) {
            String newName = JOptionPane.showInputDialog(this,
                    "Новое имя:", model.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                model.setName(newName.trim());
                model.setModified(true);
                updateModelList();
            }
        }
    }

    public void updateModelList() {
        modelSelector.removeAllItems();
        for (Model3D model : mainWindow.getSceneModel().getModels()) {
            String name = model.getName();
            if (model.isModified()) name += " *";
            modelSelector.addItem(name);
        }
        updateModelInfo();
    }

    private void updateModelInfo() {
        Model3D model = mainWindow.getSceneModel().getActiveModel();
        if (model != null) {
            String info = String.format(
                    "<html><b>%s</b><br>Вершин: %d<br>Полигонов: %d</html>",
                    model.getName(),
                    model.getVertexCount(),
                    model.getFaceCount()
            );
            modelInfoLabel.setText(info);
            modelInfoLabel.setForeground(ThemeManager.getTextColor());
        } else {
            modelInfoLabel.setText("Нет модели");
            modelInfoLabel.setForeground(ThemeManager.getTextColor());
        }
    }
}