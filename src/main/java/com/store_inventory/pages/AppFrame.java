package com.store_inventory.pages;

import com.store_inventory.AppServices;
import com.store_inventory.pages.components.Header;
import com.store_inventory.pages.components.UITheme;
import com.store_inventory.pages.components.WindowTitleBar;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AppFrame extends JFrame implements NavigationHandler {
  private CardLayout rootLayout;
  private CardLayout pageLayout;
  private JPanel rootPanel;
  private JPanel pagePanel;
  private LoginPage loginPage;
  private Header header;
  private WindowTitleBar titleBar;
  private final Map<String, String> titles = new HashMap<>();
  private final Map<String, JPanel> pages = new HashMap<>();
  private final AppServices services;
  private String currentDestination = Navigation.HOME;
  private String currentUser = "";
  private boolean appVisible = false;

  public AppFrame(AppServices services) {
    this.services = services;
    setTitle("Store Inventory");
    setUndecorated(true);
    setSize(1100, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    initializeTitles();
    rebuildUI();
  }

  private void initializeTitles() {
    titles.put(Navigation.HOME, "Home Page");
    titles.put(Navigation.PRODUCTS, "Products Page");
    titles.put(Navigation.INVENTORY, "Inventory Page");
    titles.put(Navigation.SALES, "Sales Page");
    titles.put(Navigation.REPORTS, "Reports Page");
    titles.put(Navigation.REPORT_DETAIL, "Product Report");
  }

  private void rebuildUI() {
    rootLayout = new CardLayout();
    pageLayout = new CardLayout();
    rootPanel = new JPanel(rootLayout);
    pagePanel = new JPanel(pageLayout);
    loginPage = new LoginPage();

    String currentTitle = titles.getOrDefault(currentDestination, "Page");
    header = new Header(currentTitle, currentUser, this);
    titleBar = new WindowTitleBar(this, "Store Inventory");

    getContentPane().removeAll();
    buildRoot();
    setLayout(new BorderLayout());
    add(titleBar, BorderLayout.NORTH);
    add(rootPanel, BorderLayout.CENTER);
    wireLoginAction();

    if (appVisible) {
      rootLayout.show(rootPanel, Navigation.APP);
      header.setUser(currentUser);
      navigate(resolveDestination(currentDestination));
    } else {
      rootLayout.show(rootPanel, Navigation.LOGIN);
    }

    revalidate();
    repaint();
  }

  private void buildRoot() {
    pages.clear();
    rootPanel.add(loginPage, Navigation.LOGIN);

    JPanel appPanel = new JPanel(new BorderLayout());
    appPanel.setBackground(UITheme.BACKGROUND);
    appPanel.add(header, BorderLayout.NORTH);

    pagePanel.setBackground(UITheme.BACKGROUND);
    addPage(Navigation.HOME,
            new HomePage(services.getInventoryManager(),
                         services.getSalesManager()));
    addPage(Navigation.PRODUCTS, new ProductsPage(services.getInventoryManager()));
    addPage(Navigation.INVENTORY,
            new InventoryPage(services.getInventoryManager()));
    addPage(Navigation.SALES,
            new SalesPage(services.getInventoryManager(),
                          services.getSalesManager()));
    addPage(Navigation.REPORTS,
            new ReportsPage(this, services.getInventoryManager(),
                            services.getSalesManager()));

    appPanel.add(pagePanel, BorderLayout.CENTER);

    rootPanel.add(appPanel, Navigation.APP);
  }

  private void wireLoginAction() {
    loginPage.getLoginButton().addActionListener(e -> {
      if (loginPage.authenticate()) {
        currentUser = loginPage.getUsername();
        currentDestination = Navigation.HOME;
        appVisible = true;
        showApp();
      } else {
        JOptionPane.showMessageDialog(this, "Invalid name or password", "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  private void showApp() {
    rootLayout.show(rootPanel, Navigation.APP);
    header.setUser(currentUser);
    navigate(resolveDestination(currentDestination));
  }

  @Override
  public void navigate(String destination) {
    currentDestination = resolveDestination(destination);
    String title = titles.getOrDefault(currentDestination, "Page");
    header.setTitle(title);
    pageLayout.show(pagePanel, currentDestination);
    JPanel page = pages.get(currentDestination);
    if (page instanceof Refreshable) {
      ((Refreshable) page).refresh();
    }
  }

  @Override
  public void logout() {
    currentUser = "";
    appVisible = false;
    currentDestination = Navigation.HOME;
    loginPage.clearFields();
    rootLayout.show(rootPanel, Navigation.LOGIN);
  }

  @Override
  public void changeTheme(UITheme.ThemeMode mode) {
    if (mode == null || mode == UITheme.getThemeMode()) {
      return;
    }
    UITheme.setThemeMode(mode);
    rebuildUI();
  }

  private String resolveDestination(String destination) {
    if (destination == null || !pages.containsKey(destination)) {
      return Navigation.HOME;
    }
    return destination;
  }

  private void addPage(String key, JPanel page) {
    pages.put(key, page);
    pagePanel.add(page, key);
  }
}
