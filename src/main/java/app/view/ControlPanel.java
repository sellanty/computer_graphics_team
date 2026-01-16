package main.java.app.view;

import main.java.app.MainWindow;
import main.java.app.controller.ModelEditor;
import main.java.app.model.Model3D;
import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private MainWindow mainWindow;
    private JComboBox<String> modelSelector;
    private JLabel modelInfoLabel;
    private JSpinner vertexSpinner;
    private JSpinner polygonSpinner;
    private JButton themeToggleBtn;

    // Базовые чекбоксы (убрали infoCheckbox)
    private JCheckBox gridCheckbox;
    private JCheckBox axesCheckbox;

    public ControlPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 700));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initFileSection();
        initModelSection();
        initEditSection();
        initRenderSection();
        initThemeSection();
        add(Box.createVerticalGlue());

        applyTheme();
    }

    private void initFileSection() {
        JPanel panel = createStyledPanel("Файл");
        panel.setLayout(new GridLayout(0, 1, 5, 5));

        JButton loadBtn = createStyledButton("Загрузить модель");
        JButton saveBtn = createStyledButton("Сохранить");
        JButton saveAsBtn = createStyledButton("Сохранить как...");
        JButton closeBtn = createStyledButton("Закрыть модель");

        loadBtn.addActionListener(e -> mainWindow.openModel());
        saveBtn.addActionListener(e -> mainWindow.saveModel());
        saveAsBtn.addActionListener(e -> mainWindow.saveModelAs());
        closeBtn.addActionListener(e -> mainWindow.closeModel());

        panel.add(loadBtn);
        panel.add(saveBtn);
        panel.add(saveAsBtn);
        panel.add(closeBtn);

        add(panel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initModelSection() {
        JPanel panel = createStyledPanel("Модели");

        modelSelector = new JComboBox<>();
        modelSelector.addActionListener(e -> {
            int index = modelSelector.getSelectedIndex();
            if (index >= 0) mainWindow.selectModel(index);
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        JButton renameBtn = createStyledButton("Переименовать");
        renameBtn.addActionListener(e -> renameModel());
        buttonPanel.add(renameBtn);

        panel.add(new JLabel("Модель:"), BorderLayout.NORTH);
        panel.add(modelSelector, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Информация
        JPanel infoPanel = createStyledPanel("Информация");
        modelInfoLabel = new JLabel("Нет модели", SwingConstants.CENTER);
        modelInfoLabel.setVerticalAlignment(SwingConstants.TOP);
        infoPanel.add(modelInfoLabel);

        add(infoPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initEditSection() {
        JPanel panel = createStyledPanel("Редактирование");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Удаление вершины
        JPanel vertexPanel = new JPanel(new BorderLayout(5, 0));
        vertexPanel.setOpaque(false);

        JLabel vertexLabel = new JLabel("Вершина:");
        vertexPanel.add(vertexLabel, BorderLayout.WEST);

        JPanel vertexControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        vertexControlPanel.setOpaque(false);

        vertexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        vertexSpinner.setPreferredSize(new Dimension(60, 25));
        vertexControlPanel.add(vertexSpinner);

        JButton deleteVertexBtn = new JButton("Удалить");
        deleteVertexBtn.setBackground(ThemeManager.getPanelColor());
        deleteVertexBtn.setForeground(ThemeManager.getTextColor());
        deleteVertexBtn.setFocusPainted(false);
        deleteVertexBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor(), 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        deleteVertexBtn.addActionListener(e -> deleteVertex());
        vertexControlPanel.add(deleteVertexBtn);

        vertexPanel.add(vertexControlPanel, BorderLayout.CENTER);

        panel.add(vertexPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Удаление полигона
        JPanel polygonPanel = new JPanel(new BorderLayout(5, 0));
        polygonPanel.setOpaque(false);

        JLabel polygonLabel = new JLabel("Полигон:");
        polygonPanel.add(polygonLabel, BorderLayout.WEST);

        JPanel polygonControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        polygonControlPanel.setOpaque(false);

        polygonSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        polygonSpinner.setPreferredSize(new Dimension(60, 25));
        polygonControlPanel.add(polygonSpinner);

        JButton deletePolygonBtn = new JButton("Удалить");
        deletePolygonBtn.setBackground(ThemeManager.getPanelColor());
        deletePolygonBtn.setForeground(ThemeManager.getTextColor());
        deletePolygonBtn.setFocusPainted(false);
        deletePolygonBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor(), 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        deletePolygonBtn.addActionListener(e -> deletePolygon());
        polygonControlPanel.add(deletePolygonBtn);

        polygonPanel.add(polygonControlPanel, BorderLayout.CENTER);

        panel.add(polygonPanel);

        add(panel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void deleteVertex() {
        Model3D model = mainWindow.getSceneModel().getActiveModel();
        if (model == null) {
            showErrorDialog("Нет активной модели");
            return;
        }

        int index = (int) vertexSpinner.getValue();
        if (!ModelEditor.isValidVertexIndex(model, index)) {
            showErrorDialog("Неверный индекс вершины");
            return;
        }

        if (ModelEditor.deleteVertex(model, index)) {
            showSuccessDialog("Вершина удалена");
            updateModelInfo();
            mainWindow.getMainPanel().repaint();
            mainWindow.updateStatus("Вершина " + index + " удалена");
        } else {
            showErrorDialog("Ошибка удаления вершины");
        }
    }

    private void deletePolygon() {
        Model3D model = mainWindow.getSceneModel().getActiveModel();
        if (model == null) {
            showErrorDialog("Нет активной модели");
            return;
        }

        int index = (int) polygonSpinner.getValue();
        if (!ModelEditor.isValidPolygonIndex(model, index)) {
            showErrorDialog("Неверный индекс полигона");
            return;
        }

        if (ModelEditor.deletePolygon(model, index)) {
            showSuccessDialog("Полигон удален");
            updateModelInfo();
            mainWindow.getMainPanel().repaint();
            mainWindow.updateStatus("Полигон " + index + " удален");
        } else {
            showErrorDialog("Ошибка удаления полигона");
        }
    }

    private void initRenderSection() {
        JPanel panel = createStyledPanel("Отрисовка");
        panel.setLayout(new GridLayout(0, 1, 8, 8));

        gridCheckbox = new JCheckBox("Сетка", true);
        axesCheckbox = new JCheckBox("Оси координат", true);

        gridCheckbox.addActionListener(e ->
                mainWindow.getMainPanel().setGridVisible(gridCheckbox.isSelected()));
        axesCheckbox.addActionListener(e ->
                mainWindow.getMainPanel().setAxesVisible(axesCheckbox.isSelected()));

        panel.add(gridCheckbox);
        panel.add(axesCheckbox);

        add(panel);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void initThemeSection() {
        JPanel panel = createStyledPanel("Тема");
        panel.setLayout(new GridLayout(1, 1, 5, 5));

        themeToggleBtn = createStyledButton("Сменить тему");
        themeToggleBtn.addActionListener(e -> toggleTheme());

        panel.add(themeToggleBtn);

        add(panel);
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ThemeManager.getPanelColor());
        panel.setForeground(ThemeManager.getTextColor());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor(), 1),
                title
        ));
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ThemeManager.getPanelColor());
        button.setForeground(ThemeManager.getTextColor());
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor(), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return button;
    }

    private void renameModel() {
        Model3D model = mainWindow.getSceneModel().getActiveModel();
        if (model == null) {
            showErrorDialog("Нет активной модели");
            return;
        }

        String newName = JOptionPane.showInputDialog(this,
                "Введите новое имя модели:",
                model.getName());

        if (newName != null && !newName.trim().isEmpty()) {
            model.setName(newName.trim());
            updateModelList();
            mainWindow.updateStatus("Модель переименована: " + newName);
        }
    }

    private void toggleTheme() {
        ThemeManager.toggleTheme();
        applyTheme();
        mainWindow.getMainPanel().repaint();
        mainWindow.updateStatus("Тема изменена на " +
                (ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? "темную" : "светлую"));
    }

    public void applyTheme() {
        setBackground(ThemeManager.getBackgroundColor());
        setForeground(ThemeManager.getTextColor());

        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.setBackground(ThemeManager.getPanelColor());
                panel.setForeground(ThemeManager.getTextColor());

                updateComponentColors(panel);
            }
        }

        if (themeToggleBtn != null) {
            themeToggleBtn.setText(ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ?
                    "Светлая тема" : "Темная тема");
        }

        repaint();
    }

    private void updateComponentColors(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(ThemeManager.getTextColor());
            } else if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(ThemeManager.getPanelColor());
                btn.setForeground(ThemeManager.getTextColor());
            } else if (comp instanceof JComboBox) {
                comp.setBackground(ThemeManager.getPanelColor());
                comp.setForeground(ThemeManager.getTextColor());
            } else if (comp instanceof JSpinner) {
                comp.setBackground(ThemeManager.getPanelColor());
                comp.setForeground(ThemeManager.getTextColor());
            } else if (comp instanceof JCheckBox) {
                comp.setForeground(ThemeManager.getTextColor());
            } else if (comp instanceof Container) {
                updateComponentColors((Container) comp);
            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width: 250px'>" + message + "</body></html>",
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width: 250px'>" + message + "</body></html>",
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateModelList() {
        modelSelector.removeAllItems();
        for (Model3D model : mainWindow.getSceneModel().getModels()) {
            modelSelector.addItem(model.getName() + (model.isModified() ? " *" : ""));
        }
        updateModelInfo();
    }

    public void updateModelInfo() {
        Model3D model = mainWindow.getSceneModel().getActiveModel();
        if (model != null) {
            String info = String.format("<html><center><b>%s</b><br>" +
                            "Вершин: %d<br>" +
                            "Полигонов: %d<br>" +
                            "Изменена: %s</center></html>",
                    model.getName(),
                    model.getVertexCount(),
                    model.getFaceCount(),
                    model.isModified() ? "Да" : "Нет");
            modelInfoLabel.setText(info);

            // Обновляем спиннеры
            SpinnerNumberModel vModel = (SpinnerNumberModel) vertexSpinner.getModel();
            vModel.setMaximum(Math.max(0, model.getVertexCount() - 1));

            SpinnerNumberModel pModel = (SpinnerNumberModel) polygonSpinner.getModel();
            pModel.setMaximum(Math.max(0, model.getFaceCount() - 1));
        } else {
            modelInfoLabel.setText("<html><center>Нет активной модели</center></html>");

            SpinnerNumberModel vModel = (SpinnerNumberModel) vertexSpinner.getModel();
            vModel.setMaximum(0);
            vModel.setValue(0);

            SpinnerNumberModel pModel = (SpinnerNumberModel) polygonSpinner.getModel();
            pModel.setMaximum(0);
            pModel.setValue(0);
        }
    }
}