package com.store_inventory.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a sale transaction, containing details such as transaction ID,
 * date, list of items sold, and total amount. Provides methods to add items and
 */
public class SaleTransaction {
  private String transactionID;
  private LocalDateTime date;
  private ArrayList<SaleItem> items;
  private double totalAmount;

  public SaleTransaction(String transactionId) {
    this.transactionID = transactionId;
    this.date = LocalDateTime.now();
    this.items = new ArrayList<>();
    this.totalAmount = 0.0;
  }

  public void addItem(SaleItem item) {
    items.add(item);
    totalAmount += item.getSubtotal();
  }

  public double calculateTotal() { return totalAmount; }

  public ArrayList<SaleItem> getItems() { return items; }

  public double getTotalAmount() { return totalAmount; }

  public LocalDateTime getDate() { return date; }

  public String getTransactionID() { return transactionID; }
}
