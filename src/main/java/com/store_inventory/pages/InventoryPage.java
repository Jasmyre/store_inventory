package com.store_inventory.pages;

import com.store_inventory.models.Product;
import com.store_inventory.pages.components.UITheme;
import com.store_inventory.services.InventoryManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class InventoryPage extends JPanel implements Refreshable {
  private final InventoryManager inventory;
  private final JLabel totalProductsValue = new JLabel();
  private final JLabel inStockValue = new JLabel();
  private final JLabel lowStockValue = new JLabel();
  private final JLabel outStockValue = new JLabel();
  private final JPanel stockList = new JPanel();
  private final JTextField searchField = new JTextField();
  private String lastQuery = "";

  public InventoryPage(InventoryManager inventory) {
    this.inventory = inventory;
    setLayout(new BorderLayout());
    setBackground(UITheme.BACKGROUND);

    JPanel content = new JPanel(new BorderLayout(0, 24));
    content.setOpaque(false);
    content.setBorder(new EmptyBorder(40, 20, 40, 20));

    JPanel topRow = new JPanel(new BorderLayout());
    topRow.setOpaque(false);

    JPanel headerText = new JPanel();
    headerText.setOpaque(false);
    headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));

    JLabel title = new JLabel("Inventory Overview");
    title.setFont(
        UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    UITheme.themeLabel(title);
    JLabel description = new JLabel(
        "Track stock levels, low items, and availability across the store.");
    description.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(description);

    headerText.add(title);
    headerText.add(Box.createVerticalStrut(6));
    headerText.add(description);
    topRow.add(headerText, BorderLayout.WEST);

    JPanel stats = new JPanel(new GridLayout(1, 4, 12, 12));
    stats.setOpaque(false);
    stats.add(statCard("Total Products", totalProductsValue));
    stats.add(statCard("In Stock", inStockValue));
    stats.add(statCard("Low Stock", lowStockValue));
    stats.add(statCard("Out of Stock", outStockValue));
    stats.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel stockLevels = UITheme.cardPanel();
    stockLevels.setLayout(new BoxLayout(stockLevels, BoxLayout.Y_AXIS));
    stockLevels.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel stockTitle = new JLabel("Inventory Items");
    stockTitle.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    stockTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(stockTitle);
    JLabel stockBrief = new JLabel(
        "Review stock, reorder levels, and status for each product.");
    stockBrief.setFont(UITheme.SUBTITLE_FONT);
    stockBrief.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(stockBrief);

    JPanel searchPanel = new JPanel();
    searchPanel.setOpaque(false);
    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
    searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel searchLabel = new JLabel("Search product by name or SKU:");
    searchLabel.setFont(UITheme.SUBTITLE_FONT);
    searchLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(searchLabel);
    JPanel searchRow = new JPanel(new BorderLayout(8, 0));
    searchRow.setOpaque(false);
    searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);
    JButton searchButton = UITheme.secondaryButton("Search");
    searchButton.setFont(UITheme.SUBTITLE_FONT);
    searchButton.setMargin(new Insets(8, 14, 8, 14));
    UITheme.themeTextField(searchField);
    Border line = UITheme.roundedBorder(UITheme.BORDER, 1, 12);
    Border padding = new EmptyBorder(6, 16, 6, 16);
    searchField.setBorder(new CompoundBorder(line, padding));
    searchField.setFont(UITheme.LABEL_FONT);
    searchRow.add(searchButton, BorderLayout.WEST);
    searchRow.add(searchField, BorderLayout.CENTER);
    searchButton.addActionListener(e -> {
      lastQuery = searchField.getText().trim();
      applyFilter(lastQuery);
    });

    searchPanel.add(searchLabel);
    searchPanel.add(Box.createVerticalStrut(6));
    searchPanel.add(searchRow);

    stockLevels.add(stockTitle);
    stockLevels.add(Box.createVerticalStrut(4));
    stockLevels.add(stockBrief);
    stockLevels.add(Box.createVerticalStrut(16));
    stockLevels.add(searchPanel);
    stockLevels.add(Box.createVerticalStrut(16));

    stockList.setOpaque(false);
    stockList.setLayout(new BoxLayout(stockList, BoxLayout.Y_AXIS));
    stockLevels.add(stockList);
    Dimension stockPreferred = stockLevels.getPreferredSize();
    stockLevels.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, stockPreferred.height));

    JPanel body = new JPanel();
    body.setOpaque(false);
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.add(stats);
    body.add(Box.createVerticalStrut(16));
    body.add(stockLevels);
    body.setAlignmentX(Component.LEFT_ALIGNMENT);

    content.add(topRow, BorderLayout.NORTH);
    content.add(body, BorderLayout.CENTER);

    JScrollPane scroll = new JScrollPane(content);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);
    UITheme.themeScrollPane(scroll);

    add(scroll, BorderLayout.CENTER);
    refresh();
  }

  private JPanel statCard(String label, JLabel valueLabel) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    valueLabel.setFont(UITheme.TITLE_FONT);
    valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(valueLabel);
    JLabel labelLabel = new JLabel(label);
    labelLabel.setFont(UITheme.SUBTITLE_FONT);
    labelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(labelLabel);
    card.add(valueLabel);
    card.add(Box.createVerticalStrut(6));
    card.add(labelLabel);
    return card;
  }

  private JPanel inventoryRow(String name, String sku, String category,
                              int stock, int reorderLevel) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BorderLayout(12, 0));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel left = new JPanel();
    left.setOpaque(false);
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

    JLabel nameLabel = new JLabel(name);
    nameLabel.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    UITheme.themeLabel(nameLabel);
    JLabel metaLabel = new JLabel("SKU: " + sku + "  |  Category: " + category);
    metaLabel.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(metaLabel);
    JLabel stockLabel =
        new JLabel("Stock: " + stock + "  |  Reorder Level: " + reorderLevel);
    stockLabel.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(stockLabel);

    left.add(nameLabel);
    left.add(Box.createVerticalStrut(4));
    left.add(metaLabel);
    left.add(Box.createVerticalStrut(4));
    left.add(stockLabel);

    JButton status = UITheme.secondaryButton(stockStatus(stock, reorderLevel));
    status.setMargin(new Insets(8, 14, 8, 14));
    status.setEnabled(false);
    status.setHorizontalAlignment(SwingConstants.CENTER);
    Dimension statusSize = new Dimension(150, 25);
    status.setPreferredSize(statusSize);

    card.add(left, BorderLayout.CENTER);
    card.add(status, BorderLayout.EAST);
    Dimension preferred = card.getPreferredSize();
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferred.height));
    return card;
  }

  private String stockStatus(int stock, int reorderLevel) {
    if (stock <= 0) {
      return "Out of Stock";
    }
    if (stock <= reorderLevel) {
      return "Low Stock";
    }
    return "In Stock";
  }

  @Override
  public void refresh() {
    totalProductsValue.setText(String.valueOf(inventory.getAllProducts().size()));
    inStockValue.setText(String.valueOf(inventory.getInStockCount()));
    lowStockValue.setText(String.valueOf(inventory.getLowStockCount()));
    outStockValue.setText(String.valueOf(inventory.getOutOfStockCount()));
    applyFilter(lastQuery);
  }

  private void applyFilter(String query) {
    String lowered = query == null ? "" : query.trim().toLowerCase();
    List<Product> products = inventory.getAllProducts();
    List<Product> filtered = new ArrayList<>();
    if (lowered.isBlank()) {
      filtered.addAll(products);
    } else {
      for (Product product : products) {
        if (product.getName().toLowerCase().contains(lowered)
            || product.getSku().toLowerCase().contains(lowered)) {
          filtered.add(product);
        }
      }
    }
    renderList(filtered);
  }

  private void renderList(List<Product> products) {
    stockList.removeAll();
    for (Product product : products) {
      stockList.add(inventoryRow(product.getName(), product.getSku(),
                                 product.getCategory(), product.getQuantity(),
                                 product.getReorderLevel()));
      stockList.add(Box.createVerticalStrut(12));
    }
    stockList.revalidate();
    stockList.repaint();
  }
}
