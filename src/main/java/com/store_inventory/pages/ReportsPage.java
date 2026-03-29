package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReportsPage extends JPanel {
  private final NavigationHandler handler;

  public ReportsPage(NavigationHandler handler) {
    this.handler = handler;
    setLayout(new BorderLayout());
    setBackground(UITheme.BACKGROUND);

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBorder(new EmptyBorder(40, 20, 40, 20));

    JPanel topRow = new JPanel(new BorderLayout());
    topRow.setOpaque(false);
    topRow.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel headerText = new JPanel();
    headerText.setOpaque(false);
    headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
    headerText.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel title = new JLabel("Reports");
    title.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    JLabel description = new JLabel("Generate and review detailed product, sales, and inventory reports.");
    description.setFont(UITheme.SUBTITLE_FONT);
    description.setForeground(UITheme.MUTED_TEXT);

    headerText.add(title);
    headerText.add(Box.createVerticalStrut(6));
    headerText.add(description);
    topRow.add(headerText, BorderLayout.WEST);

    content.add(topRow);
    content.add(Box.createVerticalStrut(16));

    CardLayout detailLayout = new CardLayout();
    JPanel detailCards = new JPanel(detailLayout);
    detailCards.setOpaque(false);
    detailCards.setAlignmentX(Component.LEFT_ALIGNMENT);
    detailCards.add(productReportPanel(), "product");
    detailCards.add(salesReportPanel(), "sales");
    detailCards.add(inventoryReportPanel(), "inventory");

    content.add(reportCard("Product Report",
                           "View all product listings and pricing.",
                           "View Report", "product", detailLayout, detailCards));
    content.add(Box.createVerticalStrut(12));
    content.add(reportCard("Sales Report",
                           "Review revenue, sales totals, and trends.",
                           "View Report", "sales", detailLayout, detailCards));
    content.add(Box.createVerticalStrut(12));
    content.add(reportCard("Inventory Report",
                           "Monitor stock levels, low stock, and value.",
                           "View Report", "inventory", detailLayout, detailCards));
    content.add(Box.createVerticalStrut(20));

    JPanel detailCardWrapper = UITheme.cardPanel();
    detailCardWrapper.setLayout(new BorderLayout());
    detailCardWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
    detailCardWrapper.add(detailCards, BorderLayout.CENTER);
    content.add(detailCardWrapper);

    JScrollPane scroll = new JScrollPane(content);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    add(scroll, BorderLayout.CENTER);
  }

  private JPanel reportCard(String title, String description, String action,
                            String cardKey, CardLayout detailLayout,
                            JPanel detailCards) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BorderLayout(12, 0));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel left = new JPanel();
    left.setOpaque(false);
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
    left.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    JLabel descLabel = new JLabel(description);
    descLabel.setFont(UITheme.SUBTITLE_FONT);
    descLabel.setForeground(UITheme.MUTED_TEXT);
    left.add(titleLabel);
    left.add(Box.createVerticalStrut(4));
    left.add(descLabel);

    JButton view = UITheme.secondaryButton(action);
    view.addActionListener(e -> detailLayout.show(detailCards, cardKey));

    card.add(left, BorderLayout.CENTER);
    card.add(view, BorderLayout.EAST);
    return card;
  }

  private JPanel productReportPanel() {
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(detailHeader("Product List Report"));
    panel.add(Box.createVerticalStrut(10));
    panel.add(tableSection(new String[] {"Name", "SKU", "Category",
                                         "Quantity", "Price"},
                           new String[][] {
                               {"Wireless Earbuds", "AUD-4312", "Audio",
                                "32", "PHP 799"},
                               {"Bluetooth Speaker", "AUD-2089", "Audio", "8",
                                "PHP 1,299"},
                               {"Phone Case", "ACC-7721", "Accessories",
                                "68", "PHP 249"},
                           }));
    return panel;
  }

  private JPanel salesReportPanel() {
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(detailHeader("Sales Report"));
    panel.add(Box.createVerticalStrut(10));
    panel.add(summaryCard("Total Revenue", "PHP 24,560"));
    panel.add(Box.createVerticalStrut(10));
    panel.add(tableSection(
        new String[] {"Date", "Product", "Quantity", "Unit Price", "Total"},
        new String[][] {
            {"2026-03-19", "Wireless Mouse", "2", "PHP 799", "PHP 1,598"},
            {"2026-03-20", "USB-C Cable", "5", "PHP 199", "PHP 995"},
        }));
    return panel;
  }

  private JPanel inventoryReportPanel() {
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(detailHeader("Inventory Report"));
    panel.add(Box.createVerticalStrut(10));
    panel.add(summaryCard("Total Inventory Value", "PHP 152,840"));
    panel.add(Box.createVerticalStrut(10));
    panel.add(tableSection(new String[] {"Name", "SKU", "Category", "Quantity",
                                         "Unit Price", "Total Value",
                                         "Status"},
                           new String[][] {
                               {"Laptop Stand", "OFF-3341", "Office", "7",
                                "PHP 1,199", "PHP 8,393", "In Stock"},
                               {"Smart Bulb", "HME-1182", "Home", "3",
                                "PHP 499", "PHP 1,497", "Low"},
                               {"Mechanical Keyboard", "ACC-5210",
                                "Accessories", "0", "PHP 2,499",
                                "PHP 0", "Out"},
                           }));
    return panel;
  }

  private JPanel summaryCard(String title, String amount) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.setBackground(new Color(230, 236, 242));

    JLabel label = new JLabel(title);
    label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    label.setForeground(UITheme.DARK_TEXT);
    JLabel value = new JLabel(amount);
    value.setFont(UITheme.customFont(UITheme.FONT_FAMILY, Font.BOLD, 24));
    value.setForeground(UITheme.PRIMARY_TEXT);

    card.add(label);
    card.add(Box.createVerticalStrut(6));
    card.add(value);

    return card;
  }

  private JPanel detailHeader(String title) {
    JPanel header = new JPanel();
    header.setOpaque(false);
    header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel text = new JPanel();
    text.setOpaque(false);
    text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
    text.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel label = new JLabel(title);
    label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel generated = new JLabel("Generated on 03/28/2026 at 05:17:50 PM");
    generated.setFont(UITheme.SUBTITLE_FONT);
    generated.setForeground(UITheme.MUTED_TEXT);
    generated.setAlignmentX(Component.LEFT_ALIGNMENT);

    text.add(label);
    text.add(Box.createVerticalStrut(4));
    text.add(generated);

    JButton action = UITheme.secondaryButton("Export Data");
    action.setAlignmentY(Component.TOP_ALIGNMENT);

    header.add(text);
    header.add(Box.createHorizontalGlue());
    header.add(action);
    return header;
  }

  private JPanel tableSection(String[] headers, String[][] rows) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);

    Font headerFont = UITheme.LABEL_FONT.deriveFont(Font.BOLD, 14f);
    Font cellFont = UITheme.LABEL_FONT.deriveFont(14f);

    JPanel headerRow = new JPanel(new GridLayout(1, headers.length, 4, 0));
    headerRow.setOpaque(false);
    for (String header : headers) {
      JLabel label = new JLabel(header);
      label.setFont(headerFont);
      label.setForeground(UITheme.DARK_TEXT);
      headerRow.add(label);
    }
    card.add(headerRow);
    card.add(Box.createVerticalStrut(6));

    for (String[] row : rows) {
      JPanel rowPanel = new JPanel(new GridLayout(1, headers.length, 4, 0));
      rowPanel.setOpaque(false);
      for (String cell : row) {
        JLabel label = new JLabel(cell);
        label.setFont(cellFont);
        label.setForeground(UITheme.MUTED_TEXT);
        rowPanel.add(label);
      }
      card.add(rowPanel);
      card.add(Box.createVerticalStrut(4));
    }
    return card;
  }
}

