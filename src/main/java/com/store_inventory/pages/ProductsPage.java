package com.store_inventory.pages;

import com.store_inventory.models.Product;
import com.store_inventory.pages.components.UITheme;
import com.store_inventory.services.InventoryManager;
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ProductsPage extends JPanel implements Refreshable {
  private static final int DEFAULT_REORDER_LEVEL = 10;
  private static final DecimalFormat CURRENCY = new DecimalFormat("#,##0.00");
  private final InventoryManager inventory;
  private final JPanel tableBody = new JPanel();

  public ProductsPage(InventoryManager inventory) {
    this.inventory = inventory;
    setLayout(new BorderLayout());
    setBackground(UITheme.BACKGROUND);

    JPanel topRow = new JPanel(new BorderLayout());
    topRow.setOpaque(false);

    JPanel headerText = new JPanel();
    headerText.setOpaque(false);
    headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));

    JLabel title = new JLabel("Manage Products");
    title.setFont(
        UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    UITheme.themeLabel(title);
    JLabel description =
        new JLabel("Add, edit, and remove products from the inventory.");
    description.setFont(UITheme.SUBTITLE_FONT);
    UITheme.themeLabel(description);

    headerText.add(title);
    headerText.add(Box.createVerticalStrut(6));
    headerText.add(description);
    topRow.add(headerText, BorderLayout.WEST);

    JButton addProduct = UITheme.primaryButton("Add Product");
    Border line = UITheme.roundedBorder(UITheme.BORDER, 1, 12);
    Border padding = new EmptyBorder(12, 16, 12, 16);
    addProduct.setBorder(new CompoundBorder(line, padding));
    addProduct.addActionListener(e -> showProductFormDialog("Add Product", null));

    JPanel buttonWrapper = new JPanel(new GridBagLayout());
    buttonWrapper.setOpaque(false);
    buttonWrapper.add(addProduct);

    topRow.add(buttonWrapper, BorderLayout.EAST);

    JPanel body = new JPanel();
    body.setOpaque(false);
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

    JPanel tableStack = new JPanel();
    tableStack.setOpaque(false);
    tableStack.setLayout(new BoxLayout(tableStack, BoxLayout.Y_AXIS));
    tableBody.setOpaque(false);
    tableBody.setLayout(new BoxLayout(tableBody, BoxLayout.Y_AXIS));

    tableStack.add(tableHeader());
    tableStack.add(Box.createVerticalStrut(8));
    tableStack.add(tableBody);
    body.add(tableStack);

    JPanel content = new JPanel(new BorderLayout(0, 24));
    content.setOpaque(false);
    content.setBorder(new EmptyBorder(40, 20, 40, 20));
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

  private JPanel tableHeader() {
    JPanel row = UITheme.cardPanel();
    row.setLayout(new GridLayout(1, 6, 8, 0));
    row.add(headerLabel("Name"));
    row.add(headerLabel("ID"));
    row.add(headerLabel("Price"));
    row.add(headerLabel("Quantity"));
    row.add(headerLabel("Category"));
    row.add(headerLabel("Actions"));
    return row;
  }

  private JPanel tableRow(Product product) {
    JPanel row = UITheme.cardPanel();
    row.setLayout(new GridLayout(1, 6, 8, 0));
    row.add(cellLabel(product.getName()));
    row.add(cellLabel(product.getSku()));
    row.add(cellLabel(formatCurrency(product.getPrice())));
    row.add(cellLabel(String.valueOf(product.getQuantity())));
    row.add(cellLabel(product.getCategory()));

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    actions.setOpaque(false);
    JButton edit = UITheme.secondaryButton("Edit");
    edit.addActionListener(
        e -> showProductFormDialog("Edit Product", product));
    JButton delete = UITheme.secondaryButton("Delete");
    delete.addActionListener(
        e -> showDeleteConfirmDialog(product.getName(), product.getSku()));
    actions.add(edit);
    actions.add(delete);

    JPanel actionsWrapper = new JPanel(new GridBagLayout());
    actionsWrapper.setOpaque(false);
    actionsWrapper.add(actions, new GridBagConstraints());
    row.add(actionsWrapper);

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

  private void showProductFormDialog(String titleText, Product existing) {
    Window owner = SwingUtilities.getWindowAncestor(this);
    JDialog dialog = new JDialog(owner, titleText, Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel content = new JPanel(new BorderLayout(0, 16));
    content.setBorder(new EmptyBorder(20, 24, 20, 24));
    content.setBackground(UITheme.BACKGROUND);

    JLabel title = new JLabel(titleText);
    title.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 22));
    UITheme.themeLabel(title);
    content.add(title, BorderLayout.NORTH);

    JPanel formContainer = new JPanel();
    formContainer.setOpaque(false);
    formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));

    JPanel basicInfo = buildSectionPanel("Basic Information");
    JPanel inventoryPanel = buildSectionPanel("Inventory Details");

    JTextField nameField = createTextField("e.g. Wireless Earbuds",
        existing == null ? "" : existing.getName());
    JTextField idField = createTextField("e.g. 101",
        existing == null ? "" : existing.getSku());
    JTextField categoryField = createTextField("e.g. Audio",
        existing == null ? "" : existing.getCategory());

    JTextField priceField = createTextField("e.g. PHP 799",
        existing == null ? "" : formatCurrency(existing.getPrice()));
    JTextField stockField = createTextField("e.g. 32",
        existing == null ? "" : String.valueOf(existing.getQuantity()));

    if (existing != null) {
      idField.setEnabled(false);
    }

    addFormRow(basicInfo, 0, "Product Name", nameField);
    addFormRow(basicInfo, 1, "Product ID", idField);
    addFormRow(basicInfo, 2, "Category", categoryField);

    addFormRow(inventoryPanel, 0, "Price", priceField);
    addFormRow(inventoryPanel, 1, "Quantity", stockField);

    formContainer.add(basicInfo);
    formContainer.add(Box.createVerticalStrut(12));
    formContainer.add(inventoryPanel);

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
    JButton save =
        UITheme.primaryButton(existing == null ? "Add Product" : "Save Changes");
    save.addActionListener(e -> {
      String name = getFieldValue(nameField);
      String sku = getFieldValue(idField);
      String category = getFieldValue(categoryField);
      String priceText = getFieldValue(priceField);
      String stockText = getFieldValue(stockField);

      if (name.isBlank() || sku.isBlank() || category.isBlank()
          || priceText.isBlank() || stockText.isBlank()) {
        JOptionPane.showMessageDialog(dialog,
            "Please fill out all product details.",
            "Missing Details", JOptionPane.WARNING_MESSAGE);
        return;
      }

      Double price = parsePrice(priceText);
      Integer stock = parseQuantity(stockText);
      if (price == null || stock == null) {
        JOptionPane.showMessageDialog(dialog,
            "Please enter a valid price and quantity.",
            "Invalid Input", JOptionPane.WARNING_MESSAGE);
        return;
      }

      if (existing == null && inventory.findProduct(sku) != null) {
        JOptionPane.showMessageDialog(dialog,
            "A product with that ID already exists.",
            "Duplicate Product", JOptionPane.ERROR_MESSAGE);
        return;
      }

      int reorderLevel =
          existing == null ? DEFAULT_REORDER_LEVEL : existing.getReorderLevel();
      Product updated =
          new Product(sku, name, category, price, stock, reorderLevel);
      if (existing == null) {
        inventory.addProduct(updated);
      } else {
        inventory.updateProduct(updated);
      }
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
                          JTextField field) {
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

  private void showDeleteConfirmDialog(String name, String id) {
    Window owner = SwingUtilities.getWindowAncestor(this);
    JDialog dialog = new JDialog(owner, "Delete Product",
        Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel content = new JPanel(new BorderLayout(0, 12));
    content.setBorder(new EmptyBorder(18, 22, 18, 22));
    content.setBackground(UITheme.BACKGROUND);

    JLabel title = new JLabel("Delete Product");
    title.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 20));
    UITheme.themeLabel(title);

    JLabel message = new JLabel(
        "<html>Are you sure you want to delete <b>" + name + "</b> (ID " + id
            + ")?</html>");
    message.setFont(UITheme.LABEL_FONT);
    UITheme.themeLabel(message);

    JPanel text = new JPanel();
    text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
    text.setOpaque(false);
    text.add(title);
    text.add(Box.createVerticalStrut(6));
    text.add(message);

    content.add(text, BorderLayout.CENTER);

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    actions.setOpaque(false);
    JButton cancel = UITheme.secondaryButton("Cancel");
    cancel.addActionListener(e -> dialog.dispose());
    JButton confirm = UITheme.primaryButton("Delete");
    confirm.addActionListener(e -> {
      inventory.removeProduct(id);
      refresh();
      dialog.dispose();
    });
    actions.add(cancel);
    actions.add(confirm);

    content.add(actions, BorderLayout.SOUTH);

    dialog.setContentPane(content);
    dialog.setSize(380, 220);
    dialog.setLocationRelativeTo(this);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }

  @Override
  public void refresh() {
    tableBody.removeAll();
    for (Product product : inventory.getAllProducts()) {
      tableBody.add(tableRow(product));
      tableBody.add(Box.createVerticalStrut(6));
    }
    tableBody.revalidate();
    tableBody.repaint();
  }

  private String getFieldValue(JTextField field) {
    return isPlaceholderActive(field) ? "" : field.getText().trim();
  }

  private Double parsePrice(String text) {
    String cleaned = text.replaceAll("[^0-9.]", "");
    if (cleaned.isBlank()) {
      return null;
    }
    try {
      return Double.parseDouble(cleaned);
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  private Integer parseQuantity(String text) {
    try {
      int value = Integer.parseInt(text.trim());
      return value >= 0 ? value : null;
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  private String formatCurrency(double amount) {
    return "PHP " + CURRENCY.format(amount);
  }
}
