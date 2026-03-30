package com.store_inventory.pages;

import com.store_inventory.models.Product;
import com.store_inventory.models.SaleItem;
import com.store_inventory.models.SaleTransaction;
import com.store_inventory.pages.components.UITheme;
import com.store_inventory.services.InventoryManager;
import com.store_inventory.services.SalesManager;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReportDetailPage extends JPanel implements Refreshable {
  private static final DecimalFormat CURRENCY = new DecimalFormat("#,##0.00");
  private static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final InventoryManager inventory;
  private final SalesManager sales;
  private final JPanel content = new JPanel();

  public ReportDetailPage(InventoryManager inventory, SalesManager sales) {
    this.inventory = inventory;
    this.sales = sales;
    setLayout(new BorderLayout());
    setBackground(UITheme.BACKGROUND);

    content.setOpaque(false);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBorder(new EmptyBorder(20, 20, 20, 20));

    add(content, BorderLayout.NORTH);
    refresh();
  }

  private JPanel sectionHeader(String title, String actionText) {
    JPanel row = new JPanel(new BorderLayout());
    row.setOpaque(false);
    JLabel label = new JLabel(title);
    label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    JButton action = UITheme.secondaryButton(actionText);
    row.add(label, BorderLayout.WEST);
    row.add(action, BorderLayout.EAST);
    return row;
  }

  private JPanel tableSection(String[] headers, String[][] rows) {
    JPanel stack = new JPanel();
    stack.setOpaque(false);
    stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));

    JPanel headerRow = UITheme.cardPanel();
    headerRow.setLayout(new GridLayout(1, headers.length, 8, 0));
    for (String header : headers) {
      JLabel label = new JLabel(header);
      label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
      label.setForeground(UITheme.DARK_TEXT);
      headerRow.add(label);
    }
    stack.add(headerRow);
    stack.add(Box.createVerticalStrut(6));

    for (String[] row : rows) {
      JPanel rowPanel = UITheme.cardPanel();
      rowPanel.setLayout(new GridLayout(1, headers.length, 8, 0));
      for (String cell : row) {
        JLabel label = new JLabel(cell);
        label.setFont(UITheme.LABEL_FONT);
        label.setForeground(UITheme.MUTED_TEXT);
        rowPanel.add(label);
      }
      stack.add(rowPanel);
      stack.add(Box.createVerticalStrut(6));
    }
    return stack;
  }

  @Override
  public void refresh() {
    content.removeAll();
    content.add(sectionHeader("Product List Report", "Save as"));
    content.add(Box.createVerticalStrut(8));
    List<Product> products = inventory.getAllProducts();
    String[][] productRows = new String[products.size()][5];
    for (int i = 0; i < products.size(); i++) {
      Product p = products.get(i);
      productRows[i] = new String[] {p.getName(), stockStatus(p),
                                     p.getCategory(),
                                     String.valueOf(p.getQuantity()),
                                     formatCurrency(p.getPrice())};
    }
    content.add(tableSection(new String[] {"Product", "Stock", "Category",
                                           "Quantity", "Price"}, productRows));
    content.add(Box.createVerticalStrut(16));

    content.add(sectionHeader("Sales Report", "Save as"));
    content.add(Box.createVerticalStrut(8));
    List<String[]> salesRows = new ArrayList<>();
    for (SaleTransaction transaction : sales.getAllTransactions()) {
      String date = transaction.getDate().format(DATE_FORMAT);
      for (SaleItem item : transaction.getItems()) {
        Product product = item.getProduct();
        String name = product != null ? product.getName() : "Unknown";
        salesRows.add(new String[] {date, name,
                                    String.valueOf(item.getQuantity()),
                                    formatCurrency(item.getUnitPrice()),
                                    formatCurrency(item.getSubtotal())});
      }
    }
    content.add(tableSection(
        new String[] {"Date", "Product", "Quantity", "Unit Price", "Total"},
        salesRows.toArray(new String[0][0])));
    content.add(Box.createVerticalStrut(16));

    content.add(sectionHeader("Inventory Report", "Save as"));
    content.add(Box.createVerticalStrut(8));
    String[][] inventoryRows = new String[products.size()][3];
    for (int i = 0; i < products.size(); i++) {
      Product p = products.get(i);
      inventoryRows[i] = new String[] {p.getName(),
                                       String.valueOf(p.getQuantity()),
                                       formatCurrency(p.getInventoryValue())};
    }
    content.add(tableSection(new String[] {"Item", "Qty", "Value"},
                             inventoryRows));

    content.revalidate();
    content.repaint();
  }

  private String formatCurrency(double amount) {
    return "PHP " + CURRENCY.format(amount);
  }

  private String stockStatus(Product product) {
    if (product.getQuantity() <= 0) {
      return "Out of Stock";
    }
    if (product.isLowStock()) {
      return "Low";
    }
    return "In Stock";
  }
}
