package com.store_inventory.ai_protorype;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.function.BiConsumer;

public class TableActionCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final JButton primary;
    private final JButton secondary;
    private final BiConsumer<Integer, String> handler;
    private int editingRow = -1;

    public TableActionCell(String primaryText, String secondaryText, BiConsumer<Integer, String> handler) {
        this.handler = handler;
        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        this.panel.setBackground(UIStyles.PANEL);
        this.primary = UIStyles.ghostButton(primaryText);
        this.secondary = UIStyles.ghostButton(secondaryText);
        this.primary.setHorizontalAlignment(SwingConstants.CENTER);
        this.secondary.setHorizontalAlignment(SwingConstants.CENTER);
        this.panel.add(primary);
        this.panel.add(secondary);

        primary.addActionListener(e -> fire("primary"));
        secondary.addActionListener(e -> fire("secondary"));
    }

    private void fire(String action) {
        if (editingRow >= 0) {
            handler.accept(editingRow, action);
        }
        fireEditingStopped();
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        panel.setBackground(isSelected ? UIStyles.CARD : UIStyles.PANEL);
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editingRow = row;
        panel.setBackground(UIStyles.CARD);
        return panel;
    }
}
