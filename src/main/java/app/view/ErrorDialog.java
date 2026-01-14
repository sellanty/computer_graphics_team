package main.java.app.view;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog {

    public static void showErrorDialog(Component parent, String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(UIManager.getColor("Panel.background"));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        JOptionPane.showMessageDialog(parent, scrollPane, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showDetailedErrorDialog(Component parent, String title,
                                               String message, String details) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel messageLabel = new JLabel("<html><b>" + message + "</b></html>");
        panel.add(messageLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea(details);
        detailsArea.setEditable(false);
        detailsArea.setBackground(new Color(240, 240, 240));
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setPreferredSize(new Dimension(500, 100));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Детали ошибки:"));
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(detailsPanel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(parent, panel, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent,
                "<html><body style='width: 300px'>" +
                        message.replace("\n", "<br>") +
                        "</body></html>",
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}