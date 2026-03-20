package com.store_inventory.ai_protorype;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.function.IntConsumer;

public class TableSingleActionCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final JButton button;
    private final IntConsumer handler;
    private int editingRow = -1;

    public TableSingleActionCell(String text, IntConsumer handler) {
        this.handler = handler;
        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        this.panel.setBackground(UIStyles.PANEL);
        this.button = UIStyles.ghostButton(text);
        this.panel.add(button);

        button.addActionListener(e -> {
            if (editingRow >= 0) {
                handler.accept(editingRow);
            }
            fireEditingStopped();
        });
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
