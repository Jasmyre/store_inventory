# Store Inventory System - Project Documentation

## 1. System Overview
Store Inventory is a desktop inventory and sales tracking application built with Java Swing. It provides a login screen, a multi-page management interface, and reporting/export tools for products, inventory, and sales.

The app is designed for day-to-day store operations such as:
- maintaining a product catalog,
- monitoring stock levels and low-stock conditions,
- recording sales transactions, and
- exporting CSV reports for analysis or sharing.

## 2. Core User Flows
### 2.1 Access and Navigation
- Users start at a login screen with name/password inputs and a show-password toggle.
- Current login validation uses a fixed credential pair: username `admin` and password `password`.
- After login, users enter the main dashboard with page navigation in the header:
  - Home
  - Products
  - Inventory
  - Sales
  - Reports
- The header also shows the current user name and supports logout.

### 2.2 Home Dashboard
The Home page provides a quick operational summary:
- Total products
- Total inventory units
- Total sales revenue (PHP)

This gives a high-level snapshot of store performance.

### 2.3 Product Management
The Products page supports full product lifecycle management:
- Add product
- Edit product
- Delete product

Each product contains:
- Product ID (SKU)
- Name
- Category
- Unit price
- Quantity
- Reorder level

Validation and safeguards include:
- required field validation,
- numeric validation for price/quantity/reorder level,
- duplicate SKU prevention when adding,
- delete confirmation dialog before removal.

### 2.4 Inventory Monitoring
The Inventory page focuses on stock visibility and control:
- Summary cards for:
  - Total Products
  - In Stock
  - Low Stock
  - Out of Stock
- Search by product name or SKU
- Filter by stock status:
  - All
  - In Stock
  - Low Stock
  - Out of Stock

Each item row shows:
- product metadata,
- stock and reorder level,
- computed status badge (In Stock, Low Stock, Out of Stock).

### 2.5 Sales Recording
The Sales page supports transaction entry and tracking:
- Add Sale flow allows selecting a product and quantity.
- Date is auto-filled (current date).
- Unit price and total are auto-calculated.
- Stock is validated before sale is recorded.
- When a sale is saved:
  - a transaction is created,
  - stock is reduced accordingly,
  - dashboard/sales metrics update.

Sales analytics shown on the page:
- Total sales count
- Total revenue
- Total units sold
- Transaction line items table

### 2.6 Reporting and Export
The Reports page provides three report views:
- Product Report
- Sales Report
- Inventory Report

For each report, users can:
- view report details in-app,
- export report data using **Export Data**.

Export behavior:
- Files are generated as CSV.
- Files are written into the `exports/` directory.
- File names include timestamps, for example:
  - `product_report_YYYYMMDD_HHMMSS.csv`
  - `sales_report_YYYYMMDD_HHMMSS.csv`
  - `inventory_report_YYYYMMDD_HHMMSS.csv`

## 3. Seed Data and Startup Behavior
On startup, the application currently preloads demo products and sample sales transactions.

This means the system starts with non-empty data by default, which is useful for demo/testing and immediate UI walkthroughs.

## 4. Current Data Behavior (Important)
The current application behavior is in-memory during runtime:
- Product and sales data are held in memory while the app is open.
- CSV exports create files on disk.
- No database or long-term persistence layer is currently active for core runtime data.

Practical result:
- runtime changes are not automatically restored on next launch,
- exported CSV files remain available in `exports/`.

## 5. Currency and Localization
- Monetary values are displayed in Philippine Peso format (`PHP`).
- Dates in sales/report tables are displayed in `yyyy-MM-dd` format.

## 6. Intended Operational Use
This system is suited for:
- small-store inventory and sales monitoring,
- demo and training workflows,
- lightweight reporting/export use cases without mandatory backend setup.

## 7. Feature Summary
- Login screen with show-password option
- Header-based multi-page navigation and logout
- Home dashboard with key totals
- Product create/edit/delete with validation
- Inventory search and status-based filtering
- Sales entry with stock checks and auto pricing totals
- Product/Sales/Inventory report viewing
- CSV export for all major reports

## 8. Project Scope
The current project scope covers:
- Desktop-based store inventory and sales operations in a single application.
- Product catalog management (add, edit, delete, basic validation).
- Inventory monitoring with stock-status filtering and search.
- Sales transaction recording with stock deduction and sales summaries.
- Operational reporting for products, sales, and inventory.
- CSV file export for generated reports.
- Demo-ready startup behavior with seeded sample data.

Out of scope for this version:
- Advanced user roles and permissions.
- Multi-branch or multi-store inventory synchronization.
- Purchase order management and supplier workflows.
- Barcode scanning, receipt printing, and POS hardware integration.
- Cloud/mobile companion applications.

## 9. Project Limitations
Current implementation limitations include:
- Authentication is not secure in its current form (it relies on a hardcoded username/password pair: `admin` / `password`).
- Runtime data is in-memory only; product/sales changes are not persisted across app restarts.
- Report exports are limited to CSV format only.
- The system is designed for single-user local execution and does not provide multi-user concurrency controls.
- Date/time, currency format, and labels are fixed in-app and are not configurable by users.
- Error handling and validation are focused on UI-level checks; there is no external persistence or transactional recovery layer.
