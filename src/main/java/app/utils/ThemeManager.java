package main.java.app.utils;

import javax.swing.*;
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
        if (theme != null && theme != currentTheme) {
            currentTheme = theme;
            applyTheme();
        }
    }

    public static void toggleTheme() {
        currentTheme = (currentTheme == Theme.DARK) ? Theme.LIGHT : Theme.DARK;
        applyTheme();
    }

    public static void applyTheme() {
        try {
            if (currentTheme == Theme.DARK) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
        } catch (Exception e) {
            System.err.println("Ошибка применения темы: " + e.getMessage());
        }
    }

    private static void applyDarkTheme() {
        Color backgroundColor = new Color(40, 40, 45);
        Color panelColor = new Color(50, 50, 55);
        Color textColor = Color.WHITE;
        Color borderColor = new Color(80, 80, 90);
        Color controlColor = new Color(60, 60, 70);
        Color highlightColor = new Color(100, 100, 255);

        UIManager.put("Panel.background", backgroundColor);
        UIManager.put("Panel.foreground", textColor);

        UIManager.put("Button.background", controlColor);
        UIManager.put("Button.foreground", textColor);
        UIManager.put("Button.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("Label.foreground", textColor);

        UIManager.put("TextField.background", panelColor);
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("TextField.caretForeground", textColor);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("ComboBox.background", controlColor);
        UIManager.put("ComboBox.foreground", textColor);
        UIManager.put("ComboBox.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("Spinner.background", controlColor);
        UIManager.put("Spinner.foreground", textColor);
        UIManager.put("Spinner.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("CheckBox.foreground", textColor);
        UIManager.put("CheckBox.background", backgroundColor);

        UIManager.put("MenuBar.background", panelColor);
        UIManager.put("MenuBar.foreground", textColor);

        UIManager.put("Menu.background", panelColor);
        UIManager.put("Menu.foreground", textColor);

        UIManager.put("MenuItem.background", controlColor);
        UIManager.put("MenuItem.foreground", textColor);

        UIManager.put("PopupMenu.background", controlColor);
        UIManager.put("PopupMenu.foreground", textColor);

        UIManager.put("Separator.background", borderColor);
        UIManager.put("Separator.foreground", borderColor);

        UIManager.put("ScrollPane.background", backgroundColor);
        UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("Viewport.background", backgroundColor);

        UIManager.put("OptionPane.background", panelColor);
        UIManager.put("OptionPane.foreground", textColor);
        UIManager.put("OptionPane.messageForeground", textColor);

        UIManager.put("TitledBorder.titleColor", textColor);
        UIManager.put("TitledBorder.border", BorderFactory.createLineBorder(borderColor));
    }

    private static void applyLightTheme() {
        Color backgroundColor = new Color(240, 240, 245);
        Color panelColor = new Color(250, 250, 255);
        Color textColor = Color.BLACK;
        Color borderColor = new Color(200, 200, 210);
        Color controlColor = new Color(240, 240, 245);
        Color highlightColor = new Color(0, 120, 215);

        UIManager.put("Panel.background", backgroundColor);
        UIManager.put("Panel.foreground", textColor);

        UIManager.put("Button.background", controlColor);
        UIManager.put("Button.foreground", textColor);
        UIManager.put("Button.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("Label.foreground", textColor);

        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("TextField.caretForeground", textColor);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", textColor);
        UIManager.put("ComboBox.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("Spinner.background", Color.WHITE);
        UIManager.put("Spinner.foreground", textColor);
        UIManager.put("Spinner.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("CheckBox.foreground", textColor);
        UIManager.put("CheckBox.background", backgroundColor);

        UIManager.put("MenuBar.background", panelColor);
        UIManager.put("MenuBar.foreground", textColor);

        UIManager.put("Menu.background", panelColor);
        UIManager.put("Menu.foreground", textColor);

        UIManager.put("MenuItem.background", Color.WHITE);
        UIManager.put("MenuItem.foreground", textColor);

        UIManager.put("PopupMenu.background", Color.WHITE);
        UIManager.put("PopupMenu.foreground", textColor);

        UIManager.put("Separator.background", borderColor);
        UIManager.put("Separator.foreground", borderColor);

        UIManager.put("ScrollPane.background", backgroundColor);
        UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(borderColor));

        UIManager.put("Viewport.background", Color.WHITE);

        UIManager.put("OptionPane.background", panelColor);
        UIManager.put("OptionPane.foreground", textColor);
        UIManager.put("OptionPane.messageForeground", textColor);

        UIManager.put("TitledBorder.titleColor", textColor);
        UIManager.put("TitledBorder.border", BorderFactory.createLineBorder(borderColor));
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

    public static Color getButtonColor() {
        return currentTheme == Theme.DARK ?
                new Color(60, 60, 70) : new Color(240, 240, 245);
    }

    public static Color getHighlightColor() {
        return currentTheme == Theme.DARK ?
                new Color(100, 100, 255) : new Color(0, 120, 215);
    }
}