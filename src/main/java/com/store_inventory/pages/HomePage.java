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
    UITheme.themeLabel(welcome);

    JLabel hint = new JLabel("Here is a quick overview of your store.");
    hint.setFont(UITheme.SUBTITLE_FONT);
    hint.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(hint);

    JPanel introCard = UITheme.cardPanel();
    introCard.setLayout(new BoxLayout(introCard, BoxLayout.Y_AXIS));
    introCard.setAlignmentX(Component.LEFT_ALIGNMENT);
    introCard.add(welcome);
    introCard.add(Box.createVerticalStrut(8));
    introCard.add(hint);

    left.add(introCard);
    left.add(Box.createVerticalStrut(12));

    productsValue.setFont(UITheme.customFont(UITheme.FONT_FAMILY,
                                             UITheme.FONT_WEIGHT_LABEL, 22));
    UITheme.themeLabel(productsValue);
    inventoryValue.setFont(UITheme.customFont(UITheme.FONT_FAMILY,
                                              UITheme.FONT_WEIGHT_LABEL, 22));
    UITheme.themeLabel(inventoryValue);
    salesValue.setFont(UITheme.customFont(UITheme.FONT_FAMILY,
                                          UITheme.FONT_WEIGHT_LABEL, 22));
    UITheme.themeLabel(salesValue);

    JPanel statsGrid = new JPanel(new GridLayout(3, 1, 0, 10));
    statsGrid.setOpaque(false);
    statsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
    statsGrid.add(statCard("Total Products", productsValue));
    statsGrid.add(statCard("Total Inventory", inventoryValue));
    statsGrid.add(statCard("Total Sales", salesValue));
    left.add(statsGrid);

    JPanel right = UITheme.cardPanel();
    right.setLayout(new BorderLayout());
    ImageIcon icon = new ImageIcon(getClass().getResource("/assets/image.png"));
    Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);

    JLabel imageLabel = new JLabel(new ImageIcon(img));
    UITheme.themeLabel(imageLabel);
    right.add(imageLabel, BorderLayout.CENTER);

    content.add(left);
    content.add(right);

    JScrollPane scroll = new JScrollPane(content);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);
    UITheme.themeScrollPane(scroll);

    add(scroll, BorderLayout.CENTER);
    refresh();
  }

  private JPanel statCard(String title, JLabel valueLabel) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel label = new JLabel(title);
    label.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(label);

    valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);

    card.add(label);
    card.add(Box.createVerticalStrut(6));
    card.add(valueLabel);
    return card;
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
