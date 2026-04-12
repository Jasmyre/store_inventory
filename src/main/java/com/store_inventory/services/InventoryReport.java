package com.store_inventory.services;

import com.store_inventory.models.Product;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InventoryReport extends Report {
  private static final DateTimeFormatter FILE_FORMAT =
      DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
  private final InventoryManager inventory;

  public InventoryReport(InventoryManager inventory) {
    this.inventory = inventory;
  }

  @Override
  public String generateReport() throws IOException {
    File dir = ensureExportDirectory();
    String fileName =
        "inventory_report_" + LocalDateTime.now().format(FILE_FORMAT) + ".csv";
    File file = new File(dir, fileName);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Total Inventory Value,Total Units,In Stock,Low Stock,Out of Stock");
      writer.newLine();
      writer.write(String.format("%.2f", inventory.getTotalInventoryValue()) + ","
          + inventory.getTotalUnits() + ","
          + inventory.getInStockCount() + ","
          + inventory.getLowStockCount() + ","
          + inventory.getOutOfStockCount());
      writer.newLine();
      writer.newLine();
      writer.write("Name,SKU,Category,Quantity,Unit Price,Total Value,Status");
      writer.newLine();
      for (Product product : inventory.getAllProducts()) {
        String status = product.getQuantity() <= 0 ? "Out"
            : (product.isLowStock() ? "Low" : "In Stock");
        writer.write(
            escapeCsv(product.getName()) + ","
            + escapeCsv(product.getSku()) + ","
            + escapeCsv(product.getCategory()) + ","
            + product.getQuantity() + ","
            + String.format("%.2f", product.getPrice()) + ","
            + String.format("%.2f", product.getInventoryValue()) + ","
            + status);
        writer.newLine();
      }
    }
    return file.getPath();
  }
}
