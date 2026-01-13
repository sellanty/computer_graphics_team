package main.java.app.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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
    }

    private void initFileSection() {
        JPanel filePanel = new JPanel(new GridLayout(0, 1, 5, 5));
        filePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Файл",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        loadModelButton = new JButton("Загрузить модель");
        saveModelButton = new JButton("Сохранить модель");
        saveModelButton.setEnabled(false);

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

        modelSelector = new JComboBox<>(new String[]{"Модель 1", "Модель 2"});
        JButton addModelButton = new JButton("Добавить модель");
        JButton removeModelButton = new JButton("Удалить модель");

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

        renderPanel.add(wireframeCheckBox);
        renderPanel.add(textureCheckBox);
        renderPanel.add(lightingCheckBox);

        // Цвет модели
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorPanel.add(new JLabel("Цвет модели:"));
        JButton colorButton = new JButton("Выбрать");
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

        editPanel.add(deleteVertexButton);
        editPanel.add(deletePolygonButton);

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

        cameraPanel.add(new JLabel("Активная камера:"));
        cameraPanel.add(cameraSelector);
        cameraPanel.add(addCameraButton);
        cameraPanel.add(removeCameraButton);

        add(cameraPanel);
    }
}