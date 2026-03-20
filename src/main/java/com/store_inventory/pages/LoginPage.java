package com.store_inventory.pages;

import java.awt.*;
import javax.swing.*;

public class LoginPage extends JFrame {
  private GridBagConstraints gbc = new GridBagConstraints();

  private JPanel header = new JPanel();
  private JPanel body = new JPanel();

  private JTextField nameField = new JTextField(20);
  private JPasswordField passwordField = new JPasswordField(20);
  private JButton loginButton = new JButton("Login");

  public JButton getLoginButton() { return loginButton; }

  public LoginPage() {
    setTitle("Login Page");
    setSize(700, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    header.setLayout(new GridBagLayout());
    JLabel headerTitle = new JLabel("Welcome to the System");
    headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
    gbc.gridx = 0;
    gbc.gridy = 0;
    header.add(headerTitle, gbc);

    JLabel headerDescription = new JLabel("Verify your identity");
    headerDescription.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridy = 1;
    header.add(headerDescription, gbc);

    header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(header, BorderLayout.NORTH);

    body.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(body, BorderLayout.CENTER);

    body.setLayout(new GridBagLayout());
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    body.add(
        new JPanel(new FlowLayout(FlowLayout.LEFT)).add(new JLabel("Name:")),
        gbc);

    gbc.gridy = 1;
    body.add(nameField, gbc);

    gbc.gridy = 3;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    body.add(new JPanel(new FlowLayout(FlowLayout.LEFT))
                 .add(new JLabel("Password:")),
             gbc);

    gbc.gridy = 4;
    body.add(passwordField, gbc);

    gbc.gridy = 5;
    gbc.insets = new Insets(20, 5, 5, 5);
    loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    body.add(loginButton, gbc);
  }

  public void display() { setVisible(true); }
  public boolean authenticate() {
    String name = nameField.getText();
    String password = new String(passwordField.getPassword());

    return name.equals("admin") && password.equals("password");
  }
}
