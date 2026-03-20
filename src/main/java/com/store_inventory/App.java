package com.store_inventory;

import com.store_inventory.pages.LoginPage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
  public static void main(String[] args) {
    LoginPage loginPage = new LoginPage();
    loginPage.display();
    loginPage.getLoginButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (loginPage.authenticate()) {
          // open main page
          System.out.println("Logged in");
          loginPage.dispose();
        } else {
          // show error message
          JOptionPane.showMessageDialog(loginPage, "Invalid name or password",
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }
}
