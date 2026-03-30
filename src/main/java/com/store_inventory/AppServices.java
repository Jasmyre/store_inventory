package com.store_inventory;

import com.store_inventory.models.Product;
import com.store_inventory.models.SaleItem;
import com.store_inventory.models.SaleTransaction;
import com.store_inventory.services.InventoryManager;
import com.store_inventory.services.SalesManager;
import java.util.UUID;

public class AppServices {
  private final InventoryManager inventoryManager;
  private final SalesManager salesManager;

  public AppServices() {
    inventoryManager = new InventoryManager();
    salesManager = new SalesManager();
    seedData();
  }

  public InventoryManager getInventoryManager() { return inventoryManager; }

  public SalesManager getSalesManager() { return salesManager; }

  private void seedData() {
    inventoryManager.addProduct(
        new Product("101", "Wireless Earbuds", "Audio", 799.0, 32, 10));
    inventoryManager.addProduct(
        new Product("102", "Bluetooth Speaker", "Audio", 1299.0, 12, 8));
    inventoryManager.addProduct(
        new Product("103", "Phone Case", "Accessories", 249.0, 68, 15));
    inventoryManager.addProduct(
        new Product("104", "USB-C Charger", "Accessories", 399.0, 45, 12));
    inventoryManager.addProduct(
        new Product("105", "Laptop Stand", "Office", 1199.0, 7, 10));
    inventoryManager.addProduct(
        new Product("106", "Smart Bulb", "Home", 499.0, 0, 6));

    recordSeedSale("101", 2);
    recordSeedSale("105", 1);
    recordSeedSale("103", 5);
  }

  private void recordSeedSale(String sku, int quantity) {
    Product product = inventoryManager.findProduct(sku);
    if (product == null || product.getQuantity() < quantity) {
      return;
    }
    SaleTransaction transaction =
        new SaleTransaction("TX-" + UUID.randomUUID());
    transaction.addItem(new SaleItem(product, quantity));
    salesManager.recordTransaction(transaction);
    inventoryManager.reduceStock(sku, quantity);
  }
}
