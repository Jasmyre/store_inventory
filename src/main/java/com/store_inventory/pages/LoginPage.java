package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginPage extends JPanel {
  private final JTextField nameField = new JTextField(20);
  private final JPasswordField passwordField = new JPasswordField(20);
  private final JButton loginButton = UITheme.primaryButton("Log In");

  public LoginPage() {
    setLayout(new BorderLayout());
    setBackground(UITheme.BACKGROUND);

    Border line = UITheme.roundedBorder(UITheme.BORDER, 1, 12);
    Border padding = new EmptyBorder(8, 8, 8, 8);
    UITheme.themeTextField(nameField);
    UITheme.themeTextField(passwordField);
    nameField.setBorder(new CompoundBorder(line, padding));
    passwordField.setBorder(new CompoundBorder(line, padding));

    JPanel center = new JPanel(new GridBagLayout());
    center.setOpaque(false);
    add(center, BorderLayout.CENTER);

    JPanel card = UITheme.cardPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBorder(new javax.swing.border.CompoundBorder(
        card.getBorder(), new EmptyBorder(28, 32, 28, 32)));

    JLabel title = new JLabel("Welcome to the System");
    title.setFont(UITheme.TITLE_FONT);
    title.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(title);

    JLabel subtitle = new JLabel("Verify your identity");
    subtitle.setFont(UITheme.SUBTITLE_FONT);
    subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(subtitle);

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setFont(UITheme.LABEL_FONT);
    nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(nameLabel);

    nameField.setMaximumSize(new Dimension(320, 28));
    nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setFont(UITheme.LABEL_FONT);
    passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    UITheme.themeLabel(passwordLabel);

    passwordField.setMaximumSize(new Dimension(320, 28));
    passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

    loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    card.add(title);
    card.add(Box.createVerticalStrut(6));
    card.add(subtitle);
    card.add(Box.createVerticalStrut(18));
    card.add(nameLabel);
    card.add(Box.createVerticalStrut(6));
    card.add(nameField);
    card.add(Box.createVerticalStrut(12));
    card.add(passwordLabel);
    card.add(Box.createVerticalStrut(6));
    card.add(passwordField);
    card.add(Box.createVerticalStrut(18));
    card.add(loginButton);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(8, 8, 8, 8);
    center.add(card, gbc);
  }

  public JButton getLoginButton() { return loginButton; }

  public String getUsername() { return nameField.getText(); }

  public void clearFields() {
    nameField.setText("");
    passwordField.setText("");
  }

  public boolean authenticate() {
    // String name = nameField.getText();
    // String password = new String(passwordField.getPassword());
    // return name.equals("admin") && password.equals("password");
    return true;
  }
}
