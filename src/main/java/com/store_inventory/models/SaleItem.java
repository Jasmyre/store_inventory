package com.store_inventory.models;

/**
 * Represents an individual item in a sale transaction, linking a product to the
 * quantity sold and the unit price at the time of sale.
 */
public class SaleItem {
  private Product product;
  private int quantity;
  private double unitPrice;

  public SaleItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    this.unitPrice = product.getPrice();
  }

  public int getQuantity() { return quantity; }
  public double getSubtotal() { return unitPrice * quantity; }
  public Product getProduct() { return product; }
  public double getUnitPrice() { return unitPrice; }
}
