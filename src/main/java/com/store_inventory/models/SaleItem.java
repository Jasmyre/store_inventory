package com.store_inventory.models;

public class SaleItem {
    private Product product;
    private int quantity;
    private double unitPrice;

    public SaleItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }
}
