package com.store_inventory.services;

import com.store_inventory.models.Product;
import com.store_inventory.models.SaleItem;
import com.store_inventory.models.SaleTransaction;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesReport extends Report {
  private static final DateTimeFormatter FILE_FORMAT =
      DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
  private static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final SalesManager sales;

  public SalesReport(SalesManager sales) {
    this.sales = sales;
  }

  @Override
  public String generateReport() throws IOException {
    File dir = ensureExportDirectory();
    String fileName =
        "sales_report_" + LocalDateTime.now().format(FILE_FORMAT) + ".csv";
    File file = new File(dir, fileName);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Total Revenue,Total Transactions,Total Units Sold");
      writer.newLine();
      writer.write(String.format("%.2f", sales.getTotalRevenue()) + ","
          + sales.getTotalSalesCount() + ","
          + sales.getTotalUnitsSold());
      writer.newLine();
      writer.newLine();
      writer.write("Date,Transaction ID,Product,Quantity,Unit Price,Subtotal");
      writer.newLine();
      for (SaleTransaction transaction : sales.getAllTransactions()) {
        String date = transaction.getDate().format(DATE_FORMAT);
        for (SaleItem item : transaction.getItems()) {
          Product product = item.getProduct();
          String productName = product != null ? product.getName() : "Unknown";
          writer.write(
              escapeCsv(date) + ","
              + escapeCsv(transaction.getTransactionID()) + ","
              + escapeCsv(productName) + ","
              + item.getQuantity() + ","
              + String.format("%.2f", item.getUnitPrice()) + ","
              + String.format("%.2f", item.getSubtotal()));
          writer.newLine();
        }
      }
    }
    return file.getPath();
  }
}
