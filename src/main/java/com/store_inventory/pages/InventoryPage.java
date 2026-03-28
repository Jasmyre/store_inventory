package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InventoryPage extends JPanel {
  public InventoryPage() {
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
    title.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    JLabel description = new JLabel("Track stock levels, low items, and availability across the store.");
    description.setFont(UITheme.SUBTITLE_FONT);
    description.setForeground(UITheme.MUTED_TEXT);

    headerText.add(title);
    headerText.add(Box.createVerticalStrut(6));
    headerText.add(description);
    topRow.add(headerText, BorderLayout.WEST);

    JPanel stats = new JPanel(new GridLayout(1, 4, 12, 12));
    stats.setOpaque(false);
    stats.add(statCard("Total Products", "152"));
    stats.add(statCard("In Stock", "96"));
    stats.add(statCard("Low Stock", "18"));
    stats.add(statCard("Out of Stock", "5"));

    JPanel stockLevels = UITheme.cardPanel();
    stockLevels.setLayout(new BoxLayout(stockLevels, BoxLayout.Y_AXIS));

    JLabel stockTitle = new JLabel("Inventory Items");
    stockTitle.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    stockTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel stockBrief = new JLabel("Review stock, reorder levels, and status for each product.");
    stockBrief.setFont(UITheme.SUBTITLE_FONT);
    stockBrief.setForeground(UITheme.MUTED_TEXT);
    stockBrief.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel searchPanel = new JPanel();
    searchPanel.setOpaque(false);
    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
    searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel searchLabel = new JLabel("Search product by name or SKU:");
    searchLabel.setFont(UITheme.SUBTITLE_FONT);
    searchLabel.setForeground(UITheme.MUTED_TEXT);
    searchLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JTextField searchField = new JTextField();
    searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
    searchField.setFont(UITheme.LABEL_FONT);
    searchField.setAlignmentX(Component.LEFT_ALIGNMENT);

    searchPanel.add(searchLabel);
    searchPanel.add(Box.createVerticalStrut(6));
    searchPanel.add(searchField);

    stockLevels.add(stockTitle);
    stockLevels.add(Box.createVerticalStrut(4));
    stockLevels.add(stockBrief);
    stockLevels.add(Box.createVerticalStrut(16));
    stockLevels.add(searchPanel);
    stockLevels.add(Box.createVerticalStrut(16));

    stockLevels.add(inventoryRow("Wireless Mouse", "SKU-101", "Accessories", 12, 8));
    stockLevels.add(Box.createVerticalStrut(12));
    stockLevels.add(inventoryRow("USB-C Cable", "SKU-114", "Accessories", 48, 20));
    stockLevels.add(Box.createVerticalStrut(12));
    stockLevels.add(inventoryRow("Laptop Stand", "SKU-204", "Office", 7, 10));
    stockLevels.add(Box.createVerticalStrut(12));
    stockLevels.add(inventoryRow("Smart Bulb", "SKU-310", "Home", 0, 6));

    JPanel body = new JPanel();
    body.setOpaque(false);
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.add(stats);
    body.add(Box.createVerticalStrut(16));
    body.add(stockLevels);

    content.add(topRow, BorderLayout.NORTH);
    content.add(body, BorderLayout.CENTER);

    add(content, BorderLayout.CENTER);
  }

  private JPanel statCard(String label, String value) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(UITheme.TITLE_FONT);
    valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel labelLabel = new JLabel(label);
    labelLabel.setFont(UITheme.SUBTITLE_FONT);
    labelLabel.setForeground(UITheme.MUTED_TEXT);
    labelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
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
    JLabel metaLabel = new JLabel("SKU: " + sku + "  |  Category: " + category);
    metaLabel.setFont(UITheme.SUBTITLE_FONT);
    metaLabel.setForeground(UITheme.MUTED_TEXT);
    JLabel stockLabel = new JLabel("Stock: " + stock + "  |  Reorder Level: " + reorderLevel);
    stockLabel.setFont(UITheme.SUBTITLE_FONT);
    stockLabel.setForeground(UITheme.MUTED_TEXT);

    left.add(nameLabel);
    left.add(Box.createVerticalStrut(4));
    left.add(metaLabel);
    left.add(Box.createVerticalStrut(4));
    left.add(stockLabel);

    JButton status = UITheme.secondaryButton(stockStatus(stock, reorderLevel));
    status.setFont(UITheme.SUBTITLE_FONT);
    status.setMargin(new Insets(8, 14, 8, 14));
    status.setEnabled(false);

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
}
