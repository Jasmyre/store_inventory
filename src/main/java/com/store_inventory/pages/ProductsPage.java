package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ProductsPage extends JPanel {
  public ProductsPage() {
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

    JLabel title = new JLabel("Manage Products");
    title.setFont(
        UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 32));
    JLabel description =
        new JLabel("Add, edit, and remove products from the inventory.");
    description.setFont(UITheme.SUBTITLE_FONT);
    description.setForeground(UITheme.MUTED_TEXT);

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

    JPanel tableCard = UITheme.cardPanel();
    tableCard.setLayout(new BoxLayout(tableCard, BoxLayout.Y_AXIS));

    tableCard.add(tableHeader());
    tableCard.add(Box.createVerticalStrut(8));
    tableCard.add(
        tableRow("Wireless Earbuds", "101", "PHP 799", "32", "Audio"));
    tableCard.add(Box.createVerticalStrut(6));
    tableCard.add(
        tableRow("Bluetooth Speaker", "102", "PHP 1,299", "12", "Audio"));
    tableCard.add(Box.createVerticalStrut(6));
    tableCard.add(
        tableRow("Phone Case", "103", "PHP 249", "68", "Accessories"));
    tableCard.add(Box.createVerticalStrut(6));
    tableCard.add(
        tableRow("USB-C Charger", "104", "PHP 399", "45", "Accessories"));

    content.add(topRow, BorderLayout.NORTH);
    content.add(Box.createVerticalStrut(20));
    content.add(tableCard, BorderLayout.CENTER);

    JScrollPane scroll = new JScrollPane(content);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    add(scroll, BorderLayout.CENTER);
  }

  private JPanel tableHeader() {
    JPanel row = new JPanel(new GridLayout(1, 6, 8, 0));
    row.setOpaque(false);
    row.add(headerLabel("Name"));
    row.add(headerLabel("ID"));
    row.add(headerLabel("Price"));
    row.add(headerLabel("Quantity"));
    row.add(headerLabel("Category"));
    row.add(headerLabel("Actions"));
    return row;
  }

  private JPanel tableRow(String name, String id, String price, String stock,
                          String category) {
    JPanel row = new JPanel(new GridLayout(1, 6, 8, 0));
    row.setOpaque(false);
    row.add(cellLabel(name));
    row.add(cellLabel(id));
    row.add(cellLabel(price));
    row.add(cellLabel(stock));
    row.add(cellLabel(category));

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    actions.setOpaque(false);
    JButton edit = UITheme.secondaryButton("Edit");
    edit.addActionListener(e -> showProductFormDialog("Edit Product",
        new ProductFormData(name, id, price, stock, category)));
    JButton delete = UITheme.secondaryButton("Delete");
    delete.addActionListener(e -> showDeleteConfirmDialog(name, id));
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
    label.setForeground(UITheme.DARK_TEXT);
    return label;
  }

  private JLabel cellLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT);
    label.setForeground(UITheme.MUTED_TEXT);
    return label;
  }

  private void showProductFormDialog(String titleText, ProductFormData data) {
    Window owner = SwingUtilities.getWindowAncestor(this);
    JDialog dialog = new JDialog(owner, titleText, Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel content = new JPanel(new BorderLayout(0, 16));
    content.setBorder(new EmptyBorder(20, 24, 20, 24));
    content.setBackground(UITheme.BACKGROUND);

    JLabel title = new JLabel(titleText);
    title.setFont(UITheme.customFont(UITheme.FONT_FAMILY, UITheme.FONT_WEIGHT_TITLE, 22));
    content.add(title, BorderLayout.NORTH);

    JPanel formContainer = new JPanel();
    formContainer.setOpaque(false);
    formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));

    JPanel basicInfo = buildSectionPanel("Basic Information");
    JPanel inventory = buildSectionPanel("Inventory Details");

    JTextField nameField =
        createTextField("e.g. Wireless Earbuds", data == null ? "" : data.name);
    JTextField idField =
        createTextField("e.g. 101", data == null ? "" : data.id);
    JTextField categoryField =
        createTextField("e.g. Audio", data == null ? "" : data.category);

    JTextField priceField =
        createTextField("e.g. PHP 799", data == null ? "" : data.price);
    JTextField stockField =
        createTextField("e.g. 32", data == null ? "" : data.stock);

    addFormRow(basicInfo, 0, "Product Name", nameField);
    addFormRow(basicInfo, 1, "Product ID", idField);
    addFormRow(basicInfo, 2, "Category", categoryField);

    addFormRow(inventory, 0, "Price", priceField);
    addFormRow(inventory, 1, "Quantity", stockField);

    formContainer.add(basicInfo);
    formContainer.add(Box.createVerticalStrut(12));
    formContainer.add(inventory);

    JScrollPane formScroll = new JScrollPane(formContainer);
    formScroll.setBorder(null);
    formScroll.setOpaque(false);
    formScroll.getViewport().setOpaque(false);
    formScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    formScroll.getVerticalScrollBar().setUnitIncrement(16);

    content.add(formScroll, BorderLayout.CENTER);

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    actions.setOpaque(false);
    JButton cancel = UITheme.secondaryButton("Cancel");
    cancel.addActionListener(e -> dialog.dispose());
    JButton save =
        UITheme.primaryButton(data == null ? "Add Product" : "Save Changes");
    save.addActionListener(e -> dialog.dispose());
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
    label.setForeground(UITheme.DARK_TEXT);
    return label;
  }

  private JLabel formLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT);
    label.setForeground(UITheme.DARK_TEXT);
    return label;
  }

  private JTextField createTextField(String placeholder, String value) {
    JTextField field = new JTextField();
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

    JLabel message = new JLabel(
        "<html>Are you sure you want to delete <b>" + name + "</b> (ID " + id
            + ")?</html>");
    message.setFont(UITheme.LABEL_FONT);
    message.setForeground(UITheme.MUTED_TEXT);

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
    confirm.addActionListener(e -> dialog.dispose());
    actions.add(cancel);
    actions.add(confirm);

    content.add(actions, BorderLayout.SOUTH);

    dialog.setContentPane(content);
    dialog.setSize(380, 220);
    dialog.setLocationRelativeTo(this);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }

  private static class ProductFormData {
    final String name;
    final String id;
    final String price;
    final String stock;
    final String category;

    private ProductFormData(String name, String id, String price, String stock,
                            String category) {
      this.name = name;
      this.id = id;
      this.price = price;
      this.stock = stock;
      this.category = category;
    }
  }
}
