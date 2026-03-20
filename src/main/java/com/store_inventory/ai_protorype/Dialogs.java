package com.store_inventory.ai_protorype;

import javax.swing.JOptionPane;
import java.awt.Component;

public final class Dialogs {
    private Dialogs() {
    }

    public static void error(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
