package main.java.app.utils;

import java.io.File;

public class FileUtils {

    /**
     * Получить расширение файла
     */
    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // нет расширения
        }
        return name.substring(lastIndexOf);
    }

    /**
     * Получить имя файла без расширения
     */
    public static String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return name;
        }
        return name.substring(0, lastIndexOf);
    }

    /**
     * Проверить, является ли файл OBJ
     */
    public static boolean isObjFile(File file) {
        return getFileExtension(file).toLowerCase().equals(".obj");
    }

    /**
     * Создать уникальное имя для модели
     */
    public static String generateUniqueModelName(String baseName) {
        return baseName + "_" + System.currentTimeMillis();
    }

    /**
     * Форматировать размер файла
     */
    public static String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.1f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * Проверить, доступен ли файл для чтения
     */
    public static boolean isFileReadable(File file) {
        return file.exists() && file.isFile() && file.canRead();
    }

    /**
     * Проверить, доступен ли файл для записи
     */
    public static boolean isFileWritable(File file) {
        if (file.exists()) {
            return file.canWrite();
        } else {
            // Проверяем, можем ли создать файл в этой директории
            File parent = file.getParentFile();
            return parent != null && parent.exists() && parent.canWrite();
        }
    }

    /**
     * Получить относительный путь
     */
    public static String getRelativePath(File file, File base) {
        String filePath = file.getAbsolutePath();
        String basePath = base.getAbsolutePath();

        if (filePath.startsWith(basePath)) {
            return filePath.substring(basePath.length());
        }
        return filePath;
    }
}