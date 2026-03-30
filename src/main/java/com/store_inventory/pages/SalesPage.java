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
import java.util.UUID;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SalesPage extends JPanel implements Refreshable {
  private static final int TABLE_SCROLL_HEIGHT = 320;
  private static final DecimalFormat CURRENCY = new DecimalFormat("#,##0.00");
  private static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final InventoryManager inventory;
  private final SalesManager sales;
  private final JLabel totalSalesValue = new JLabel();
  private final JLabel totalRevenueValue = new JLabel();
  private final JLabel unitsSoldValue = new JLabel();
  private final JPanel tableBody = new JPanel();

  public SalesPage(InventoryManager inventory, SalesManager sales) {
    this.inventory = inventory;
    this.sales = sales;
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

    JLabel title = new JLabel("Sales Overview");
    title.setFont(
        UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    UITheme.themeLabel(title);
    JLabel description = new JLabel(
        "Review recent transactions and sales performance at a glance.");
    description.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(description);

    headerText.add(title);
    headerText.add(Box.createVerticalStrut(6));
    headerText.add(description);
    topRow.add(headerText, BorderLayout.WEST);

    JButton addSale = UITheme.primaryButton("Add Sale");
    Border line = UITheme.roundedBorder(UITheme.BORDER, 1, 12);
    Border padding = new EmptyBorder(12, 16, 12, 16);
    addSale.setBorder(new CompoundBorder(line, padding));
    addSale.addActionListener(e -> showSaleFormDialog());

    JPanel buttonWrapper = new JPanel(new GridBagLayout());
    buttonWrapper.setOpaque(false);
    buttonWrapper.add(addSale);

    topRow.add(buttonWrapper, BorderLayout.EAST);

    JPanel stats = new JPanel(new GridLayout(1, 3, 12, 12));
    stats.setOpaque(false);
    stats.add(statCard("Total Sales", totalSalesValue));
    stats.add(statCard("Total Revenue", totalRevenueValue));
    stats.add(statCard("Units Sold", unitsSoldValue));

    JPanel tableStack = new JPanel();
    tableStack.setOpaque(false);
    tableStack.setLayout(new BoxLayout(tableStack, BoxLayout.Y_AXIS));

    tableBody.setOpaque(false);
    tableBody.setLayout(new BoxLayout(tableBody, BoxLayout.Y_AXIS));
    tableStack.add(tableHeader());
    tableStack.add(Box.createVerticalStrut(8));
    JScrollPane tableScroll = new JScrollPane(tableBody);
    tableScroll.setBorder(null);
    tableScroll.setOpaque(false);
    tableScroll.getViewport().setOpaque(false);
    tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    tableScroll.getVerticalScrollBar().setUnitIncrement(16);
    tableScroll.setPreferredSize(new Dimension(0, TABLE_SCROLL_HEIGHT));
    UITheme.themeScrollPane(tableScroll);
    tableStack.add(tableScroll);

    JPanel body = new JPanel();
    body.setOpaque(false);
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.add(stats);
    body.add(Box.createVerticalStrut(16));
    body.add(tableStack);

    content.add(topRow, BorderLayout.NORTH);
    content.add(body, BorderLayout.CENTER);

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

  private JPanel tableHeader() {
    JPanel row = UITheme.cardPanel();
    row.setLayout(new GridLayout(1, 5, 8, 0));
    row.add(headerLabel("Date"));
    row.add(headerLabel("Product"));
    row.add(headerLabel("Qty"));
    row.add(headerLabel("Unit Price"));
    row.add(headerLabel("Total"));
    return row;
  }

  private JPanel tableRow(String date, String product, String qty, String unit,
                          String total) {
    JPanel row = UITheme.cardPanel();
    row.setLayout(new GridLayout(1, 5, 8, 0));
    row.add(cellLabel(date));
    row.add(cellLabel(product));
    row.add(cellLabel(qty));
    row.add(cellLabel(unit));
    row.add(cellLabel(total));
    return row;
  }

  private JLabel headerLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    UITheme.themeLabel(label);
    return label;
  }

  private JLabel cellLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT);
    UITheme.themeLabel(label);
    return label;
  }

  private void showSaleFormDialog() {
    Window owner = SwingUtilities.getWindowAncestor(this);
    JDialog dialog =
        new JDialog(owner, "Add Sale", Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel content = new JPanel(new BorderLayout(0, 16));
    content.setBorder(new EmptyBorder(20, 24, 20, 24));
    content.setBackground(UITheme.BACKGROUND);

    JLabel title = new JLabel("Add Sale");
    title.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 22));
    UITheme.themeLabel(title);
    content.add(title, BorderLayout.NORTH);

    JPanel formContainer = new JPanel();
    formContainer.setOpaque(false);
    formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));

    JPanel saleInfo = buildSectionPanel("Sale Information");
    JPanel pricing = buildSectionPanel("Pricing Details");

    JTextField dateField = createTextField("e.g. 2026-03-30", DATE_FORMAT.format(java.time.LocalDate.now()));
    dateField.setEnabled(false);
    JComboBox<Product> productBox = new JComboBox<>(
        inventory.getAllProducts().toArray(new Product[0]));
    UITheme.themeComboBox(productBox);
    productBox.setFont(UITheme.LABEL_FONT);
    productBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
      JLabel label = new JLabel();
      if (value != null) {
        label.setText(value.getName() + " (" + value.getSku() + ")");
      }
      label.setOpaque(true);
      label.setFont(UITheme.LABEL_FONT);
      label.setBackground(isSelected ? UITheme.CARD_BACKGROUND : UITheme.BACKGROUND);
      UITheme.themeLabel(label);
      return label;
    });
    JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1_000_000, 1));
    UITheme.themeNumberInput(qtySpinner);
    qtySpinner.setFont(UITheme.LABEL_FONT);

    JTextField unitPriceField = createTextField("e.g. PHP 799", "");
    unitPriceField.setEnabled(false);
    JTextField totalField = createTextField("e.g. PHP 1,598", "");
    totalField.setEnabled(false);

    addFormRow(saleInfo, 0, "Date", dateField);
    addFormRow(saleInfo, 1, "Product", productBox);
    addFormRow(saleInfo, 2, "Quantity", qtySpinner);

    addFormRow(pricing, 0, "Unit Price", unitPriceField);
    addFormRow(pricing, 1, "Total", totalField);

    Runnable updatePricing = () -> {
      Product selected = (Product) productBox.getSelectedItem();
      int qty = (Integer) qtySpinner.getValue();
      if (selected == null) {
        unitPriceField.setText(formatCurrency(0));
        unitPriceField.setForeground(UITheme.DARK_TEXT);
        totalField.setText(formatCurrency(0));
        totalField.setForeground(UITheme.DARK_TEXT);
        return;
      }
      unitPriceField.setText(formatCurrency(selected.getPrice()));
      unitPriceField.setForeground(UITheme.DARK_TEXT);
      totalField.setText(formatCurrency(selected.getPrice() * qty));
      totalField.setForeground(UITheme.DARK_TEXT);
    };
    updatePricing.run();
    productBox.addActionListener(e -> updatePricing.run());
    qtySpinner.addChangeListener(e -> updatePricing.run());

    formContainer.add(saleInfo);
    formContainer.add(Box.createVerticalStrut(12));
    formContainer.add(pricing);

    JScrollPane formScroll = new JScrollPane(formContainer);
    formScroll.setBorder(null);
    formScroll.setOpaque(false);
    formScroll.getViewport().setOpaque(false);
    formScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    formScroll.getVerticalScrollBar().setUnitIncrement(16);
    UITheme.themeScrollPane(formScroll);

    content.add(formScroll, BorderLayout.CENTER);

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    actions.setOpaque(false);
    JButton cancel = UITheme.secondaryButton("Cancel");
    cancel.addActionListener(e -> dialog.dispose());
    JButton save = UITheme.primaryButton("Add Sale");
    save.addActionListener(e -> {
      Product selected = (Product) productBox.getSelectedItem();
      if (selected == null) {
        JOptionPane.showMessageDialog(dialog, "Please select a product.",
            "Missing Product", JOptionPane.WARNING_MESSAGE);
        return;
      }
      int qty = (Integer) qtySpinner.getValue();
      if (qty <= 0) {
        JOptionPane.showMessageDialog(dialog, "Quantity must be greater than 0.",
            "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
        return;
      }
      if (selected.getQuantity() < qty) {
        JOptionPane.showMessageDialog(dialog,
            "Insufficient stock for this sale.",
            "Stock Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      SaleTransaction transaction =
          new SaleTransaction("TX-" + UUID.randomUUID());
      transaction.addItem(new SaleItem(selected, qty));
      sales.recordTransaction(transaction);
      inventory.reduceStock(selected.getSku(), qty);
      refresh();
      dialog.dispose();
    });
    actions.add(cancel);
    actions.add(save);

    content.add(actions, BorderLayout.SOUTH);

    dialog.setContentPane(content);
    dialog.setSize(540, 520);
    dialog.setLocationRelativeTo(this);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }

  private JPanel buildSectionPanel(String titleText) {
    JPanel section = UITheme.cardPanel();
    section.setLayout(new GridBagLayout());

    GridBagConstraints headerGbc = new GridBagConstraints();
    headerGbc.gridx = 0;
    headerGbc.gridy = 0;
    headerGbc.weightx = 1;
    headerGbc.anchor = GridBagConstraints.WEST;
    headerGbc.insets = new Insets(2, 2, 10, 2);
    section.add(sectionTitle(titleText), headerGbc);

    return section;
  }

  private void addFormRow(JPanel section, int row, String labelText,
                          JComponent field) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = row * 2 + 1;
    gbc.weightx = 1;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(6, 2, 4, 2);
    section.add(formLabel(labelText), gbc);

    gbc.gridy = row * 2 + 2;
    gbc.insets = new Insets(0, 2, 10, 2);
    field.setPreferredSize(new Dimension(0, 36));
    section.add(field, gbc);
  }

  private JLabel sectionTitle(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT.deriveFont(Font.BOLD));
    UITheme.themeLabel(label);
    return label;
  }

  private JLabel formLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT);
    UITheme.themeLabel(label);
    return label;
  }

  private JTextField createTextField(String placeholder, String value) {
    JTextField field = new JTextField();
    UITheme.themeTextField(field);
    field.setFont(UITheme.LABEL_FONT);
    field.setBorder(new CompoundBorder(
        UITheme.roundedBorder(UITheme.BORDER, 1, 10),
        new EmptyBorder(6, 10, 6, 10)));

    if (value != null && !value.isBlank()) {
      field.setForeground(UITheme.DARK_TEXT);
      field.setText(value);
    } else {
      applyPlaceholder(field, placeholder);
    }

    field.addFocusListener(new java.awt.event.FocusAdapter() {
      @Override
      public void focusGained(java.awt.event.FocusEvent e) {
        if (isPlaceholderActive(field)) {
          field.setText("");
          field.setForeground(UITheme.DARK_TEXT);
        }
      }

      @Override
      public void focusLost(java.awt.event.FocusEvent e) {
        if (field.getText().isBlank()) {
          applyPlaceholder(field, placeholder);
        }
      }
    });

    field.putClientProperty("placeholderText", placeholder);
    return field;
  }

  private void applyPlaceholder(JTextField field, String placeholder) {
    field.setForeground(UITheme.MUTED_TEXT);
    field.setText(placeholder);
  }

  private boolean isPlaceholderActive(JTextField field) {
    Object placeholder = field.getClientProperty("placeholderText");
    return placeholder != null && placeholder.equals(field.getText())
        && UITheme.MUTED_TEXT.equals(field.getForeground());
  }

  @Override
  public void refresh() {
    totalSalesValue.setText(String.valueOf(sales.getTotalSalesCount()));
    totalRevenueValue.setText(formatCurrency(sales.getTotalRevenue()));
    unitsSoldValue.setText(String.valueOf(sales.getTotalUnitsSold()));
    tableBody.removeAll();
    for (SaleTransaction transaction : sales.getAllTransactions()) {
      String date = transaction.getDate().format(DATE_FORMAT);
      for (SaleItem item : transaction.getItems()) {
        Product product = item.getProduct();
        String name = product != null ? product.getName() : "Unknown";
        tableBody.add(tableRow(date, name,
            String.valueOf(item.getQuantity()),
            formatCurrency(item.getUnitPrice()),
            formatCurrency(item.getSubtotal())));
        tableBody.add(Box.createVerticalStrut(6));
      }
    }
    tableBody.revalidate();
    tableBody.repaint();
  }

  private String formatCurrency(double amount) {
    return "PHP " + CURRENCY.format(amount);
  }
}
