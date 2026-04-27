package com.store_inventory.pages.components;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public final class UITheme {
  private static final Color COLOR_BACKGROUND = new Color(245, 242, 252);
  private static final Color COLOR_FOREGROUND = new Color(64, 49, 92);
  private static final Color COLOR_CARD = new Color(255, 255, 255);
  private static final Color COLOR_MUTED = new Color(241, 236, 249);
  private static final Color COLOR_MUTED_FOREGROUND = new Color(127, 113, 150);
  private static final Color COLOR_PRIMARY = new Color(128, 80, 204);
  private static final Color COLOR_PRIMARY_FOREGROUND =
      new Color(255, 255, 255);
  private static final Color COLOR_SECONDARY = new Color(233, 222, 246);
  private static final Color COLOR_SECONDARY_FOREGROUND =
      new Color(97, 67, 140);
  private static final Color COLOR_ACCENT = new Color(223, 232, 250);
  private static final Color COLOR_BORDER = new Color(227, 220, 238);
  private static final Color COLOR_INPUT = new Color(255, 255, 255);
  private static final Color COLOR_RING = new Color(128, 80, 204);

  public static final int RADIUS_SM = 6 * 2;
  public static final int RADIUS_MD = 8 * 2;
  public static final int RADIUS_LG = 10 * 2;
  public static final int RADIUS_XL = 14 * 2;

  public static final Color SHADOW_SM = new Color(55, 43, 89, 24);
  public static final Color SHADOW_MD = new Color(55, 43, 89, 32);

  public static final Color BACKGROUND = COLOR_BACKGROUND;
  public static final Color HEADER_BACKGROUND = COLOR_CARD;
  public static final Color CARD_BACKGROUND = COLOR_CARD;
  public static final Color BORDER = COLOR_BORDER;
  public static final Color PRIMARY = COLOR_PRIMARY;
  public static final Color PRIMARY_TEXT = COLOR_PRIMARY_FOREGROUND;
  public static final Color DARK_TEXT = COLOR_FOREGROUND;
  public static final Color MUTED_TEXT = COLOR_MUTED_FOREGROUND;
  public static final Color INPUT_BACKGROUND = COLOR_INPUT;
  public static final Color INPUT_TEXT = COLOR_FOREGROUND;
  public static final Color LABEL_TEXT = COLOR_FOREGROUND;
  public static final Color TITLEBAR_BACKGROUND = COLOR_CARD;
  public static final Color TITLEBAR_TEXT = COLOR_FOREGROUND;
  public static final Color TITLEBAR_BUTTON_HOVER = new Color(221, 212, 238);
  public static final Color TITLEBAR_BUTTON_CLOSE_HOVER =
      new Color(220, 93, 108);
  public static final Color SUMMARY_CARD_BACKGROUND = new Color(248, 245, 255);
  public static final Color SCROLLBAR_TRACK = new Color(236, 229, 247);
  public static final Color SCROLLBAR_THUMB = new Color(187, 170, 219);

  public static final Color STATUS_OUT_BG = new Color(253, 233, 236);
  public static final Color STATUS_OUT_TEXT = new Color(154, 38, 54);
  public static final Color STATUS_OUT_BORDER = new Color(226, 111, 125);
  public static final Color STATUS_LOW_BG = new Color(255, 244, 228);
  public static final Color STATUS_LOW_TEXT = new Color(142, 90, 24);
  public static final Color STATUS_LOW_BORDER = new Color(230, 170, 90);
  public static final Color STATUS_IN_BG = new Color(230, 248, 238);
  public static final Color STATUS_IN_TEXT = new Color(40, 114, 76);
  public static final Color STATUS_IN_BORDER = new Color(103, 193, 143);

  // DARK
  // private static final Color COLOR_BACKGROUND = new Color(30, 35, 52);
  // private static final Color COLOR_FOREGROUND = new Color(237, 242, 251);
  // private static final Color COLOR_CARD = new Color(42, 49, 72);
  // private static final Color COLOR_MUTED = new Color(36, 42, 61);
  // private static final Color COLOR_MUTED_FOREGROUND = new Color(177, 185, 202);
  // private static final Color COLOR_PRIMARY = new Color(107, 143, 255);
  // private static final Color COLOR_PRIMARY_FOREGROUND =
  //     new Color(237, 242, 251);
  // private static final Color COLOR_SECONDARY = new Color(48, 56, 84);
  // private static final Color COLOR_SECONDARY_FOREGROUND =
  //     new Color(237, 242, 251);
  // private static final Color COLOR_ACCENT = new Color(55, 64, 90);
  // private static final Color COLOR_BORDER = new Color(89, 97, 120);
  // private static final Color COLOR_INPUT = new Color(34, 40, 60);
  // private static final Color COLOR_RING = new Color(107, 143, 255);

  // public static final int RADIUS_SM = 6 * 2;
  // public static final int RADIUS_MD = 8 * 2;
  // public static final int RADIUS_LG = 10 * 2;
  // public static final int RADIUS_XL = 14 * 2;

  // public static final Color SHADOW_SM = new Color(0, 0, 0, 40);
  // public static final Color SHADOW_MD = new Color(0, 0, 0, 60);

  // public static final Color BACKGROUND = COLOR_BACKGROUND;
  // public static final Color HEADER_BACKGROUND = new Color(36, 42, 61);
  // public static final Color CARD_BACKGROUND = COLOR_CARD;
  // public static final Color BORDER = COLOR_BORDER;
  // public static final Color PRIMARY = COLOR_PRIMARY;
  // public static final Color PRIMARY_TEXT = COLOR_PRIMARY_FOREGROUND;
  // public static final Color DARK_TEXT = new Color(255, 255, 255);
  // public static final Color MUTED_TEXT = COLOR_MUTED_FOREGROUND;
  // public static final Color INPUT_BACKGROUND = COLOR_INPUT;
  // public static final Color INPUT_TEXT = COLOR_PRIMARY_FOREGROUND;
  // public static final Color LABEL_TEXT = COLOR_PRIMARY_FOREGROUND;
  // public static final Color TITLEBAR_BACKGROUND =
  //     new Color(24, 28, 42); // treat as card
  // public static final Color TITLEBAR_TEXT = COLOR_PRIMARY_FOREGROUND;
  // public static final Color TITLEBAR_BUTTON_HOVER = new Color(55, 64, 90);
  // public static final Color TITLEBAR_BUTTON_CLOSE_HOVER =
  //     new Color(140, 40, 50);
  // public static final Color SUMMARY_CARD_BACKGROUND = COLOR_SECONDARY;
  // public static final Color SCROLLBAR_TRACK = new Color(26, 30, 44);
  // public static final Color SCROLLBAR_THUMB = new Color(74, 84, 110);

  // public static final Color STATUS_OUT_BG = new Color(92, 15, 22);
  // public static final Color STATUS_OUT_TEXT = Color.WHITE;
  // public static final Color STATUS_OUT_BORDER = new Color(166, 34, 49);
  // public static final Color STATUS_LOW_BG = new Color(92, 54, 16);
  // public static final Color STATUS_LOW_TEXT = Color.WHITE;
  // public static final Color STATUS_LOW_BORDER = new Color(190, 108, 38);
  // public static final Color STATUS_IN_BG = new Color(18, 80, 44);
  // public static final Color STATUS_IN_TEXT = Color.WHITE;
  // public static final Color STATUS_IN_BORDER = new Color(40, 160, 90);

  // FONT WEIGHTS
  public static final int FONT_WEIGHT_TITLE = Font.BOLD;
  public static final int FONT_WEIGHT_SUBTITLE = Font.PLAIN;
  public static final int FONT_WEIGHT_LABEL = Font.PLAIN;

  // FONT FAMILY
  public static final String FONT_FAMILY = resolveSansFontFamily();

  public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 24);
  public static final Font SUBTITLE_FONT =
      new Font(FONT_FAMILY, Font.PLAIN, 16);
  public static final Font LABEL_FONT = new Font(FONT_FAMILY, Font.PLAIN, 16);

  private UITheme() {}

  private static String resolveSansFontFamily() {
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
                         .getAvailableFontFamilyNames();
    boolean hasInter =
        Arrays.stream(fonts).anyMatch(name -> "Inter".equalsIgnoreCase(name));
    return hasInter ? "Inter" : Font.SANS_SERIF;
  }

  // font with custom font_family, weight, and size
  public static Font customFont(String family, int weight, int size) {
    return new Font(family, weight, size);
  }

  public static JLabel themeLabel(JLabel label) {
    label.setForeground(LABEL_TEXT);
    return label;
  }

  public static JTextField themeTextField(JTextField field) {
    field.setBackground(INPUT_BACKGROUND);
    field.setForeground(INPUT_TEXT);
    field.setCaretColor(COLOR_FOREGROUND);
    field.setSelectionColor(COLOR_ACCENT);
    field.setSelectedTextColor(COLOR_FOREGROUND);
    field.setOpaque(true);
    return field;
  }

  public static JComboBox<?> themeComboBox(JComboBox<?> box) {
    Border comboBorder = new CompoundBorder(roundedBorder(BORDER, 1, RADIUS_MD),
                                            new EmptyBorder(6, 12, 6, 12));

    box.setBackground(INPUT_BACKGROUND);
    box.setForeground(INPUT_TEXT);
    box.setBorder(comboBorder);
    box.setFocusable(false);
    box.setOpaque(true);
    box.setCursor(new Cursor(Cursor.HAND_CURSOR));

    box.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
      JLabel label = new JLabel();

      if (value != null) {
        label.setText(value.toString());
      }

      label.setCursor(new Cursor(Cursor.HAND_CURSOR));

      label.setOpaque(true);
      label.setFont(LABEL_FONT);

      label.setBorder(new EmptyBorder(4, 10, 4, 10));

      label.setBackground(isSelected ? COLOR_SECONDARY : INPUT_BACKGROUND);
      label.setForeground(isSelected ? COLOR_SECONDARY_FOREGROUND : INPUT_TEXT);

      return label;
    });

    box.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
      @Override
      protected JButton createArrowButton() {
        JButton button = new JButton();
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
      }
    });

    return box;
  }

  public static JSpinner themeNumberInput(JSpinner spinner) {
    spinner.setBackground(INPUT_BACKGROUND);
    spinner.setForeground(INPUT_TEXT);
    spinner.setBorder(new CompoundBorder(roundedBorder(BORDER, 1, RADIUS_MD),
                                         new EmptyBorder(4, 8, 4, 8)));
    spinner.setOpaque(true);
    JComponent editor = spinner.getEditor();
    if (editor instanceof JSpinner.DefaultEditor) {
      JTextField field = ((JSpinner.DefaultEditor)editor).getTextField();
      themeTextField(field);
      field.setBorder(null);
    }
    return spinner;
  }

  public static JScrollPane themeScrollPane(JScrollPane scroll) {
    scroll.getVerticalScrollBar().setUI(new ThinScrollBarUI());
    scroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 8));
    scroll.getHorizontalScrollBar().setUI(new ThinScrollBarUI());
    scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(8, 8));
    scroll.getVerticalScrollBar().setOpaque(false);
    scroll.getHorizontalScrollBar().setOpaque(false);
    return scroll;
  }

  public static JPanel cardPanel() {
    Border padding = new EmptyBorder(12, 16, 12, 16);
    JPanel panel = new RoundedPanel(RADIUS_XL, BORDER, SHADOW_MD, 6);
    panel.setBackground(CARD_BACKGROUND);
    panel.setBorder(padding);
    return panel;
  }

  public static JPanel statCard(String label, JLabel valueLabel) {
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

  public static JButton primaryButton(String text) {
    Border line = roundedBorder(COLOR_RING, 1, RADIUS_MD);
    Border padding = new EmptyBorder(6, 16, 6, 16);

    JButton button = new JButton(text) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS_MD, RADIUS_MD);
        g2.dispose();
        super.paintComponent(g);
      }
    };
    button.setBackground(PRIMARY);
    button.setForeground(PRIMARY_TEXT);
    button.setFocusPainted(false);
    button.setBorder(new CompoundBorder(line, padding));
    button.setContentAreaFilled(false);
    button.setOpaque(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
  }

  public static JButton secondaryButton(String text) {
    Border line = roundedBorder(BORDER, 1, RADIUS_MD);
    Border padding = new EmptyBorder(6, 16, 6, 16);

    JButton button = new JButton(text) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS_MD, RADIUS_MD);
        g2.dispose();
        super.paintComponent(g);
      }
    };
    button.setBackground(COLOR_MUTED);
    button.setForeground(COLOR_FOREGROUND);
    button.setFocusPainted(false);
    button.setBorder(new CompoundBorder(line, padding));
    button.setContentAreaFilled(false);
    button.setOpaque(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    return button;
  }

  public static void applyStockStatusStyle(JButton button, int stock,
                                           int reorderLevel) {
    Color background;
    Color foreground;
    Color borderColor;
    if (stock <= 0) {
      background = STATUS_OUT_BG;
      foreground = STATUS_OUT_TEXT;
      borderColor = STATUS_OUT_BORDER;
    } else if (stock <= reorderLevel) {
      background = STATUS_LOW_BG;
      foreground = STATUS_LOW_TEXT;
      borderColor = STATUS_LOW_BORDER;
    } else {
      background = STATUS_IN_BG;
      foreground = STATUS_IN_TEXT;
      borderColor = STATUS_IN_BORDER;
    }

    button.setBackground(background);
    button.setForeground(foreground);
    button.setBorder(
        new CompoundBorder(roundedBorder(borderColor, 1, RADIUS_MD),
                           new EmptyBorder(6, 16, 6, 16)));
  }

  public static Border roundedBorder(Color color, int thickness, int radius) {
    return new RoundedBorder(color, thickness, radius);
  }

  private static final class RoundedBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int radius;

    private RoundedBorder(Color color, int thickness, int radius) {
      this.color = color;
      this.thickness = thickness;
      this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
                            int height) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(color);
      g2.setStroke(new BasicStroke(thickness));
      int offset = thickness / 2;
      int w = width - thickness;
      int h = height - thickness;
      g2.drawRoundRect(x + offset, y + offset, w, h, radius, radius);
      g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
      return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
      insets.left = thickness;
      insets.right = thickness;
      insets.top = thickness;
      insets.bottom = thickness;
      return insets;
    }
  }

  private static final class RoundedPanel extends JPanel {
    private final int radius;
    private final Color borderColor;
    private final Color shadowColor;
    private final int shadowSize;

    private RoundedPanel(int radius, Color borderColor, Color shadowColor,
                         int shadowSize) {
      this.radius = radius;
      this.borderColor = borderColor;
      this.shadowColor = shadowColor;
      this.shadowSize = shadowSize;
      setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      int offset = Math.max(1, shadowSize / 2);
      g2.setColor(shadowColor);
      g2.fillRoundRect(offset, offset, getWidth() - shadowSize,
                       getHeight() - shadowSize, radius, radius);
      g2.setColor(getBackground());
      g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize,
                       radius, radius);
      g2.setColor(borderColor);
      g2.drawRoundRect(0, 0, getWidth() - shadowSize - 1,
                       getHeight() - shadowSize - 1, radius, radius);
      g2.dispose();
    }
  }

  private static final class ThinScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
      thumbColor = SCROLLBAR_THUMB;
      trackColor = SCROLLBAR_TRACK;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
      return zeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
      return zeroButton();
    }

    private JButton zeroButton() {
      JButton button = new JButton();
      button.setPreferredSize(new Dimension(0, 0));
      button.setMinimumSize(new Dimension(0, 0));
      button.setMaximumSize(new Dimension(0, 0));
      return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(thumbColor);
      int arc = Math.min(thumbBounds.width, thumbBounds.height);
      g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width,
                       thumbBounds.height, arc, arc);
      g2.dispose();
    }
  }
}
