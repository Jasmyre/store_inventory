package com.store_inventory.pages.components;

import com.store_inventory.pages.Navigation;
import com.store_inventory.pages.NavigationHandler;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Header extends JPanel {
  private final JLabel titleLabel = new JLabel();
  private final JLabel userLabel = new JLabel();

  public Header(String title, String user, NavigationHandler handler) {
    setLayout(new BorderLayout());
    setBackground(UITheme.HEADER_BACKGROUND);
    setBorder(new MatteBorder(0, 0, 1, 0, UITheme.BORDER));

    JPanel left = new JPanel();
    left.setOpaque(false);
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
    left.setBorder(new EmptyBorder(12, 16, 12, 16));

    titleLabel.setFont(UITheme.TITLE_FONT);
    titleLabel.setForeground(UITheme.DARK_TEXT);
    setTitle(title);

    userLabel.setFont(UITheme.SUBTITLE_FONT);
    userLabel.setForeground(UITheme.MUTED_TEXT);
    setUser(user);

    left.add(titleLabel);
    left.add(Box.createVerticalStrut(2));
    left.add(userLabel);

    JPanel leftWrapper = new JPanel(new GridBagLayout());
    leftWrapper.setOpaque(false);
    leftWrapper.add(left, new GridBagConstraints());

    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
    right.setOpaque(false);
    right.setBorder(new EmptyBorder(0, 0, 0, 12));

    right.add(navButton("Home", Navigation.HOME, handler));
    right.add(navButton("Products", Navigation.PRODUCTS, handler));
    right.add(navButton("Inventory", Navigation.INVENTORY, handler));
    right.add(navButton("Sales", Navigation.SALES, handler));
    right.add(navButton("Reports", Navigation.REPORTS, handler));
    right.add(logoutButton(handler));

    add(leftWrapper, BorderLayout.WEST);
    add(right, BorderLayout.EAST);
  }

  public void setTitle(String title) { titleLabel.setText(title); }

  public void setUser(String user) {
    if (user == null || user.trim().isEmpty()) {
      userLabel.setText("");
      userLabel.setVisible(false);
      return;
    }
    userLabel.setText("User: " + user.trim());
    userLabel.setVisible(true);
  }

  private JButton navButton(String label, String destination,
                            NavigationHandler handler) {
    JButton button = UITheme.secondaryButton(label);
    button.setFont(new Font("SansSerif", Font.PLAIN, 20));
    button.setMargin(new Insets(12, 16, 12, 16));
    button.addActionListener(e -> handler.navigate(destination));
    return button;
  }

  private JButton logoutButton(NavigationHandler handler) {
    JButton button = UITheme.secondaryButton("Logout");
    button.setFont(new Font("SansSerif", Font.PLAIN, 20));
    button.setMargin(new Insets(12, 16, 12, 16));
    button.addActionListener(e -> handler.logout());
    return button;
  }
}
