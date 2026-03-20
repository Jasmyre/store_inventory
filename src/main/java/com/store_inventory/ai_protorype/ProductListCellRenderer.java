package com.store_inventory.ai_protorype;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import java.awt.Component;

public class ProductListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Product) {
            Product product = (Product) value;
            setText(product.getName() + " (" + product.getId() + ")");
        }
        setBackground(isSelected ? UIStyles.CARD : UIStyles.PANEL);
        setForeground(UIStyles.TEXT);
        setFont(UIStyles.BODY_FONT);
        return this;
    }
}
