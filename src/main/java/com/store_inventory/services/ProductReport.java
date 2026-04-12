package com.store_inventory.services;

import com.store_inventory.models.Product;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductReport extends Report {
  private static final DateTimeFormatter FILE_FORMAT =
      DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
  private final InventoryManager inventory;

  public ProductReport(InventoryManager inventory) {
    this.inventory = inventory;
  }

  @Override
  public String generateReport() throws IOException {
    File dir = ensureExportDirectory();
    String fileName =
        "product_report_" + LocalDateTime.now().format(FILE_FORMAT) + ".csv";
    File file = new File(dir, fileName);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Name,SKU,Category,Quantity,Price");
      writer.newLine();
      for (Product product : inventory.getAllProducts()) {
        writer.write(
            escapeCsv(product.getName()) + ","
            + escapeCsv(product.getSku()) + ","
            + escapeCsv(product.getCategory()) + ","
            + product.getQuantity() + ","
            + String.format("%.2f", product.getPrice()));
        writer.newLine();
      }
    }
    return file.getPath();
  }
}
