package main.java.app.controller;

import main.java.app.model.Model3D;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FileController {
    private JFrame parentFrame;
    private ObjReader objReader;
    private ObjWriter objWriter;

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
            try {
                Model3D model = objReader.read(file);
                showInfoDialog("Успех",
                        "Модель успешно загружена!\n" +
                                "Файл: " + file.getName() + "\n" +
                                "Вершин: " + model.getVertexCount() + "\n" +
                                "Полигонов: " + model.getFaceCount());
                model.setFilePath(file.getAbsolutePath());
                return model;
            } catch (IOException e) {
                showErrorDialog("Ошибка загрузки",
                        "Не удалось загрузить файл: " + file.getName() + "\n" +
                                "Причина: " + e.getMessage());
            } catch (Exception e) {
                showErrorDialog("Ошибка формата",
                        "Файл имеет неверный формат или поврежден: " + file.getName() + "\n" +
                                "Детали: " + e.getMessage());
            }
        }
        return null;
    }

    public boolean saveModel(Model3D model, File currentFile) {
        if (currentFile == null) {
            return saveModelAs(model);
        }

        try {
            objWriter.write(model, currentFile);
            showInfoDialog("Успех",
                    "Модель успешно сохранена!\n" +
                            "Файл: " + currentFile.getName());
            return true;
        } catch (IOException e) {
            showErrorDialog("Ошибка сохранения",
                    "Не удалось сохранить файл: " + currentFile.getName() + "\n" +
                            "Причина: " + e.getMessage());
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

        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".obj")) {
                file = new File(file.getAbsolutePath() + ".obj");
            }

            if (file.exists()) {
                boolean overwrite = showConfirmDialog("Подтверждение",
                        "Файл " + file.getName() + " уже существует.\n" +
                                "Перезаписать?");
                if (!overwrite) {
                    return false;
                }
            }

            try {
                objWriter.write(model, file);
                showInfoDialog("Успех",
                        "Модель успешно сохранена!\n" +
                                "Файл: " + file.getName());
                return true;
            } catch (IOException e) {
                showErrorDialog("Ошибка сохранения",
                        "Не удалось сохранить файл: " + file.getName() + "\n" +
                                "Причина: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(parentFrame,
                wrapMessage(message),
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoDialog(String title, String message) {
        JOptionPane.showMessageDialog(parentFrame,
                wrapMessage(message),
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean showConfirmDialog(String title, String message) {
        int result = JOptionPane.showConfirmDialog(parentFrame,
                wrapMessage(message),
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private String wrapMessage(String message) {
        return "<html><body style='width: 300px'>" +
                message.replace("\n", "<br>") +
                "</body></html>";
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

    public String getModelInfo(Model3D model) {
        if (model == null) {
            return "Нет загруженной модели";
        }
        return String.format("Модель: %s | Вершин: %d | Полигонов: %d",
                model.getName(), model.getVertexCount(), model.getFaceCount());
    }
}