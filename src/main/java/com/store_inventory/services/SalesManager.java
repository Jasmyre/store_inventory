package com.store_inventory.services;

import com.store_inventory.models.SaleTransaction;
import java.util.ArrayList;

/**
 * Manages sales transactions, allowing for recording new sales, retrieving past
 * transactions, and calculating total revenue and units sold. This class serves
 * as the central point for sales operations, providing methods to track and
 * analyze sales data effectively.
 */
public class SalesManager {
  private ArrayList<SaleTransaction> transactions;

  public SalesManager() { this.transactions = new ArrayList<>(); }

  public void recordTransaction(SaleTransaction transaction) {
    transactions.add(transaction);
  }

  public ArrayList<SaleTransaction> getAllTransactions() {
    return transactions;
  }

  public ArrayList<SaleTransaction> getRecentSales(int limit) {
    int size = transactions.size();
    if (size <= limit) {
      return new ArrayList<>(transactions);
    }

    ArrayList<SaleTransaction> recentSales = new ArrayList<>();
    for (int i = Math.max(0, size - limit); i < size; i++) {
      recentSales.add(transactions.get(i));
    }
    return recentSales;
  }

  public double getTotalRevenue() {
    double totalRevenue = 0.0;
    for (SaleTransaction transaction : transactions) {
      totalRevenue += transaction.getTotalAmount();
    }
    return totalRevenue;
  }

  public int getTotalUnitsSold() {
    int totalUnits = 0;
    for (SaleTransaction transaction : transactions) {
      for (var item : transaction.getItems()) {
        totalUnits += item.getQuantity();
      }
    }
    return totalUnits;
  }

  public int getTotalSalesCount() { return transactions.size(); }
}
