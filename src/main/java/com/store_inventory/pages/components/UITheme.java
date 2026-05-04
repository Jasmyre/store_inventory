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
  public enum ThemeMode { LIGHT, DARK }

  private static final class ThemePalette {
    private final Color background;
    private final Color foreground;
    private final Color card;
    private final Color muted;
    private final Color mutedForeground;
    private final Color primary;
    private final Color primaryForeground;
    private final Color secondary;
    private final Color secondaryForeground;
    private final Color accent;
    private final Color border;
    private final Color input;
    private final Color ring;
    private final Color headerBackground;
    private final Color titlebarBackground;
    private final Color titlebarText;
    private final Color titlebarButtonHover;
    private final Color titlebarButtonCloseHover;
    private final Color summaryCardBackground;
    private final Color scrollbarTrack;
    private final Color scrollbarThumb;
    private final Color statusOutBg;
    private final Color statusOutText;
    private final Color statusOutBorder;
    private final Color statusLowBg;
    private final Color statusLowText;
    private final Color statusLowBorder;
    private final Color statusInBg;
    private final Color statusInText;
    private final Color statusInBorder;
    private final Color shadowSm;
    private final Color shadowMd;

    private ThemePalette(
        Color background, Color foreground, Color card, Color muted,
        Color mutedForeground, Color primary, Color primaryForeground,
        Color secondary, Color secondaryForeground, Color accent, Color border,
        Color input, Color ring, Color headerBackground, Color titlebarBackground,
        Color titlebarText, Color titlebarButtonHover,
        Color titlebarButtonCloseHover, Color summaryCardBackground,
        Color scrollbarTrack, Color scrollbarThumb, Color statusOutBg,
        Color statusOutText, Color statusOutBorder, Color statusLowBg,
        Color statusLowText, Color statusLowBorder, Color statusInBg,
        Color statusInText, Color statusInBorder, Color shadowSm,
        Color shadowMd) {
      this.background = background;
      this.foreground = foreground;
      this.card = card;
      this.muted = muted;
      this.mutedForeground = mutedForeground;
      this.primary = primary;
      this.primaryForeground = primaryForeground;
      this.secondary = secondary;
      this.secondaryForeground = secondaryForeground;
      this.accent = accent;
      this.border = border;
      this.input = input;
      this.ring = ring;
      this.headerBackground = headerBackground;
      this.titlebarBackground = titlebarBackground;
      this.titlebarText = titlebarText;
      this.titlebarButtonHover = titlebarButtonHover;
      this.titlebarButtonCloseHover = titlebarButtonCloseHover;
      this.summaryCardBackground = summaryCardBackground;
      this.scrollbarTrack = scrollbarTrack;
      this.scrollbarThumb = scrollbarThumb;
      this.statusOutBg = statusOutBg;
      this.statusOutText = statusOutText;
      this.statusOutBorder = statusOutBorder;
      this.statusLowBg = statusLowBg;
      this.statusLowText = statusLowText;
      this.statusLowBorder = statusLowBorder;
      this.statusInBg = statusInBg;
      this.statusInText = statusInText;
      this.statusInBorder = statusInBorder;
      this.shadowSm = shadowSm;
      this.shadowMd = shadowMd;
    }
  }

  private static final ThemePalette LIGHT_PALETTE = new ThemePalette(
      new Color(245, 242, 252), // background
      new Color(64, 49, 92), // foreground
      new Color(255, 255, 255), // card
      new Color(241, 236, 249), // muted
      new Color(127, 113, 150), // muted foreground
      new Color(128, 80, 204), // primary
      new Color(255, 255, 255), // primary foreground
      new Color(233, 222, 246), // secondary
      new Color(97, 67, 140), // secondary foreground
      new Color(223, 232, 250), // accent
      new Color(227, 220, 238), // border
      new Color(255, 255, 255), // input
      new Color(128, 80, 204), // ring
      new Color(255, 255, 255), // header background
      new Color(255, 255, 255), // titlebar background
      new Color(64, 49, 92), // titlebar text
      new Color(221, 212, 238), // titlebar button hover
      new Color(220, 93, 108), // titlebar close hover
      new Color(248, 245, 255), // summary card background
      new Color(236, 229, 247), // scrollbar track
      new Color(187, 170, 219), // scrollbar thumb
      new Color(253, 233, 236), // status out bg
      new Color(154, 38, 54), // status out text
      new Color(226, 111, 125), // status out border
      new Color(255, 244, 228), // status low bg
      new Color(142, 90, 24), // status low text
      new Color(230, 170, 90), // status low border
      new Color(230, 248, 238), // status in bg
      new Color(40, 114, 76), // status in text
      new Color(103, 193, 143), // status in border
      new Color(55, 43, 89, 24), // shadow sm
      new Color(55, 43, 89, 32) // shadow md
  );

  private static final ThemePalette DARK_PALETTE = new ThemePalette(
      new Color(30, 35, 52), // background
      new Color(237, 242, 251), // foreground
      new Color(42, 49, 72), // card
      new Color(36, 42, 61), // muted
      new Color(177, 185, 202), // muted foreground
      new Color(107, 143, 255), // primary
      new Color(237, 242, 251), // primary foreground
      new Color(48, 56, 84), // secondary
      new Color(237, 242, 251), // secondary foreground
      new Color(55, 64, 90), // accent
      new Color(89, 97, 120), // border
      new Color(34, 40, 60), // input
      new Color(107, 143, 255), // ring
      new Color(36, 42, 61), // header background
      new Color(24, 28, 42), // titlebar background
      new Color(237, 242, 251), // titlebar text
      new Color(55, 64, 90), // titlebar button hover
      new Color(140, 40, 50), // titlebar close hover
      new Color(48, 56, 84), // summary card background
      new Color(26, 30, 44), // scrollbar track
      new Color(74, 84, 110), // scrollbar thumb
      new Color(92, 15, 22), // status out bg
      new Color(255, 255, 255), // status out text
      new Color(166, 34, 49), // status out border
      new Color(92, 54, 16), // status low bg
      new Color(255, 255, 255), // status low text
      new Color(190, 108, 38), // status low border
      new Color(18, 80, 44), // status in bg
      new Color(255, 255, 255), // status in text
      new Color(40, 160, 90), // status in border
      new Color(0, 0, 0, 40), // shadow sm
      new Color(0, 0, 0, 60) // shadow md
  );

  private static ThemeMode activeThemeMode = ThemeMode.LIGHT;

  public static final int RADIUS_SM = 6 * 2;
  public static final int RADIUS_MD = 8 * 2;
  public static final int RADIUS_LG = 10 * 2;
  public static final int RADIUS_XL = 14 * 2;

  public static Color SHADOW_SM;
  public static Color SHADOW_MD;

  public static Color BACKGROUND;
  public static Color HEADER_BACKGROUND;
  public static Color CARD_BACKGROUND;
  public static Color BORDER;
  public static Color PRIMARY;
  public static Color PRIMARY_TEXT;
  public static Color DARK_TEXT;
  public static Color MUTED_TEXT;
  public static Color INPUT_BACKGROUND;
  public static Color INPUT_TEXT;
  public static Color LABEL_TEXT;
  public static Color TITLEBAR_BACKGROUND;
  public static Color TITLEBAR_TEXT;
  public static Color TITLEBAR_BUTTON_HOVER;
  public static Color TITLEBAR_BUTTON_CLOSE_HOVER;
  public static Color SUMMARY_CARD_BACKGROUND;
  public static Color SCROLLBAR_TRACK;
  public static Color SCROLLBAR_THUMB;

  public static Color STATUS_OUT_BG;
  public static Color STATUS_OUT_TEXT;
  public static Color STATUS_OUT_BORDER;
  public static Color STATUS_LOW_BG;
  public static Color STATUS_LOW_TEXT;
  public static Color STATUS_LOW_BORDER;
  public static Color STATUS_IN_BG;
  public static Color STATUS_IN_TEXT;
  public static Color STATUS_IN_BORDER;

  private static Color MUTED_BACKGROUND;
  private static Color SECONDARY_BACKGROUND;
  private static Color SECONDARY_FOREGROUND;
  private static Color ACCENT;
  private static Color RING;

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

  static {
    applyPalette(LIGHT_PALETTE);
  }

  private UITheme() {}

  public static ThemeMode getThemeMode() { return activeThemeMode; }

  public static void setThemeMode(ThemeMode mode) {
    ThemeMode nextMode = mode == null ? ThemeMode.LIGHT : mode;
    if (nextMode == activeThemeMode) {
      return;
    }
    activeThemeMode = nextMode;
    applyPalette(nextMode == ThemeMode.DARK ? DARK_PALETTE : LIGHT_PALETTE);
  }

  private static void applyPalette(ThemePalette palette) {
    BACKGROUND = palette.background;
    HEADER_BACKGROUND = palette.headerBackground;
    CARD_BACKGROUND = palette.card;
    BORDER = palette.border;
    PRIMARY = palette.primary;
    PRIMARY_TEXT = palette.primaryForeground;
    DARK_TEXT = palette.foreground;
    MUTED_TEXT = palette.mutedForeground;
    INPUT_BACKGROUND = palette.input;
    INPUT_TEXT = palette.foreground;
    LABEL_TEXT = palette.foreground;
    TITLEBAR_BACKGROUND = palette.titlebarBackground;
    TITLEBAR_TEXT = palette.titlebarText;
    TITLEBAR_BUTTON_HOVER = palette.titlebarButtonHover;
    TITLEBAR_BUTTON_CLOSE_HOVER = palette.titlebarButtonCloseHover;
    SUMMARY_CARD_BACKGROUND = palette.summaryCardBackground;
    SCROLLBAR_TRACK = palette.scrollbarTrack;
    SCROLLBAR_THUMB = palette.scrollbarThumb;
    STATUS_OUT_BG = palette.statusOutBg;
    STATUS_OUT_TEXT = palette.statusOutText;
    STATUS_OUT_BORDER = palette.statusOutBorder;
    STATUS_LOW_BG = palette.statusLowBg;
    STATUS_LOW_TEXT = palette.statusLowText;
    STATUS_LOW_BORDER = palette.statusLowBorder;
    STATUS_IN_BG = palette.statusInBg;
    STATUS_IN_TEXT = palette.statusInText;
    STATUS_IN_BORDER = palette.statusInBorder;
    SHADOW_SM = palette.shadowSm;
    SHADOW_MD = palette.shadowMd;

    MUTED_BACKGROUND = palette.muted;
    SECONDARY_BACKGROUND = palette.secondary;
    SECONDARY_FOREGROUND = palette.secondaryForeground;
    ACCENT = palette.accent;
    RING = palette.ring;
  }

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
    field.setCaretColor(DARK_TEXT);
    field.setSelectionColor(ACCENT);
    field.setSelectedTextColor(DARK_TEXT);
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

      label.setBackground(isSelected ? SECONDARY_BACKGROUND : INPUT_BACKGROUND);
      label.setForeground(isSelected ? SECONDARY_FOREGROUND : INPUT_TEXT);

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
    Border line = roundedBorder(RING, 1, RADIUS_MD);
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
    button.setBackground(MUTED_BACKGROUND);
    button.setForeground(DARK_TEXT);
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
