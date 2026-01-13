package main.java.app;

import main.java.app.view.MainPanel;
import app.view.ControlPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    private MainPanel mainPanel;
    private ControlPanel controlPanel;
    private JMenuBar menuBar;

    public MainWindow() {
        initWindow();
        initMenuBar();
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

    private void initMenuBar() {
        menuBar = new JMenuBar();

        // Меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openItem = new JMenuItem("Открыть модель...");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> onOpenModel());

        JMenuItem saveItem = new JMenuItem("Сохранить модель...");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveItem.setEnabled(false);
        saveItem.addActionListener(e -> onSaveModel());

        JMenuItem saveAsItem = new JMenuItem("Сохранить как...");
        saveAsItem.setMnemonic(KeyEvent.VK_A);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        saveAsItem.setEnabled(false);
        saveAsItem.addActionListener(e -> onSaveAsModel());

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        exitItem.addActionListener(e -> System.exit(0));

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

        // Добавляем все меню в меню бар
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

    // Обработчики действий (заглушки - будут реализованы в следующих коммитах)
    private void onOpenModel() {
        System.out.println("Меню: Открыть модель");
        // TODO: Реализовать в коммите 3
    }

    private void onSaveModel() {
        System.out.println("Меню: Сохранить модель");
        // TODO: Реализовать в коммите 3
    }

    private void onSaveAsModel() {
        System.out.println("Меню: Сохранить как");
        // TODO: Реализовать в коммите 3
    }

    private void onDeleteVertex() {
        System.out.println("Меню: Удалить вершину");
        // TODO: Реализовать в коммите 6
    }

    private void onDeletePolygon() {
        System.out.println("Меню: Удалить полигон");
        // TODO: Реализовать в коммите 6
    }

    private void onThemeChanged(String theme) {
        System.out.println("Тема изменена на: " + theme);
        // TODO: Реализовать в коммите 5
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "3D Viewer v1.0\n" +
                        "Разработчик: [Ваша фамилия]\n" +
                        "Курс: Компьютерная графика\n" +
                        "Группа: [Ваша группа]",
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }
}