package main.java.app;

import main.java.app.controller.FileController;
import main.java.app.model.Model3D;
import main.java.app.model.SceneModel;
import main.java.app.view.MainPanel;
import main.java.app.view.ControlPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    private ControlPanel controlPanel;
    private JMenuBar menuBar;
    private FileController fileController;
    private SceneModel sceneModel;
    private File currentModelFile;
    private JLabel statusLabel;

    public MainWindow() {
        initWindow();
        initControllers();
        initMenuBar();
        initComponents();
        layoutComponents();
        updateUIState();
    }

    private void initWindow() {
        setTitle("3D Viewer - Ваша фамилия");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        setMinimumSize(new Dimension(800, 600));
    }

    private void initControllers() {
        fileController = new FileController(this);
        sceneModel = new SceneModel();
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        // Меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openItem = new JMenuItem("Открыть модель...");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> onOpenModel());

        JMenuItem saveItem = new JMenuItem("Сохранить модель");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(e -> onSaveModel());

        JMenuItem saveAsItem = new JMenuItem("Сохранить как...");
        saveAsItem.setMnemonic(KeyEvent.VK_A);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        saveAsItem.addActionListener(e -> onSaveAsModel());

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(e -> onExit());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Меню "Редактирование"
        JMenu editMenu = new JMenu("Редактирование");
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem deleteVertexItem = new JMenuItem("Удалить вершину");
        deleteVertexItem.setMnemonic(KeyEvent.VK_V);
        deleteVertexItem.setEnabled(false);
        deleteVertexItem.addActionListener(e -> onDeleteVertex());

        JMenuItem deletePolygonItem = new JMenuItem("Удалить полигон");
        deletePolygonItem.setMnemonic(KeyEvent.VK_P);
        deletePolygonItem.setEnabled(false);
        deletePolygonItem.addActionListener(e -> onDeletePolygon());

        editMenu.add(deleteVertexItem);
        editMenu.add(deletePolygonItem);

        // Меню "Вид"
        JMenu viewMenu = new JMenu("Вид");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        JCheckBoxMenuItem wireframeItem = new JCheckBoxMenuItem("Полигональная сетка");
        JCheckBoxMenuItem textureItem = new JCheckBoxMenuItem("Текстура");
        JCheckBoxMenuItem lightingItem = new JCheckBoxMenuItem("Освещение");

        ButtonGroup themeGroup = new ButtonGroup();
        JRadioButtonMenuItem lightThemeItem = new JRadioButtonMenuItem("Светлая тема");
        JRadioButtonMenuItem darkThemeItem = new JRadioButtonMenuItem("Тёмная тема", true);

        themeGroup.add(lightThemeItem);
        themeGroup.add(darkThemeItem);

        lightThemeItem.addActionListener(e -> onThemeChanged("light"));
        darkThemeItem.addActionListener(e -> onThemeChanged("dark"));

        viewMenu.add(wireframeItem);
        viewMenu.add(textureItem);
        viewMenu.add(lightingItem);
        viewMenu.addSeparator();
        viewMenu.add(lightThemeItem);
        viewMenu.add(darkThemeItem);

        // Меню "Камера"
        JMenu cameraMenu = new JMenu("Камера");
        cameraMenu.setMnemonic(KeyEvent.VK_C);

        JMenuItem addCameraItem = new JMenuItem("Добавить камеру");
        JMenuItem removeCameraItem = new JMenuItem("Удалить камеру");
        JMenuItem cameraSettingsItem = new JMenuItem("Настройки камеры...");

        cameraMenu.add(addCameraItem);
        cameraMenu.add(removeCameraItem);
        cameraMenu.addSeparator();
        cameraMenu.add(cameraSettingsItem);

        // Меню "Помощь"
        JMenu helpMenu = new JMenu("Помощь");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);

        // Добавляем меню
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(cameraMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void initComponents() {
        mainPanel = new MainPanel();
        controlPanel = new ControlPanel();

        // Привязываем кнопки из controlPanel
        controlPanel.getLoadModelButton().addActionListener(e -> onOpenModel());
        controlPanel.getSaveModelButton().addActionListener(e -> onSaveModel());

        // Привязываем выбор модели в комбобоксе
        controlPanel.getModelSelector().addActionListener(e -> onModelSelected());
    }

    private void layoutComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Панель управления слева
        container.add(controlPanel, BorderLayout.WEST);

        // Основная панель отрисовки по центру
        container.add(mainPanel, BorderLayout.CENTER);

        // Статус бар внизу
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("Готов к работе. Нажмите Ctrl+O для загрузки модели.");
        statusPanel.add(statusLabel, BorderLayout.WEST);

        // Добавляем информацию о версии справа
        JLabel versionLabel = new JLabel("3D Viewer v1.0");
        versionLabel.setForeground(Color.GRAY);
        statusPanel.add(versionLabel, BorderLayout.EAST);

        container.add(statusPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    // ========== ОБРАБОТЧИКИ СОБЫТИЙ ==========

    private void onOpenModel() {
        Model3D model = fileController.openModel();
        if (model != null) {
            sceneModel.addModel(model);
            currentModelFile = new File(model.getName() + ".obj");

            // Обновляем интерфейс
            updateModelSelector();
            controlPanel.getModelSelector().setSelectedItem(model.getName());
            sceneModel.setActiveModel(model);
            updateUIState();

            // Обновляем статус
            updateStatus("Загружена модель: " + model.getName() +
                    " (" + model.getVertexCount() + " вершин, " +
                    model.getFaceCount() + " полигонов)");

            // Перерисовываем
            mainPanel.repaint();
        }
    }

    private void onSaveModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel == null) {
            fileController.showErrorDialog("Ошибка", "Нет активной модели для сохранения");
            return;
        }

        if (currentModelFile == null) {
            onSaveAsModel();
        } else {
            boolean success = fileController.saveModel(activeModel, currentModelFile);
            if (success) {
                updateStatus("Модель сохранена: " + currentModelFile.getName());
                activeModel.setModified(false);
            }
        }
    }

    private void onSaveAsModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel == null) {
            fileController.showErrorDialog("Ошибка", "Нет активной модели для сохранения");
            return;
        }

        boolean success = fileController.saveModelAs(activeModel);
        if (success) {
            updateStatus("Модель сохранена как...");
            activeModel.setModified(false);
        }
    }

    private void onExit() {
        // Проверяем несохраненные изменения
        boolean hasUnsaved = false;
        for (Model3D model : sceneModel.getModels()) {
            if (model.isModified()) {
                hasUnsaved = true;
                break;
            }
        }

        if (hasUnsaved) {
            boolean confirm = fileController.showConfirmDialog("Подтверждение выхода",
                    "Есть несохраненные изменения. Выйти без сохранения?");
            if (!confirm) {
                return;
            }
        }
        System.exit(0);
    }

    private void onModelSelected() {
        String selectedName = (String) controlPanel.getModelSelector().getSelectedItem();
        if (selectedName != null && !selectedName.equals("Нет моделей")) {
            for (Model3D model : sceneModel.getModels()) {
                if (model.getName().equals(selectedName)) {
                    sceneModel.setActiveModel(model);
                    updateStatus("Активная модель: " + model.getName());
                    break;
                }
            }
        }
    }

    private void onDeleteVertex() {
        // TODO: Реализовать в коммите 6
        System.out.println("Удалить вершину");
    }

    private void onDeletePolygon() {
        // TODO: Реализовать в коммите 6
        System.out.println("Удалить полигон");
    }

    private void onThemeChanged(String theme) {
        // TODO: Реализовать в коммите 5
        System.out.println("Тема изменена на: " + theme);
    }

    private void showAboutDialog() {
        fileController.showInfoDialog("О программе",
                "3D Viewer v1.0\n" +
                        "Разработчик: Иванов Александр\n" +
                        "Курс: Компьютерная графика\n" +
                        "Группа: 6\n\n" +
                        "Функции:\n" +
                        "• Загрузка/сохранение OBJ моделей\n" +
                        "• Управление несколькими моделями\n" +
                        "• Режимы отрисовки\n" +
                        "• Обработка ошибок\n");
    }

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ==========

    private void updateModelSelector() {
        JComboBox<String> selector = controlPanel.getModelSelector();
        selector.removeAllItems();

        if (sceneModel.getModels().isEmpty()) {
            selector.addItem("Нет моделей");
            selector.setEnabled(false);
        } else {
            for (Model3D model : sceneModel.getModels()) {
                String name = model.getName();
                if (model.isModified()) {
                    name += " *";
                }
                selector.addItem(name);
            }
            selector.setEnabled(true);
        }
    }

    private void updateUIState() {
        boolean hasModels = !sceneModel.getModels().isEmpty();

        // Обновляем меню
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            if (menu.getText().equals("Файл")) {
                for (int j = 0; j < menu.getItemCount(); j++) {
                    JMenuItem item = menu.getItem(j);
                    if (item != null && item.getText().contains("Сохранить")) {
                        item.setEnabled(hasModels);
                    }
                }
            } else if (menu.getText().equals("Редактирование")) {
                for (int j = 0; j < menu.getItemCount(); j++) {
                    JMenuItem item = menu.getItem(j);
                    if (item != null) {
                        item.setEnabled(hasModels);
                    }
                }
            }
        }

        // Обновляем кнопки в controlPanel
        controlPanel.getSaveModelButton().setEnabled(hasModels);
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public FileController getFileController() {
        return fileController;
    }

    public SceneModel getSceneModel() {
        return sceneModel;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}