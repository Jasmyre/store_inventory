package com.store_inventory.ai_protorype;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageProductsUI extends JPanel {
    private final InventoryManager inventory;
    private final JTable table;
    private final DefaultTableModel model;
    private final List<Product> tableProducts;
    private final DecimalFormat moneyFormat;

    public ManageProductsUI(InventoryManager inventory) {
        this.inventory = inventory;
        this.tableProducts = new ArrayList<>();
        this.moneyFormat = new DecimalFormat("#,##0.00");
        setBackground(UIStyles.BG);
        setLayout(new BorderLayout(16, 16));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIStyles.BG);
        JLabel title = UIStyles.titleLabel("Manage Products");
        JLabel subtitle = UIStyles.mutedLabel("Add, edit, or remove products from your inventory.");
        JPanel titleStack = new JPanel(new GridLayout(2, 1));
        titleStack.setBackground(UIStyles.BG);
        titleStack.add(title);
        titleStack.add(subtitle);
        header.add(titleStack, BorderLayout.WEST);

        JButton addButton = UIStyles.accentButton("Add Product");
        addButton.addActionListener(e -> openProductDialog(null));
        header.add(addButton, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        String[] columns = {"Name", "ID", "Price", "Quantity", "Category", "Actions"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        table = UIStyles.styleTable(new JTable(model));
        table.setRowHeight(34);
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(UIStyles.PANEL);
        scroll.setBorder(UIStyles.roundedBorder());

        TableActionCell actionCell = new TableActionCell("Edit", "Delete", (row, action) -> {
            if (row < 0 || row >= tableProducts.size()) {
                return;
            }
            Product product = tableProducts.get(row);
            if ("primary".equals(action)) {
                openProductDialog(product);
            } else {
                onDeleteClick(product.getId());
            }
        });

        TableColumn actionColumn = table.getColumnModel().getColumn(5);
        actionColumn.setCellRenderer(actionCell);
        actionColumn.setCellEditor(actionCell);
        actionColumn.setPreferredWidth(160);

        add(scroll, BorderLayout.CENTER);
    }

    public void refresh() {
        renderTable();
    }

    public void onAddClick(Map<String, Object> formData) {
        openProductDialog(null);
    }

    public void onUpdateClick(Map<String, Object> formData) {
        if (!tableProducts.isEmpty()) {
            openProductDialog(tableProducts.get(0));
        }
    }

    public void onDeleteClick(String productId) {
        if (!inventory.removeProduct(productId)) {
            Dialogs.error(this, "Delete failed", "Product not found.");
            return;
        }
        inventory.saveToStorage();
        renderTable();
    }

    public void renderTable() {
        tableProducts.clear();
        model.setRowCount(0);
        for (Product p : inventory.listProducts()) {
            tableProducts.add(p);
            model.addRow(new Object[]{
                    p.getName(),
                    p.getId(),
                    moneyFormat.format(p.getPrice()),
                    p.getQuantity(),
                    p.getCategory(),
                    ""
            });
        }
    }

    private void openProductDialog(Product existing) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, existing == null ? "Add Product" : "Edit Product", true);
        dialog.setBackground(UIStyles.BG);
        dialog.setLayout(new BorderLayout(12, 12));

        JPanel form = new JPanel(new GridLayout(5, 2, 12, 12));
        form.setBackground(UIStyles.BG);
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JTextField idField = UIStyles.styledField();
        JTextField nameField = UIStyles.styledField();
        JComboBox<Category> categoryBox = new JComboBox<>(Category.values());
        categoryBox.setBackground(UIStyles.PANEL);
        categoryBox.setForeground(UIStyles.TEXT);
        categoryBox.setFont(UIStyles.BODY_FONT);
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1_000_000.0, 0.5));
        priceSpinner.setFont(UIStyles.BODY_FONT);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1_000_000, 1));
        quantitySpinner.setFont(UIStyles.BODY_FONT);

        if (existing != null) {
            idField.setText(existing.getId());
            idField.setEnabled(false);
            nameField.setText(existing.getName());
            categoryBox.setSelectedItem(existing.getCategory());
            priceSpinner.setValue(existing.getPrice());
            quantitySpinner.setValue(existing.getQuantity());
        }

        form.add(UIStyles.mutedLabel("ID"));
        form.add(idField);
        form.add(UIStyles.mutedLabel("Name"));
        form.add(nameField);
        form.add(UIStyles.mutedLabel("Category"));
        form.add(categoryBox);
        form.add(UIStyles.mutedLabel("Price"));
        form.add(priceSpinner);
        form.add(UIStyles.mutedLabel("Quantity"));
        form.add(quantitySpinner);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(UIStyles.BG);
        JButton cancel = UIStyles.ghostButton("Cancel");
        JButton save = UIStyles.accentButton(existing == null ? "Add" : "Save");
        cancel.addActionListener(e -> dialog.dispose());
        save.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            Category category = (Category) categoryBox.getSelectedItem();
            double price = ((Number) priceSpinner.getValue()).doubleValue();
            int quantity = ((Number) quantitySpinner.getValue()).intValue();

            try {
                Product product = new Product(id, name, category, price, quantity);
                boolean result = existing == null ? inventory.addProduct(product) : inventory.updateProduct(product);
                if (!result) {
                    Dialogs.error(dialog, "Save failed", "A product with this ID already exists.");
                    return;
                }
                inventory.saveToStorage();
                renderTable();
                dialog.dispose();
            } catch (IllegalArgumentException ex) {
                Dialogs.error(dialog, "Validation error", ex.getMessage());
            }
        });

        actions.add(cancel);
        actions.add(save);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(actions, BorderLayout.SOUTH);
        dialog.setPreferredSize(new Dimension(420, 340));
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
