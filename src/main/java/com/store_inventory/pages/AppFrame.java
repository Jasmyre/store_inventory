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
  private final CardLayout rootLayout = new CardLayout();
  private final CardLayout pageLayout = new CardLayout();
  private final JPanel rootPanel = new JPanel(rootLayout);
  private final JPanel pagePanel = new JPanel(pageLayout);
  private final LoginPage loginPage = new LoginPage();
  private final Header header;
  private final Map<String, String> titles = new HashMap<>();
  private final Map<String, JPanel> pages = new HashMap<>();
  private final AppServices services;

  private final HomePage homePage;
  private final ProductsPage productsPage;
  private final InventoryPage inventoryPage;
  private final SalesPage salesPage;
  private final ReportsPage reportsPage;

  public AppFrame(AppServices services) {
    this.services = services;
    setTitle("Store Inventory");
    // setUndecorated(true);
    setSize(1100, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    titles.put(Navigation.HOME, "Home Page");
    titles.put(Navigation.PRODUCTS, "Products Page");
    titles.put(Navigation.INVENTORY, "Inventory Page");
    titles.put(Navigation.SALES, "Sales Page");
    titles.put(Navigation.REPORTS, "Reports Page");
    titles.put(Navigation.REPORT_DETAIL, "Product Report");

    header = new Header("Home Page", "", this);

    homePage = new HomePage(services.getInventoryManager(),
                            services.getSalesManager());
    productsPage = new ProductsPage(services.getInventoryManager());
    inventoryPage = new InventoryPage(services.getInventoryManager());
    salesPage = new SalesPage(services.getInventoryManager(),
                              services.getSalesManager());
    reportsPage = new ReportsPage(this, services.getInventoryManager(),
                                  services.getSalesManager());

    buildRoot();
    setLayout(new BorderLayout());
    // add(new WindowTitleBar(this, "Store Inventory"), BorderLayout.NORTH);
    add(rootPanel, BorderLayout.CENTER);

    loginPage.getLoginButton().addActionListener(e -> {
      if (loginPage.authenticate()) {
        header.setUser(loginPage.getUsername());
        showApp();
      } else {
        JOptionPane.showMessageDialog(this, "Invalid name or password", "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  private void buildRoot() {
    rootPanel.add(loginPage, Navigation.LOGIN);

    JPanel appPanel = new JPanel(new BorderLayout());
    appPanel.setBackground(UITheme.BACKGROUND);
    appPanel.add(header, BorderLayout.NORTH);

    pagePanel.setBackground(UITheme.BACKGROUND);
    addPage(Navigation.HOME, homePage);
    addPage(Navigation.PRODUCTS, productsPage);
    addPage(Navigation.INVENTORY, inventoryPage);
    addPage(Navigation.SALES, salesPage);
    addPage(Navigation.REPORTS, reportsPage);

    appPanel.add(pagePanel, BorderLayout.CENTER);

    rootPanel.add(appPanel, Navigation.APP);
    rootLayout.show(rootPanel, Navigation.LOGIN);
  }

  private void showApp() {
    rootLayout.show(rootPanel, Navigation.APP);
    navigate(Navigation.HOME);
  }

  @Override
  public void navigate(String destination) {
    pageLayout.show(pagePanel, destination);
    String title = titles.getOrDefault(destination, "Page");
    header.setTitle(title);
    JPanel page = pages.get(destination);
    if (page instanceof Refreshable) {
      ((Refreshable) page).refresh();
    }
  }

  @Override
  public void logout() {
    loginPage.clearFields();
    rootLayout.show(rootPanel, Navigation.LOGIN);
  }

  private void addPage(String key, JPanel page) {
    pages.put(key, page);
    pagePanel.add(page, key);
  }
}
