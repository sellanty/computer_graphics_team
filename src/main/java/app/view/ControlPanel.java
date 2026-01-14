package main.java.app.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private JButton loadModelButton;
    private JButton saveModelButton;
    private JComboBox<String> modelSelector;
    private JCheckBox wireframeCheckBox;
    private JCheckBox textureCheckBox;
    private JCheckBox lightingCheckBox;

    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initFileSection();
        initSceneSection();
        initRenderSection();
        initModelEditSection();
        initCameraSection();
        initTransformSection();

        // Заполняем пустое пространство внизу
        add(Box.createVerticalGlue());
    }

    private void initFileSection() {
        JPanel filePanel = new JPanel(new GridLayout(0, 1, 5, 5));
        filePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Файл",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        loadModelButton = new JButton("Загрузить модель (Ctrl+O)");
        saveModelButton = new JButton("Сохранить модель (Ctrl+S)");
        saveModelButton.setEnabled(false);

        // Обработчики для кнопок (заглушки)
        loadModelButton.addActionListener(e -> {
            System.out.println("Кнопка: Загрузить модель");
            // TODO: Реализовать в коммите 3
        });

        saveModelButton.addActionListener(e -> {
            System.out.println("Кнопка: Сохранить модель");
            // TODO: Реализовать в коммите 3
        });

        filePanel.add(loadModelButton);
        filePanel.add(saveModelButton);

        add(filePanel);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void initSceneSection() {
        JPanel scenePanel = new JPanel(new GridLayout(0, 1, 5, 5));
        scenePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Сцена",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        modelSelector = new JComboBox<>(new String[]{"Нет моделей"});
        JButton addModelButton = new JButton("Добавить модель");
        JButton removeModelButton = new JButton("Удалить модель");

        // Обработчики для сцены
        modelSelector.addActionListener(e -> {
            String selected = (String) modelSelector.getSelectedItem();
            System.out.println("Выбрана модель: " + selected);
            // TODO: Обновить активную модель
        });

        addModelButton.addActionListener(e -> {
            System.out.println("Кнопка: Добавить модель");
            // TODO: Реализовать диалог добавления
        });

        removeModelButton.addActionListener(e -> {
            System.out.println("Кнопка: Удалить модель");
            // TODO: Реализовать удаление
        });

        scenePanel.add(new JLabel("Активная модель:"));
        scenePanel.add(modelSelector);
        scenePanel.add(addModelButton);
        scenePanel.add(removeModelButton);

        add(scenePanel);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void initRenderSection() {
        JPanel renderPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        renderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Режимы отрисовки",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        wireframeCheckBox = new JCheckBox("Полигональная сетка");
        textureCheckBox = new JCheckBox("Текстура");
        lightingCheckBox = new JCheckBox("Освещение");

        // Обработчики для чекбоксов
        wireframeCheckBox.addActionListener(e -> {
            System.out.println("Сетка: " + (wireframeCheckBox.isSelected() ? "ВКЛ" : "ВЫКЛ"));
        });

        textureCheckBox.addActionListener(e -> {
            System.out.println("Текстура: " + (textureCheckBox.isSelected() ? "ВКЛ" : "ВЫКЛ"));
        });

        lightingCheckBox.addActionListener(e -> {
            System.out.println("Освещение: " + (lightingCheckBox.isSelected() ? "ВКЛ" : "ВЫКЛ"));
        });

        renderPanel.add(wireframeCheckBox);
        renderPanel.add(textureCheckBox);
        renderPanel.add(lightingCheckBox);

        // Цвет модели
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorPanel.add(new JLabel("Цвет модели:"));
        JButton colorButton = new JButton("Выбрать");
        colorButton.addActionListener(e -> {
            System.out.println("Кнопка: Выбрать цвет");
            Color color = JColorChooser.showDialog(this, "Выберите цвет модели", Color.RED);
            if (color != null) {
                System.out.println("Выбран цвет: " + color);
            }
        });
        colorPanel.add(colorButton);
        renderPanel.add(colorPanel);

        add(renderPanel);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void initModelEditSection() {
        JPanel editPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        editPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Редактирование модели",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        JButton deleteVertexButton = new JButton("Удалить вершину");
        JButton deletePolygonButton = new JButton("Удалить полигон");
        deleteVertexButton.setEnabled(false);
        deletePolygonButton.setEnabled(false);

        // Обработчики для редактирования
        deleteVertexButton.addActionListener(e -> {
            System.out.println("Кнопка: Удалить вершину");
            // TODO: Реализовать в коммите 6
        });

        deletePolygonButton.addActionListener(e -> {
            System.out.println("Кнопка: Удалить полигон");
            // TODO: Реализовать в коммите 6
        });

        editPanel.add(deleteVertexButton);
        editPanel.add(deletePolygonButton);

        // Селектор для выбора элемента
        JPanel selectPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        selectPanel.add(new JLabel("Вершина:"));
        JSpinner vertexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        selectPanel.add(vertexSpinner);

        selectPanel.add(new JLabel("Полигон:"));
        JSpinner polygonSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        selectPanel.add(polygonSpinner);

        editPanel.add(selectPanel);

        add(editPanel);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void initCameraSection() {
        JPanel cameraPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        cameraPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Камера",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        JComboBox<String> cameraSelector = new JComboBox<>(new String[]{"Камера 1"});
        JButton addCameraButton = new JButton("Добавить камеру");
        JButton removeCameraButton = new JButton("Удалить камеру");

        // Обработчики для камеры
        cameraSelector.addActionListener(e -> {
            System.out.println("Выбрана камера: " + cameraSelector.getSelectedItem());
        });

        addCameraButton.addActionListener(e -> {
            System.out.println("Кнопка: Добавить камеру");
        });

        removeCameraButton.addActionListener(e -> {
            System.out.println("Кнопка: Удалить камеру");
        });

        cameraPanel.add(new JLabel("Активная камера:"));
        cameraPanel.add(cameraSelector);
        cameraPanel.add(addCameraButton);
        cameraPanel.add(removeCameraButton);

        add(cameraPanel);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void initTransformSection() {
        JPanel transformPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        transformPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Трансформации (заглушка)",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        // Заглушки для трансформаций (будет реализовано 2-м человеком)
        transformPanel.add(new JLabel("Масштаб X:"));
        JSpinner scaleXSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        transformPanel.add(scaleXSpinner);

        transformPanel.add(new JLabel("Масштаб Y:"));
        JSpinner scaleYSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        transformPanel.add(scaleYSpinner);

        transformPanel.add(new JLabel("Масштаб Z:"));
        JSpinner scaleZSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.1));
        transformPanel.add(scaleZSpinner);

        transformPanel.add(new JLabel("Поворот X:"));
        JSpinner rotateXSpinner = new JSpinner(new SpinnerNumberModel(0, -180, 180, 1));
        transformPanel.add(rotateXSpinner);

        transformPanel.add(new JLabel("Поворот Y:"));
        JSpinner rotateYSpinner = new JSpinner(new SpinnerNumberModel(0, -180, 180, 1));
        transformPanel.add(rotateYSpinner);

        transformPanel.add(new JLabel("Поворот Z:"));
        JSpinner rotateZSpinner = new JSpinner(new SpinnerNumberModel(0, -180, 180, 1));
        transformPanel.add(rotateZSpinner);

        JButton applyTransformButton = new JButton("Применить");
        applyTransformButton.addActionListener(e -> {
            System.out.println("Применить трансформации");
            System.out.println("Масштаб: X=" + scaleXSpinner.getValue() +
                    " Y=" + scaleYSpinner.getValue() +
                    " Z=" + scaleZSpinner.getValue());
            System.out.println("Поворот: X=" + rotateXSpinner.getValue() +
                    " Y=" + rotateYSpinner.getValue() +
                    " Z=" + rotateZSpinner.getValue());
        });

        transformPanel.add(new JLabel()); // Пустая ячейка
        transformPanel.add(applyTransformButton);

        add(transformPanel);
    }

    // Геттеры для доступа из контроллера
    public JButton getLoadModelButton() { return loadModelButton; }
    public JButton getSaveModelButton() { return saveModelButton; }
    public JComboBox<String> getModelSelector() { return modelSelector; }
}