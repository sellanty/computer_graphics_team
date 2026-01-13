package app.controller;

import javax.swing.*;
import java.io.File;

public class FileController {
    private JFrame parentFrame;

    public FileController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public File openFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Открыть модель");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Фильтр для .obj файлов
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".obj");
            }

            @Override
            public String getDescription() {
                return "OBJ файлы (*.obj)";
            }
        });

        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public File saveFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить модель как");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".obj");
            }

            @Override
            public String getDescription() {
                return "OBJ файлы (*.obj)";
            }
        });

        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Добавляем расширение .obj если его нет
            if (!file.getName().toLowerCase().endsWith(".obj")) {
                file = new File(file.getAbsolutePath() + ".obj");
            }
            return file;
        }
        return null;
    }

    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(parentFrame,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoDialog(String title, String message) {
        JOptionPane.showMessageDialog(parentFrame,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean showConfirmDialog(String title, String message) {
        int result = JOptionPane.showConfirmDialog(parentFrame,
                message,
                title,
                JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}