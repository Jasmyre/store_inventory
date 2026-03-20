package com.store_inventory.ai_protorype;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

public class MainMenuUI {
    private final InventoryManager inventory;
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final JPanel homePanel;
    private final ManageProductsUI manageProductsUI;
    private final UpdateStockUI updateStockUI;
    private final InventoryViewUI inventoryViewUI;
    private JLabel totalProductsLabel;
    private JLabel totalItemsLabel;
    private JLabel totalValueLabel;
    private final DecimalFormat moneyFormat;

    public MainMenuUI(InventoryManager inventory) {
        this.inventory = inventory;
        this.frame = new JFrame("Store Inventory");
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.moneyFormat = new DecimalFormat("#,##0.00");

        UIStyles.applyGlobalDefaults();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1100, 720));
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(UIStyles.BG);
        root.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel nav = new JPanel(new BorderLayout(16, 16));
        nav.setBackground(UIStyles.BG);
        JLabel title = UIStyles.titleLabel("Store Inventory");
        nav.add(title, BorderLayout.WEST);

        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        navButtons.setBackground(UIStyles.BG);
        JButton homeBtn = UIStyles.ghostButton("Home");
        JButton manageBtn = UIStyles.ghostButton("Manage Products");
        JButton updateBtn = UIStyles.ghostButton("Update Stock");
        JButton viewBtn = UIStyles.ghostButton("Inventory");
        navButtons.add(homeBtn);
        navButtons.add(manageBtn);
        navButtons.add(updateBtn);
        navButtons.add(viewBtn);
        nav.add(navButtons, BorderLayout.EAST);

        root.add(nav, BorderLayout.NORTH);

        homePanel = buildHomePanel();
        manageProductsUI = new ManageProductsUI(inventory);
        updateStockUI = new UpdateStockUI(inventory);
        inventoryViewUI = new InventoryViewUI(inventory);

        cardPanel.setBackground(UIStyles.BG);
        cardPanel.add(homePanel, "home");
        cardPanel.add(wrapScroll(manageProductsUI), "manage");
        cardPanel.add(updateStockUI, "update");
        cardPanel.add(inventoryViewUI, "view");

        root.add(cardPanel, BorderLayout.CENTER);
        frame.setContentPane(root);

        homeBtn.addActionListener(e -> showCard("home"));
        manageBtn.addActionListener(e -> openManageProducts());
        updateBtn.addActionListener(e -> openUpdateStock());
        viewBtn.addActionListener(e -> openViewInventory());
    }

    public void show() {
        refreshHomeStats();
        frame.setVisible(true);
        showCard("home");
    }

    public void openManageProducts() {
        manageProductsUI.refresh();
        showCard("manage");
    }

    public void openUpdateStock() {
        updateStockUI.refresh();
        showCard("update");
    }

    public void openViewInventory() {
        inventoryViewUI.refresh();
        showCard("view");
    }

    private void showCard(String name) {
        if ("home".equals(name)) {
            refreshHomeStats();
        }
        cardLayout.show(cardPanel, name);
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private JPanel buildHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setBackground(UIStyles.BG);

        JPanel welcome = new JPanel(new GridLayout(2, 1));
        welcome.setBackground(UIStyles.BG);
        welcome.add(UIStyles.titleLabel("Welcome to the System"));
        welcome.add(UIStyles.mutedLabel("Track stock levels and inventory value at a glance."));
        panel.add(welcome, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 16, 16));
        content.setBackground(UIStyles.BG);

        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 12, 12));
        statsPanel.setBackground(UIStyles.BG);
        totalProductsLabel = UIStyles.headerLabel("Total Products: 0");
        totalItemsLabel = UIStyles.headerLabel("Total Stock Items: 0");
        totalValueLabel = UIStyles.headerLabel("Inventory Value: 0.00");
        statsPanel.add(wrapStatCard(totalProductsLabel));
        statsPanel.add(wrapStatCard(totalItemsLabel));
        statsPanel.add(wrapStatCard(totalValueLabel));

        JPanel chartPanel = UIStyles.cardPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(UIStyles.headerLabel("Inventory Overview"), BorderLayout.NORTH);
        JLabel placeholder = UIStyles.mutedLabel("Chart placeholder");
        placeholder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chartPanel.add(placeholder, BorderLayout.CENTER);

        content.add(statsPanel);
        content.add(chartPanel);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel wrapStatCard(JLabel label) {
        JPanel card = UIStyles.cardPanel();
        card.setLayout(new BorderLayout());
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private JScrollPane wrapScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIStyles.BG);
        return scroll;
    }

    private void refreshHomeStats() {
        int totalProducts = inventory.listProducts().size();
        int totalItems = inventory.listProducts().stream().mapToInt(Product::getQuantity).sum();
        double totalValue = inventory.getTotalInventoryValue();
        totalProductsLabel.setText("Total Products: " + totalProducts);
        totalItemsLabel.setText("Total Stock Items: " + totalItems);
        totalValueLabel.setText("Inventory Value: " + moneyFormat.format(totalValue));
    }
}
