package main.java.app.view;

import main.java.app.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class ErrorDialog {

    public static void showErrorDialog(Component parent, String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(ThemeManager.getPanelColor());
        textArea.setForeground(ThemeManager.getTextColor());
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        JOptionPane.showMessageDialog(parent, scrollPane, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showDetailedErrorDialog(Component parent, String title,
                                               String message, String details) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ThemeManager.getPanelColor());
        panel.setForeground(ThemeManager.getTextColor());

        JLabel messageLabel = new JLabel("<html><b>" + message + "</b></html>");
        messageLabel.setForeground(ThemeManager.getTextColor());
        panel.add(messageLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea(details);
        detailsArea.setEditable(false);
        detailsArea.setBackground(ThemeManager.getBackgroundColor());
        detailsArea.setForeground(ThemeManager.getTextColor());
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setPreferredSize(new Dimension(500, 100));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(ThemeManager.getPanelColor());
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeManager.getBorderColor(), 1),
                "Детали ошибки:"
        ));
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(detailsPanel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(parent, panel, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessDialog(Component parent, String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(ThemeManager.getPanelColor());
        textArea.setForeground(ThemeManager.getTextColor());
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        JOptionPane.showMessageDialog(parent, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmDialog(Component parent, String title, String message) {
        int result = JOptionPane.showConfirmDialog(parent,
                "<html><body style='width: 300px'>" +
                        message.replace("\n", "<br>") +
                        "</body></html>",
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static String showInputDialog(Component parent, String title, String message, String defaultValue) {
        return (String) JOptionPane.showInputDialog(parent,
                "<html><body style='width: 300px'>" +
                        message.replace("\n", "<br>") +
                        "</body></html>",
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                defaultValue);
    }
}