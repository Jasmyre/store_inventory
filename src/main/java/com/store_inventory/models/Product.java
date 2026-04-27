package com.store_inventory.models;

/**
 * Represents a product in the inventory, encapsulating details such as SKU,
 * name, category, price, quantity, and reorder level. Provides methods to check
 * stock status and calculate inventory value.
 */
public class Product {
  private String sku;
  private String name;
  private String category;
  private double price;
  private int quantity;
  private int reorderLevel;

  public Product(String sku, String name, String category, double price,
                 int quantity, int reorderLevel) {
    this.sku = sku;
    this.name = name;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
    this.reorderLevel = reorderLevel;
  }

  public String getSku() { return sku; }
  public String getName() { return name; }
  public String getCategory() { return category; }
  public double getPrice() { return price; }
  public int getQuantity() { return quantity; }
  public int getReorderLevel() { return reorderLevel; }

  public void setPrice(double price) { this.price = price; }
  public void setQuantity(int quantity) { this.quantity = quantity; }
  public void setReorderLevel(int reorderLevel) {
    this.reorderLevel = reorderLevel;
  }

  public boolean isLowStock() { return quantity <= reorderLevel; }
  public double getInventoryValue() { return price * quantity; }

  @Override
  public String toString() {
    return name + " (" + sku + ")";
  }
}
