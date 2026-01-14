package main.java.app;

import main.java.app.controller.FileController;
import main.java.app.model.Model3D;
import main.java.app.model.SceneModel;
import main.java.app.utils.ThemeManager;
import main.java.app.view.MainPanel;
import main.java.app.view.ControlPanel;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    private ControlPanel controlPanel;
    private FileController fileController;
    private SceneModel sceneModel;

    public MainWindow() {
        initWindow();
        initControllers();
        initComponents();
        layoutComponents();
        initMenu();
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
    }

    private void layoutComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(controlPanel, BorderLayout.WEST);
        container.add(mainPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("Готов к работе");
        statusPanel.add(statusLabel, BorderLayout.WEST);
        container.add(statusPanel, BorderLayout.SOUTH);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Меню Файл
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Открыть модель (Ctrl+O)");
        JMenuItem saveItem = new JMenuItem("Сохранить (Ctrl+S)");
        JMenuItem exitItem = new JMenuItem("Выход");

        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        openItem.addActionListener(e -> openModel());
        saveItem.addActionListener(e -> saveModel());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Меню Вид
        JMenu viewMenu = new JMenu("Вид");
        JMenuItem lightThemeItem = new JMenuItem("Светлая тема");
        JMenuItem darkThemeItem = new JMenuItem("Тёмная тема");
        JCheckBoxMenuItem wireframeItem = new JCheckBoxMenuItem("Полигональная сетка");
        JCheckBoxMenuItem textureItem = new JCheckBoxMenuItem("Текстура");
        JCheckBoxMenuItem lightingItem = new JCheckBoxMenuItem("Освещение");

        lightThemeItem.addActionListener(e -> setLightTheme());
        darkThemeItem.addActionListener(e -> setDarkTheme());

        viewMenu.add(lightThemeItem);
        viewMenu.add(darkThemeItem);
        viewMenu.addSeparator();
        viewMenu.add(wireframeItem);
        viewMenu.add(textureItem);
        viewMenu.add(lightingItem);

        // Меню Помощь
        JMenu helpMenu = new JMenu("Помощь");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setLightTheme() {
        ThemeManager.setTheme(ThemeManager.Theme.LIGHT);
        applyTheme();
    }

    private void setDarkTheme() {
        ThemeManager.setTheme(ThemeManager.Theme.DARK);
        applyTheme();
    }

    private void applyTheme() {
        SwingUtilities.updateComponentTreeUI(this);
        mainPanel.repaint();
        controlPanel.repaint();
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "3D Viewer v1.0\n" +
                        "Разработчик: Иванов Александр\n" +
                        "Курс: Компьютерная графика\n\n" +
                        "Функции:\n" +
                        "• Загрузка/сохранение OBJ моделей\n" +
                        "• Управление несколькими моделями\n" +
                        "• Переключение тем (светлая/тёмная)\n" +
                        "• Режимы отрисовки",
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // === Публичные методы для ControlPanel ===

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
        }
    }

    public void saveModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel == null) {
            fileController.showErrorDialog("Ошибка", "Нет активной модели");
            return;
        }
        fileController.saveModelAs(activeModel);
    }

    public void closeModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel != null) {
            sceneModel.removeModel(activeModel);
            controlPanel.updateModelList();
            mainPanel.repaint();
        }
    }

    public void selectModel(int index) {
        if (index >= 0 && index < sceneModel.getModels().size()) {
            sceneModel.setActiveModel(sceneModel.getModels().get(index));
            mainPanel.repaint();
        }
    }

    public void duplicateModel() {
        Model3D activeModel = sceneModel.getActiveModel();
        if (activeModel != null) {
            Model3D duplicate = activeModel.copy();
            sceneModel.addModel(duplicate);
            controlPanel.updateModelList();
            mainPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}