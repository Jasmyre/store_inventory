package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SalesPage extends JPanel {
  public SalesPage() {
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
    JLabel description = new JLabel(
        "Review recent transactions and sales performance at a glance.");
    description.setFont(UITheme.SUBTITLE_FONT);
    description.setForeground(UITheme.MUTED_TEXT);

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
    stats.add(statCard("Total Sales", "PHP 42,580"));
    stats.add(statCard("Total Revenue", "PHP 58,900"));
    stats.add(statCard("Units Sold", "420"));

    JPanel tableCard = UITheme.cardPanel();
    tableCard.setLayout(new BoxLayout(tableCard, BoxLayout.Y_AXIS));

    tableCard.add(tableHeader());
    tableCard.add(Box.createVerticalStrut(8));
    tableCard.add(
        tableRow("2026-03-19", "Wireless Mouse", "2", "PHP 799", "PHP 1,598"));
    tableCard.add(Box.createVerticalStrut(6));
    tableCard.add(
        tableRow("2026-03-19", "Laptop Stand", "1", "PHP 1,199", "PHP 1,199"));
    tableCard.add(Box.createVerticalStrut(6));
    tableCard.add(
        tableRow("2026-03-20", "USB-C Cable", "5", "PHP 199", "PHP 995"));

    JPanel body = new JPanel();
    body.setOpaque(false);
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.add(stats);
    body.add(Box.createVerticalStrut(16));
    body.add(tableCard);

    content.add(topRow, BorderLayout.NORTH);
    content.add(body, BorderLayout.CENTER);

    JScrollPane scroll = new JScrollPane(content);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    add(scroll, BorderLayout.CENTER);
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

  private JPanel tableHeader() {
    JPanel row = new JPanel(new GridLayout(1, 5, 8, 0));
    row.setOpaque(false);
    row.add(headerLabel("Date"));
    row.add(headerLabel("Product"));
    row.add(headerLabel("Qty"));
    row.add(headerLabel("Unit Price"));
    row.add(headerLabel("Total"));
    return row;
  }

  private JPanel tableRow(String date, String product, String qty, String unit,
                          String total) {
    JPanel row = new JPanel(new GridLayout(1, 5, 8, 0));
    row.setOpaque(false);
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
    label.setForeground(UITheme.DARK_TEXT);
    return label;
  }

  private JLabel cellLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(UITheme.LABEL_FONT);
    label.setForeground(UITheme.MUTED_TEXT);
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
    content.add(title, BorderLayout.NORTH);

    JPanel formContainer = new JPanel();
    formContainer.setOpaque(false);
    formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));

    JPanel saleInfo = buildSectionPanel("Sale Information");
    JPanel pricing = buildSectionPanel("Pricing Details");

    JTextField dateField = createTextField("e.g. 2026-03-30", "");
    JTextField productField = createTextField("e.g. Wireless Mouse", "");
    JTextField qtyField = createTextField("e.g. 2", "");

    JTextField unitPriceField = createTextField("e.g. PHP 799", "");
    JTextField totalField = createTextField("e.g. PHP 1,598", "");

    addFormRow(saleInfo, 0, "Date", dateField);
    addFormRow(saleInfo, 1, "Product", productField);
    addFormRow(saleInfo, 2, "Quantity", qtyField);

    addFormRow(pricing, 0, "Unit Price", unitPriceField);
    addFormRow(pricing, 1, "Total", totalField);

    formContainer.add(saleInfo);
    formContainer.add(Box.createVerticalStrut(12));
    formContainer.add(pricing);

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
    JButton save = UITheme.primaryButton("Add Sale");
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
}
