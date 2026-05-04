# Store Inventory

A Java Swing desktop application for managing products, tracking inventory, recording sales, and exporting operational reports. (test lol)

## Overview
Store Inventory is designed for small-store operations that need a lightweight desktop tool for daily stock and sales monitoring.

Main workflows:
- manage products (add, edit, delete),
- monitor stock status (in stock, low stock, out of stock),
- record sales transactions with stock deduction,
- generate and export CSV reports.

## Features
- Login page with username/password fields, show-password toggle, and basic credential check (`admin` / `password`)
- Header navigation across Home, Products, Inventory, Sales, and Reports pages
- Home dashboard with summary metrics:
  - total products,
  - total inventory units,
  - total sales revenue
- Products management with form validation and duplicate SKU checks
- Inventory page with search and stock-status filters
- Sales entry dialog with:
  - product selection,
  - quantity input,
  - auto-computed pricing,
  - stock availability validation
- Reports page with three report types:
  - Product Report
  - Sales Report
  - Inventory Report
- CSV export support to local `exports/` directory

## Project Scope
In scope:
- single desktop application for product, inventory, and sales operations
- report generation and CSV exports
- demo-ready seeded startup data for quick walkthroughs

Out of scope (current version):
- role-based access control and permissions
- multi-store synchronization
- supplier/purchase-order workflows
- POS hardware integration (barcode scanner, printer)
- cloud/mobile deployment targets

## Current Limitations
- Authentication is hardcoded to a single static credential pair (`admin` / `password`) and is not production-grade security.
- Runtime data is in-memory; changes are not persisted after application restart.
- Export format is CSV only.
- The application is intended for local/single-user usage.
- Localization/configuration options (currency/date formatting, labels) are limited.

## Tech Stack
- Java 21
- Swing (desktop UI)
- Maven (build tool)

## Project Structure
- `src/main/java/com/store_inventory` - app entry point, services, pages, models
- `src/main/resources` - assets and supporting resources
- `exports` - generated report output directory (created on demand)

## Setup and Execution
### Prerequisites
- JDK 21 installed
- Maven 3.9+ installed

Verify:
```bash
java -version
mvn -version
```

### 1. Clone and open the project
```bash
git clone https://github.com/Jasmyre/store_inventory
cd store_inventory
```

### 2. Build
```bash
mvn clean compile
```

### 3. Run the application
Option A: run with Maven
```bash
mvn exec:java -Dexec.mainClass=com.store_inventory.App
```

Option B: run compiled classes directly
```bash
mvn clean compile
java -cp target/classes com.store_inventory.App
```

### 4. Package (optional)
```bash
mvn clean package
```

## Report Exports
When exporting from the Reports page, files are generated under `exports/` with timestamped names:
- `product_report_YYYYMMDD_HHMMSS.csv`
- `sales_report_YYYYMMDD_HHMMSS.csv`
- `inventory_report_YYYYMMDD_HHMMSS.csv`

