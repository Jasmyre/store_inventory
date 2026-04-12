package com.store_inventory.pages;

import com.store_inventory.models.Product;
import com.store_inventory.models.SaleItem;
import com.store_inventory.models.SaleTransaction;
import com.store_inventory.pages.components.UITheme;
import com.store_inventory.services.InventoryManager;
import com.store_inventory.services.InventoryReport;
import com.store_inventory.services.SalesManager;
import com.store_inventory.services.SalesReport;
import com.store_inventory.services.ProductReport;
import java.io.IOException;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReportsPage extends JPanel implements Refreshable {
  private static final DecimalFormat CURRENCY = new DecimalFormat("#,##0.00");
  private static final DateTimeFormatter GENERATED_FORMAT =
      DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ss a");
  private static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final NavigationHandler handler;
  private final InventoryManager inventory;
  private final SalesManager sales;
  private final CardLayout detailLayout = new CardLayout();
  private final JPanel detailCards = new JPanel(detailLayout);

  public ReportsPage(NavigationHandler handler, InventoryManager inventory,
                     SalesManager sales) {
    this.handler = handler;
    this.inventory = inventory;
    this.sales = sales;
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
    UITheme.themeLabel(title);
    JLabel description = new JLabel("Generate and review detailed product, sales, and inventory reports.");
    description.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(description);

    headerText.add(title);
    headerText.add(Box.createVerticalStrut(6));
    headerText.add(description);
    topRow.add(headerText, BorderLayout.WEST);

    content.add(topRow);
    content.add(Box.createVerticalStrut(16));

    detailCards.setOpaque(false);
    detailCards.setAlignmentX(Component.LEFT_ALIGNMENT);

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
    UITheme.themeScrollPane(scroll);

    add(scroll, BorderLayout.CENTER);
    refresh();
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
    UITheme.themeLabel(titleLabel);
    JLabel descLabel = new JLabel(description);
    descLabel.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(descLabel);
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

    panel.add(detailHeader("Product List Report", () -> exportProductReport()));
    panel.add(Box.createVerticalStrut(10));
    List<Product> products = inventory.getAllProducts();
    String[][] rows = new String[products.size()][5];
    for (int i = 0; i < products.size(); i++) {
      Product p = products.get(i);
      rows[i] = new String[] {p.getName(), p.getSku(), p.getCategory(),
                              String.valueOf(p.getQuantity()),
                              formatCurrency(p.getPrice())};
    }
    panel.add(tableSection(new String[] {"Name", "SKU", "Category",
                                         "Quantity", "Price"}, rows));
    return panel;
  }

  private JPanel salesReportPanel() {
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(detailHeader("Sales Report", () -> exportSalesReport()));
    panel.add(Box.createVerticalStrut(10));
    panel.add(summaryCard("Total Revenue", formatCurrency(sales.getTotalRevenue())));
    panel.add(Box.createVerticalStrut(10));
    List<String[]> rows = new ArrayList<>();
    for (SaleTransaction transaction : sales.getAllTransactions()) {
      String date = transaction.getDate().format(DATE_FORMAT);
      for (SaleItem item : transaction.getItems()) {
        Product product = item.getProduct();
        String name = product != null ? product.getName() : "Unknown";
        rows.add(new String[] {date, name, String.valueOf(item.getQuantity()),
                               formatCurrency(item.getUnitPrice()),
                               formatCurrency(item.getSubtotal())});
      }
    }
    panel.add(tableSection(
        new String[] {"Date", "Product", "Quantity", "Unit Price", "Total"},
        rows.toArray(new String[0][0])));
    return panel;
  }

  private JPanel inventoryReportPanel() {
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(detailHeader("Inventory Report", () -> exportInventoryReport()));
    panel.add(Box.createVerticalStrut(10));
    panel.add(summaryCard("Total Inventory Value",
                          formatCurrency(inventory.getTotalInventoryValue())));
    panel.add(Box.createVerticalStrut(10));
    List<Product> products = inventory.getAllProducts();
    String[][] rows = new String[products.size()][7];
    for (int i = 0; i < products.size(); i++) {
      Product p = products.get(i);
      rows[i] = new String[] {p.getName(), p.getSku(), p.getCategory(),
                              String.valueOf(p.getQuantity()),
                              formatCurrency(p.getPrice()),
                              formatCurrency(p.getInventoryValue()),
                              stockStatus(p)};
    }
    panel.add(tableSection(new String[] {"Name", "SKU", "Category", "Quantity",
                                         "Unit Price", "Total Value",
                                         "Status"}, rows));
    return panel;
  }

  private JPanel summaryCard(String title, String amount) {
    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.setBackground(UITheme.SUMMARY_CARD_BACKGROUND);

    JLabel label = new JLabel(title);
    label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    UITheme.themeLabel(label);
    JLabel value = new JLabel(amount);
    value.setFont(UITheme.customFont(UITheme.FONT_FAMILY, Font.BOLD, 24));
    UITheme.themeLabel(value);

    card.add(label);
    card.add(Box.createVerticalStrut(6));
    card.add(value);

    return card;
  }

  private JPanel detailHeader(String title, Runnable onExport) {
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
    UITheme.themeLabel(label);
    JLabel generated = new JLabel(
        "Generated on " + LocalDateTime.now().format(GENERATED_FORMAT));
    generated.setFont(UITheme.SUBTITLE_FONT);
    generated.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(generated);

    text.add(label);
    text.add(Box.createVerticalStrut(4));
    text.add(generated);

    JButton action = UITheme.secondaryButton("Export Data");
    action.setAlignmentY(Component.TOP_ALIGNMENT);
    action.addActionListener(e -> onExport.run());

    header.add(text);
    header.add(Box.createHorizontalGlue());
    header.add(action);
    return header;
  }

  private JPanel tableSection(String[] headers, String[][] rows) {
    JPanel stack = new JPanel();
    stack.setOpaque(false);
    stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
    stack.setAlignmentX(Component.LEFT_ALIGNMENT);

    Font headerFont = UITheme.LABEL_FONT.deriveFont(Font.BOLD, 14f);
    Font cellFont = UITheme.LABEL_FONT.deriveFont(14f);

    JPanel headerRow = UITheme.cardPanel();
    headerRow.setLayout(new GridLayout(1, headers.length, 8, 0));
    for (String header : headers) {
      JLabel label = new JLabel(header);
      label.setFont(headerFont);
      UITheme.themeLabel(label);
      headerRow.add(label);
    }
    stack.add(headerRow);
    stack.add(Box.createVerticalStrut(6));

    for (String[] row : rows) {
      JPanel rowPanel = UITheme.cardPanel();
      rowPanel.setLayout(new GridLayout(1, headers.length, 8, 0));
      for (String cell : row) {
        JLabel label = new JLabel(cell);
        label.setFont(cellFont);
        UITheme.themeLabel(label);
        rowPanel.add(label);
      }
      stack.add(rowPanel);
      stack.add(Box.createVerticalStrut(6));
    }
    return stack;
  }

  @Override
  public void refresh() {
    detailCards.removeAll();
    detailCards.add(productReportPanel(), "product");
    detailCards.add(salesReportPanel(), "sales");
    detailCards.add(inventoryReportPanel(), "inventory");
    detailLayout.show(detailCards, "product");
    detailCards.revalidate();
    detailCards.repaint();
  }

  private String formatCurrency(double amount) {
    return "PHP " + CURRENCY.format(amount);
  }

  private String stockStatus(Product product) {
    if (product.getQuantity() <= 0) {
      return "Out";
    }
    if (product.isLowStock()) {
      return "Low";
    }
    return "In Stock";
  }

  private void exportProductReport() {
    try {
      String path = new ProductReport(inventory).generateReport();
      JOptionPane.showMessageDialog(this,
          "Product report exported to:\n" + path,
          "Export Complete",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
      showExportError(ex);
    }
  }

  private void exportSalesReport() {
    try {
      String path = new SalesReport(sales).generateReport();
      JOptionPane.showMessageDialog(this,
          "Sales report exported to:\n" + path,
          "Export Complete",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
      showExportError(ex);
    }
  }

  private void exportInventoryReport() {
    try {
      String path = new InventoryReport(inventory).generateReport();
      JOptionPane.showMessageDialog(this,
          "Inventory report exported to:\n" + path,
          "Export Complete",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
      showExportError(ex);
    }
  }

  private void showExportError(IOException ex) {
    JOptionPane.showMessageDialog(this,
        "Failed to export report:\n" + ex.getMessage(),
        "Export Failed",
        JOptionPane.ERROR_MESSAGE);
  }
}

