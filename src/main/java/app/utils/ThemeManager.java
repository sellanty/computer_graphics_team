package main.java.app.utils;

import java.awt.*;

public class ThemeManager {
    public enum Theme {
        LIGHT, DARK
    }

    private static Theme currentTheme = Theme.DARK;

    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    public static void setTheme(Theme theme) {
        currentTheme = theme;
        applyTheme();
    }

    public static void toggleTheme() {
        currentTheme = (currentTheme == Theme.DARK) ? Theme.LIGHT : Theme.DARK;
        applyTheme();
    }

    private static void applyTheme() {
        // Здесь можно добавить глобальную смену темы через UIManager
        System.out.println("Тема изменена на: " + currentTheme);
    }

    public static Color getBackgroundColor() {
        return currentTheme == Theme.DARK ?
                new Color(40, 40, 45) : new Color(240, 240, 245);
    }

    public static Color getPanelColor() {
        return currentTheme == Theme.DARK ?
                new Color(50, 50, 55) : new Color(250, 250, 255);
    }

    public static Color getTextColor() {
        return currentTheme == Theme.DARK ?
                Color.WHITE : Color.BLACK;
    }

    public static Color getBorderColor() {
        return currentTheme == Theme.DARK ?
                new Color(80, 80, 90) : new Color(200, 200, 210);
    }

    public static Color getGridColor() {
        return currentTheme == Theme.DARK ?
                new Color(80, 80, 90, 100) : new Color(180, 180, 190, 100);
    }

    public static Color getModelColor(boolean active) {
        if (currentTheme == Theme.DARK) {
            return active ? new Color(255, 200, 100, 200) :
                    new Color(100, 150, 255, 150);
        } else {
            return active ? new Color(255, 150, 50, 200) :
                    new Color(50, 120, 255, 150);
        }
    }
}