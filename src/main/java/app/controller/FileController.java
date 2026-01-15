package main.java.app.controller;

import main.java.app.model.Model3D;
import main.java.app.view.ErrorDialog;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FileController {
    private JFrame parentFrame;
    private ObjReader objReader;
    private ObjWriter objWriter;
    private File currentFile;

    public FileController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.objReader = new ObjReader();
        this.objWriter = new ObjWriter();
    }

    public Model3D openModel() {
        JFileChooser fileChooser = createFileChooser("Открыть модель");
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
            File file = fileChooser.getSelectedFile();
            currentFile = file;

            try {
                // Валидация файла
                if (!objReader.validateObjFile(file)) {
                    ErrorDialog.showErrorDialog(parentFrame, "Ошибка",
                            "Файл не является корректным OBJ файлом или не содержит вершин.");
                    return null;
                }

                Model3D model = objReader.read(file);

                showInfoDialog("Успех",
                        "Модель успешно загружена!\n" +
                                "Файл: " + file.getName() + "\n" +
                                "Вершин: " + model.getVertexCount() + "\n" +
                                "Полигонов: " + model.getFaceCount() + "\n" +
                                "Размер: " + formatFileSize(file.length()));
                return model;

            } catch (IOException e) {
                ErrorDialog.showDetailedErrorDialog(parentFrame, "Ошибка загрузки",
                        "Не удалось загрузить файл: " + file.getName(),
                        e.getMessage());
            } catch (Exception e) {
                ErrorDialog.showDetailedErrorDialog(parentFrame, "Ошибка формата",
                        "Файл имеет неверный формат или поврежден: " + file.getName(),
                        e.getMessage());
            }
        }
        return null;
    }

    public boolean saveModel(Model3D model, File file) {
        if (file == null) {
            return saveModelAs(model);
        }

        try {
            objWriter.write(model, file);
            currentFile = file;
            model.setModified(false);

            showInfoDialog("Успех",
                    "Модель успешно сохранена!\n" +
                            "Файл: " + file.getName() + "\n" +
                            "Размер: " + formatFileSize(file.length()));
            return true;
        } catch (IOException e) {
            ErrorDialog.showDetailedErrorDialog(parentFrame, "Ошибка сохранения",
                    "Не удалось сохранить файл: " + file.getName(),
                    e.getMessage());
            return false;
        }
    }

    public boolean saveModelAs(Model3D model) {
        JFileChooser fileChooser = createFileChooser("Сохранить модель как");
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

        // Предлагаем сохранить с текущим именем модели
        if (currentFile != null) {
            fileChooser.setSelectedFile(new File(currentFile.getParentFile(), model.getName() + ".obj"));
        } else {
            fileChooser.setSelectedFile(new File(model.getName() + ".obj"));
        }

        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".obj")) {
                file = new File(file.getAbsolutePath() + ".obj");
            }

            if (file.exists()) {
                int response = JOptionPane.showConfirmDialog(parentFrame,
                        "Файл " + file.getName() + " уже существует.\nПерезаписать?",
                        "Подтверждение",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (response != JOptionPane.YES_OPTION) {
                    return false;
                }
            }

            try {
                objWriter.write(model, file);
                currentFile = file;
                model.setModified(false);

                showInfoDialog("Успех",
                        "Модель успешно сохранена!\n" +
                                "Файл: " + file.getName() + "\n" +
                                "Размер: " + formatFileSize(file.length()));
                return true;
            } catch (IOException e) {
                ErrorDialog.showDetailedErrorDialog(parentFrame, "Ошибка сохранения",
                        "Не удалось сохранить файл: " + file.getName(),
                        e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void showErrorDialog(String title, String message) {
        ErrorDialog.showErrorDialog(parentFrame, title, message);
    }

    public void showInfoDialog(String title, String message) {
        ErrorDialog.showSuccessDialog(parentFrame, title, message);
    }

    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0);
        } else {
            return String.format("%.1f MB", size / (1024.0 * 1024.0));
        }
    }

    private JFileChooser createFileChooser(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        String userHome = System.getProperty("user.home");
        File desktop = new File(userHome, "Desktop");
        if (desktop.exists() && desktop.isDirectory()) {
            fileChooser.setCurrentDirectory(desktop);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        return fileChooser;
    }

    public File getCurrentFile() {
        return currentFile;
    }
}