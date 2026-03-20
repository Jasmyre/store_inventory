package com.store_inventory.ai_protorype;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class UpdateStockUI extends JPanel {
    private final InventoryManager inventory;
    private final JComboBox<Product> restockCombo;
    private final JComboBox<Product> saleCombo;
    private final JLabel restockQtyLabel;
    private final JLabel saleQtyLabel;
    private final JSpinner restockSpinner;
    private final JSpinner saleSpinner;

    public UpdateStockUI(InventoryManager inventory) {
        this.inventory = inventory;
        setBackground(UIStyles.BG);
        setLayout(new BorderLayout(16, 16));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIStyles.BG);
        header.add(UIStyles.titleLabel("Update Stock"), BorderLayout.WEST);
        header.add(UIStyles.mutedLabel("Adjust inventory quantities for your products."), BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 16, 16));
        content.setBackground(UIStyles.BG);

        restockCombo = new JComboBox<>();
        saleCombo = new JComboBox<>();
        styleCombo(restockCombo);
        styleCombo(saleCombo);
        restockQtyLabel = UIStyles.mutedLabel("Current: --");
        saleQtyLabel = UIStyles.mutedLabel("Current: --");
        restockSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1_000_000, 1));
        saleSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1_000_000, 1));
        restockSpinner.setFont(UIStyles.BODY_FONT);
        saleSpinner.setFont(UIStyles.BODY_FONT);

        JPanel restockPanel = buildStockPanel(
                "Restock",
                "Increase stock for selected product.",
                restockCombo,
                restockQtyLabel,
                restockSpinner,
                () -> {
                    Product product = (Product) restockCombo.getSelectedItem();
                    if (product == null) {
                        Dialogs.error(this, "Restock failed", "No product selected.");
                        return;
                    }
                    int amount = ((Number) restockSpinner.getValue()).intValue();
                    if (inventory.restockProduct(product.getId(), amount)) {
                        inventory.saveToStorage();
                        refreshProducts();
                    }
                }
        );

        JPanel salePanel = buildStockPanel(
                "Record Sale",
                "Decrease stock for selected product.",
                saleCombo,
                saleQtyLabel,
                saleSpinner,
                () -> {
                    Product product = (Product) saleCombo.getSelectedItem();
                    if (product == null) {
                        Dialogs.error(this, "Sale failed", "No product selected.");
                        return;
                    }
                    int amount = ((Number) saleSpinner.getValue()).intValue();
                    try {
                        if (inventory.recordSale(product.getId(), amount)) {
                            inventory.saveToStorage();
                            refreshProducts();
                        }
                    } catch (IllegalArgumentException ex) {
                        Dialogs.error(this, "Sale failed", ex.getMessage());
                    }
                }
        );

        content.add(restockPanel);
        content.add(salePanel);
        add(content, BorderLayout.CENTER);

        restockCombo.addActionListener(e -> updateQuantityLabel(restockCombo, restockQtyLabel));
        saleCombo.addActionListener(e -> updateQuantityLabel(saleCombo, saleQtyLabel));
    }

    public void refresh() {
        refreshProducts();
    }

    public void onRestockClick(String productId, int amount) {
        inventory.restockProduct(productId, amount);
        inventory.saveToStorage();
        refreshProducts();
    }

    public void onRecordSaleClick(String productId, int amount) {
        inventory.recordSale(productId, amount);
        inventory.saveToStorage();
        refreshProducts();
    }

    private JPanel buildStockPanel(String title, String subtitle, JComboBox<Product> combo, JLabel qtyLabel,
                                   JSpinner spinner, Runnable action) {
        JPanel panel = UIStyles.cardPanel();
        panel.setLayout(new BorderLayout(12, 12));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(UIStyles.CARD);
        header.add(UIStyles.headerLabel(title));
        header.add(UIStyles.mutedLabel(subtitle));

        JPanel form = new JPanel(new GridLayout(4, 1, 8, 8));
        form.setBackground(UIStyles.CARD);
        form.add(combo);
        form.add(qtyLabel);
        form.add(UIStyles.mutedLabel("Set quantity"));
        form.add(spinner);

        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBackground(UIStyles.CARD);
        actionPanel.add(UIStyles.accentButton(title), BorderLayout.CENTER);
        actionPanel.getComponent(0).setFont(UIStyles.HEADER_FONT);
        ((javax.swing.JButton) actionPanel.getComponent(0)).addActionListener(e -> action.run());

        panel.add(header, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void styleCombo(JComboBox<Product> combo) {
        combo.setBackground(UIStyles.PANEL);
        combo.setForeground(UIStyles.TEXT);
        combo.setFont(UIStyles.BODY_FONT);
        combo.setRenderer(new ProductListCellRenderer());
    }

    private void refreshProducts() {
        List<Product> products = inventory.listProducts();
        restockCombo.removeAllItems();
        saleCombo.removeAllItems();
        for (Product p : products) {
            restockCombo.addItem(p);
            saleCombo.addItem(p);
        }
        updateQuantityLabel(restockCombo, restockQtyLabel);
        updateQuantityLabel(saleCombo, saleQtyLabel);
        SwingUtilities.invokeLater(this::repaint);
    }

    private void updateQuantityLabel(JComboBox<Product> combo, JLabel label) {
        Product product = (Product) combo.getSelectedItem();
        if (product == null) {
            label.setText("Current: --");
        } else {
            label.setText("Current: " + product.getQuantity());
        }
    }
}
