package com.store_inventory.ai_protorype;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.awt.Font;

public final class UIStyles {
    public static final Color BG = new Color(245, 247, 249);
    public static final Color PANEL = new Color(255, 255, 255);
    public static final Color CARD = new Color(250, 252, 253);
    public static final Color ACCENT = new Color(0, 145, 190);
    public static final Color TEXT = new Color(20, 26, 31);
    public static final Color MUTED = new Color(90, 105, 115);
    public static final Color BORDER = new Color(210, 222, 230);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font MONO_FONT = new Font("Consolas", Font.PLAIN, 12);

    private UIStyles() {
    }

    public static Border roundedBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );
    }

    public static JPanel cardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(roundedBorder());
        return panel;
    }

    public static JButton accentButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BG);
        button.setForeground(ACCENT);
        button.setFont(HEADER_FONT);
        button.setBorder(BorderFactory.createLineBorder(ACCENT, 1, true));
        button.setFocusPainted(false);
        return button;
    }

    public static JButton ghostButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PANEL);
        button.setForeground(TEXT);
        button.setFont(BODY_FONT);
        button.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        button.setFocusPainted(false);
        return button;
    }

    public static JLabel titleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(TITLE_FONT);
        return label;
    }

    public static JLabel headerLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(HEADER_FONT);
        return label;
    }

    public static JLabel mutedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(BODY_FONT);
        return label;
    }

    public static JTable styleTable(JTable table) {
        table.setForeground(TEXT);
        table.setBackground(PANEL);
        table.setFont(BODY_FONT);
        table.setRowHeight(30);
        table.setGridColor(BORDER);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG);
        header.setForeground(ACCENT);
        header.setFont(HEADER_FONT);
        return table;
    }

    public static JTextField styledField() {
        JTextField field = new JTextField();
        field.setBackground(PANEL);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return field;
    }

    public static JLabel badgeLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(ACCENT);
        label.setFont(HEADER_FONT);
        label.setBorder(BorderFactory.createLineBorder(ACCENT, 1, true));
        return label;
    }

    public static void applyGlobalDefaults() {
        UIManager.put("OptionPane.background", BG);
        UIManager.put("Panel.background", BG);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("Button.background", PANEL);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("TextField.background", PANEL);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", TEXT);
    }
}
