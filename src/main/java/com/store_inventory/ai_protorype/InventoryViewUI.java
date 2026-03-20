package com.store_inventory.ai_protorype;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class InventoryViewUI extends JPanel {
    private static final int LOW_STOCK_THRESHOLD = 10;

    private final InventoryManager inventory;
    private final JTable table;
    private final DefaultTableModel model;
    private final List<Product> tableProducts;
    private final JLabel detailLabel;
    private final javax.swing.JTextField searchField;
    private StatusFilter statusFilter;

    public InventoryViewUI(InventoryManager inventory) {
        this.inventory = inventory;
        this.tableProducts = new ArrayList<>();
        this.statusFilter = StatusFilter.ALL;
        setBackground(UIStyles.BG);
        setLayout(new BorderLayout(16, 16));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIStyles.BG);
        header.add(UIStyles.titleLabel("View Inventory"), BorderLayout.WEST);
        header.add(UIStyles.mutedLabel("Complete overview of your inventory status."), BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        JPanel detailsPanel = UIStyles.cardPanel();
        detailsPanel.setLayout(new BorderLayout(8, 8));
        detailLabel = UIStyles.mutedLabel("Select a product to see details.");
        detailsPanel.add(UIStyles.headerLabel("Details"), BorderLayout.NORTH);
        detailsPanel.add(detailLabel, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new BorderLayout(8, 8));
        filterPanel.setBackground(UIStyles.BG);
        searchField = UIStyles.styledField();
        searchField.setToolTipText("Search by name or ID");
        filterPanel.add(searchField, BorderLayout.CENTER);

        JPanel filterButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filterButtons.setBackground(UIStyles.BG);
        JToggleButton allBtn = filterButton("All");
        JToggleButton inStockBtn = filterButton("In Stock");
        JToggleButton lowStockBtn = filterButton("Low Stock");
        JToggleButton outStockBtn = filterButton("Out of Stock");

        ButtonGroup group = new ButtonGroup();
        group.add(allBtn);
        group.add(inStockBtn);
        group.add(lowStockBtn);
        group.add(outStockBtn);
        allBtn.setSelected(true);

        allBtn.addActionListener(e -> setStatusFilter(StatusFilter.ALL));
        inStockBtn.addActionListener(e -> setStatusFilter(StatusFilter.IN_STOCK));
        lowStockBtn.addActionListener(e -> setStatusFilter(StatusFilter.LOW_STOCK));
        outStockBtn.addActionListener(e -> setStatusFilter(StatusFilter.OUT_OF_STOCK));

        filterButtons.add(allBtn);
        filterButtons.add(inStockBtn);
        filterButtons.add(lowStockBtn);
        filterButtons.add(outStockBtn);
        filterPanel.add(filterButtons, BorderLayout.SOUTH);

        JPanel topSection = new JPanel(new GridLayout(2, 1, 8, 8));
        topSection.setBackground(UIStyles.BG);
        topSection.add(detailsPanel);
        topSection.add(filterPanel);

        String[] columns = {"Name", "ID", "Quantity", "Status", "Category", "Actions"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        table = UIStyles.styleTable(new JTable(model));
        table.setRowHeight(34);
        table.getSelectionModel().addListSelectionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0 && selected < tableProducts.size()) {
                showProductDetails(tableProducts.get(selected).getId());
            }
        });

        TableSingleActionCell actionCell = new TableSingleActionCell("View", row -> {
            if (row >= 0 && row < tableProducts.size()) {
                showProductDetails(tableProducts.get(row).getId());
            }
        });
        TableColumn actionColumn = table.getColumnModel().getColumn(5);
        actionColumn.setCellRenderer(actionCell);
        actionColumn.setCellEditor(actionCell);
        actionColumn.setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(UIStyles.PANEL);
        scroll.setBorder(UIStyles.roundedBorder());

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBackground(UIStyles.BG);
        content.add(topSection, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                renderTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                renderTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                renderTable();
            }
        });
    }

    public void refresh() {
        renderTable();
    }

    public void renderTable() {
        String query = searchField.getText().trim().toLowerCase();
        tableProducts.clear();
        model.setRowCount(0);
        for (Product p : inventory.listProducts()) {
            if (!query.isEmpty()
                    && !p.getName().toLowerCase().contains(query)
                    && !p.getId().toLowerCase().contains(query)) {
                continue;
            }
            Status status = computeStatus(p);
            if (!statusFilter.matches(status)) {
                continue;
            }
            tableProducts.add(p);
            model.addRow(new Object[]{
                    p.getName(),
                    p.getId(),
                    p.getQuantity(),
                    status.label,
                    p.getCategory(),
                    ""
            });
        }
        SwingUtilities.invokeLater(this::repaint);
    }

    public void showProductDetails(String productId) {
        Product product = inventory.findProductById(productId);
        if (product == null) {
            detailLabel.setText("Product not found.");
            return;
        }
        Status status = computeStatus(product);
        detailLabel.setText(String.format(
                "%s (%s) | Qty: %d | Price: %.2f | Status: %s",
                product.getName(),
                product.getId(),
                product.getQuantity(),
                product.getPrice(),
                status.label
        ));
    }

    private Status computeStatus(Product product) {
        if (product.getQuantity() <= 0) {
            return Status.OUT_OF_STOCK;
        }
        if (product.getQuantity() < LOW_STOCK_THRESHOLD) {
            return Status.LOW_STOCK;
        }
        return Status.IN_STOCK;
    }

    private void setStatusFilter(StatusFilter filter) {
        this.statusFilter = filter;
        renderTable();
    }

    private JToggleButton filterButton(String text) {
        JToggleButton button = new JToggleButton(text);
        button.setBackground(UIStyles.PANEL);
        button.setForeground(UIStyles.TEXT);
        button.setFont(UIStyles.BODY_FONT);
        button.setBorder(UIStyles.roundedBorder());
        button.setFocusPainted(false);
        return button;
    }

    private enum Status {
        IN_STOCK("In Stock"),
        LOW_STOCK("Low Stock"),
        OUT_OF_STOCK("Out of Stock");

        private final String label;

        Status(String label) {
            this.label = label;
        }
    }

    private enum StatusFilter {
        ALL {
            @Override
            boolean matches(Status status) {
                return true;
            }
        },
        IN_STOCK {
            @Override
            boolean matches(Status status) {
                return status == Status.IN_STOCK;
            }
        },
        LOW_STOCK {
            @Override
            boolean matches(Status status) {
                return status == Status.LOW_STOCK;
            }
        },
        OUT_OF_STOCK {
            @Override
            boolean matches(Status status) {
                return status == Status.OUT_OF_STOCK;
            }
        };

        abstract boolean matches(Status status);
    }
}
