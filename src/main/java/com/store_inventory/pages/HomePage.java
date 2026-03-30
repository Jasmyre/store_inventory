package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;
import com.store_inventory.services.InventoryManager;
import com.store_inventory.services.SalesManager;
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomePage extends JPanel implements Refreshable {
  private static final DecimalFormat CURRENCY = new DecimalFormat("#,##0.00");
  private final InventoryManager inventory;
  private final SalesManager sales;
  private final JLabel productsValue = new JLabel();
  private final JLabel inventoryValue = new JLabel();
  private final JLabel salesValue = new JLabel();

  public HomePage(InventoryManager inventory, SalesManager sales) {
    this.inventory = inventory;
    this.sales = sales;
    setLayout(new BorderLayout());
    setBackground(UITheme.BACKGROUND);

    JPanel content = new JPanel(new GridLayout(1, 2, 16, 16));
    content.setOpaque(false);
    content.setBorder(new EmptyBorder(40, 20, 40, 20));

    JPanel left = new JPanel();
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
    left.setBackground(UITheme.BACKGROUND);

    JLabel welcome = new JLabel("Welcome to the System");
    welcome.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel hint = new JLabel("Here is a quick overview of your store.");
    hint.setFont(UITheme.SUBTITLE_FONT);
    hint.setForeground(UITheme.MUTED_TEXT);
    hint.setAlignmentX(Component.LEFT_ALIGNMENT);

    left.add(welcome);
    left.add(Box.createVerticalStrut(8));
    left.add(hint);
    left.add(Box.createVerticalStrut(12));

    JPanel statsPanel = UITheme.cardPanel();
    statsPanel.setOpaque(false);
    statsPanel.setBorder(null);
    statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
    statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    productsValue.setFont(UITheme.customFont(UITheme.FONT_FAMILY,
                                             UITheme.FONT_WEIGHT_LABEL, 20));
    statsPanel.add(productsValue);
    statsPanel.add(Box.createVerticalStrut(6));
    inventoryValue.setFont(UITheme.customFont(UITheme.FONT_FAMILY,
                                              UITheme.FONT_WEIGHT_LABEL, 20));
    statsPanel.add(inventoryValue);
    statsPanel.add(Box.createVerticalStrut(6));
    salesValue.setFont(UITheme.customFont(UITheme.FONT_FAMILY,
                                          UITheme.FONT_WEIGHT_LABEL, 20));
    statsPanel.add(salesValue);
    left.add(statsPanel);
    left.add(Box.createVerticalStrut(12));

    JPanel right = UITheme.cardPanel();
    right.setLayout(new BorderLayout());
    ImageIcon icon = new ImageIcon(getClass().getResource("/assets/image.png"));
    Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);

    JLabel imageLabel = new JLabel(new ImageIcon(img));
    right.add(imageLabel, BorderLayout.CENTER);

    content.add(left);
    content.add(right);

    JScrollPane scroll = new JScrollPane(content);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    add(scroll, BorderLayout.CENTER);
    refresh();
  }

  @Override
  public void refresh() {
    productsValue.setText("Total Products: " + inventory.getAllProducts().size());
    inventoryValue.setText("Total Inventory: " + inventory.getTotalUnits());
    salesValue.setText("Total Sales: " + formatCurrency(sales.getTotalRevenue()));
  }

  private String formatCurrency(double amount) {
    return "PHP " + CURRENCY.format(amount);
  }
}
