package com.store_inventory.services;

import com.store_inventory.models.Product;
import java.util.ArrayList;

/**
 * Manages the inventory of products, allowing for adding new products, updating
 * stock levels, and retrieving product information. This class serves as the
 * central point for inventory operations
 */
public class InventoryManager {
  private ArrayList<Product> products;

  public InventoryManager() { this.products = new ArrayList<>(); }

  public void addProduct(Product product) { products.add(product); }

  public void updateProduct(Product updatedProduct) {
    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).getSku().equals(updatedProduct.getSku())) {
        products.set(i, updatedProduct);
        return;
      }
    }
  }

  public boolean removeProduct(String sku) {
    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).getSku().equals(sku)) {
        products.remove(i);
        return true;
      }
    }
    return false;
  }

  public Product findProduct(String sku) {
    for (Product product : products) {
      if (product.getSku().equals(sku)) {
        return product;
      }
    }
    return null;
  }

  public ArrayList<Product> getAllProducts() { return products; }

  public void restockProduct(String sku, int quantity) {
    Product product = findProduct(sku);
    if (product != null) {
      product.setQuantity(product.getQuantity() + quantity);
    }
  }

  public void reduceStock(String sku, int quantity) {
    Product product = findProduct(sku);
    if (product != null && product.getQuantity() >= quantity) {
      product.setQuantity(product.getQuantity() - quantity);
    }
  }

  public ArrayList<Product> getLowStockProducts() {
    ArrayList<Product> lowStockProducts = new ArrayList<>();
    for (Product product : products) {
      if (product.isLowStock()) {
        lowStockProducts.add(product);
      }
    }
    return lowStockProducts;
  }

  public ArrayList<Product> getOutOfStockProducts() {
    ArrayList<Product> outOfStockProducts = new ArrayList<>();
    for (Product product : products) {
      if (product.getQuantity() == 0) {
        outOfStockProducts.add(product);
      }
    }
    return outOfStockProducts;
  }

  public double getTotalInventoryValue() {
    double totalValue = 0.0;
    for (Product product : products) {
      totalValue += product.getInventoryValue();
    }
    return totalValue;
  }

  public int getTotalUnits() {
    int totalUnits = 0;
    for (Product product : products) {
      totalUnits += product.getQuantity();
    }
    return totalUnits;
  }

  public int getInStockCount() {
    int count = 0;
    for (Product product : products) {
      if (product.getQuantity() > product.getReorderLevel()) {
        count++;
      }
    }
    return count;
  }

  public int getLowStockCount() {
    int count = 0;
    for (Product product : products) {
      if (product.getQuantity() > 0 && product.isLowStock()) {
        count++;
      }
    }
    return count;
  }

  public int getOutOfStockCount() {
    int count = 0;
    for (Product product : products) {
      if (product.getQuantity() <= 0) {
        count++;
      }
    }
    return count;
  }
}
