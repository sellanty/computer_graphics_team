package main.java.app;

import main.java.app.controller.FileController;
import main.java.app.model.Model3D;
import main.java.app.model.SceneModel;
import main.java.app.view.MainPanel;
import main.java.app.view.ControlPanel;
import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    private ControlPanel controlPanel;
    private FileController fileController;
    private SceneModel sceneModel;
    private JPanel statusPanel;
    private JLabel statusLabel;

    public MainWindow() {
        initWindow();
        initControllers();
        initComponents();
        layoutComponents();
        initMenu();
        applyTheme();
    }

    private void initWindow() {
        setTitle("3D Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    private void initControllers() {
        fileController = new FileController(this);
        sceneModel = new SceneModel();
    }

    private void initComponents() {
        mainPanel = new MainPanel(sceneModel);
        controlPanel = new ControlPanel(this);

        statusLabel = new JLabel("Готов к работе");
        statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
    }

    private void layoutComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(controlPanel, BorderLayout.WEST);
        container.add(mainPanel, BorderLayout.CENTER);
        container.add(statusPanel, BorderLayout.SOUTH);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        applyMenuTheme(menuBar);

        // Меню Файл
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Открыть (Ctrl+O)");
        JMenuItem saveItem = new JMenuItem("Сохранить (Ctrl+S)");
        JMenuItem saveAsItem = new JMenuItem("Сохранить как...");
        JMenuItem exitItem = new JMenuItem("Выход");

        openItem.setAccelerator(KeyStroke.getKeyStroke("control O"));
        saveItem.setAccelerator(KeyStroke.getKeyStroke("control S"));

        openItem.addActionListener(e -> openModel());
        saveItem.addActionListener(e -> saveModel());
        saveAsItem.addActionListener(e -> saveModelAs());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);



        // Меню Вид
        JMenu viewMenu = new JMenu("Вид");
        JMenuItem lightThemeItem = new JMenuItem("Светлая тема");
        JMenuItem darkThemeItem = new JMenuItem("Темная тема");
        JCheckBoxMenuItem gridItem = new JCheckBoxMenuItem("Показать сетку", true);

        lightThemeItem.addActionListener(e -> setLightTheme());
        darkThemeItem.addActionListener(e -> setDarkTheme());
        gridItem.addActionListener(e -> mainPanel.setGridVisible(gridItem.isSelected()));

        viewMenu.add(lightThemeItem);
        viewMenu.add(darkThemeItem);
        viewMenu.addSeparator();
        viewMenu.add(gridItem);

        // Меню Справка
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void applyMenuTheme(JMenuBar menuBar) {
        menuBar.setBackground(ThemeManager.getPanelColor());
        menuBar.setForeground(ThemeManager.getTextColor());
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeManager.getBorderColor()));
    }

    private void applyTheme() {
        getContentPane().setBackground(ThemeManager.getBackgroundColor());
        statusPanel.setBackground(ThemeManager.getPanelColor());
        statusPanel.setForeground(ThemeManager.getTextColor());
        statusLabel.setForeground(ThemeManager.getTextColor());

        if (getJMenuBar() != null) {
            applyMenuTheme(getJMenuBar());
        }

        mainPanel.repaint();
        controlPanel.repaint();
    }

    private void setLightTheme() {
        ThemeManager.setTheme(ThemeManager.Theme.LIGHT);
        applyTheme();
        controlPanel.applyTheme();
    }

    private void setDarkTheme() {
        ThemeManager.setTheme(ThemeManager.Theme.DARK);
        applyTheme();
        controlPanel.applyTheme();
    }

    private void showAboutDialog() {
        String message = "<html><center>" +
                "<h2>3D Viewer</h2>" +
                "<p>Версия 1.0</p>" +
                "<p>Курс: Компьютерная графика</p>" +
                "<p>Задание: Финальный проект</p>" +
                "<p>Разработчик: Иванов Александр</p>" +
                "</center></html>";

        JOptionPane.showMessageDialog(this, message, "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // === Публичные методы ===

    public FileController getFileController() {
        return fileController;
    }

    public SceneModel getSceneModel() {
        return sceneModel;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void openModel() {
        Model3D model = fileController.openModel();
        if (model != null) {
            sceneModel.addModel(model);
            controlPanel.updateModelList();
            mainPanel.repaint();
            statusLabel.setText("Модель загружена: " + model.getName());
        }
    }

    public void saveModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel == null) {
            fileController.showErrorDialog("Ошибка", "Нет активной модели");
            return;
        }
        if (fileController.saveModel(activeModel, null)) {
            statusLabel.setText("Модель сохранена: " + activeModel.getName());
        }
    }

    public void saveModelAs() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel == null) {
            fileController.showErrorDialog("Ошибка", "Нет активной модели");
            return;
        }
        if (fileController.saveModelAs(activeModel)) {
            statusLabel.setText("Модель сохранена как: " + activeModel.getName());
        }
    }

    public void closeModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel != null) {
            sceneModel.removeModel(activeModel);
            controlPanel.updateModelList();
            mainPanel.repaint();
            statusLabel.setText("Модель закрыта: " + activeModel.getName());
        }
    }

    public void selectModel(int index) {
        if (index >= 0 && index < sceneModel.getModels().size()) {
            sceneModel.setActiveModel(sceneModel.getModels().get(index));
            controlPanel.updateModelInfo();
            mainPanel.repaint();
            statusLabel.setText("Активная модель: " + sceneModel.getActiveModel().getName());
        }
    }

    public void duplicateModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel != null) {
            Model3D duplicate = activeModel.copy();
            sceneModel.addModel(duplicate);
            controlPanel.updateModelList();
            mainPanel.repaint();
            statusLabel.setText("Модель дублирована: " + duplicate.getName());
        }
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}