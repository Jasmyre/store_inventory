package com.store_inventory.services;

import java.io.File;
import java.io.IOException;

public abstract class Report {
  public static final String EXPORT_DIRECTORY = "exports";

  public abstract String generateReport() throws IOException;

  protected static File ensureExportDirectory() {
    File dir = new File(EXPORT_DIRECTORY);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  protected static String escapeCsv(String value) {
    if (value == null) {
      return "";
    }
    boolean needsQuotes = value.contains(",") || value.contains("\"")
        || value.contains("\n") || value.contains("\r");
    String escaped = value.replace("\"", "\"\"");
    return needsQuotes ? "\"" + escaped + "\"" : escaped;
  }
}
