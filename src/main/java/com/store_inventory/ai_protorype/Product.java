package com.store_inventory.ai_protorype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private double price;
    private int quantity;

    @JsonCreator
    public Product(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("category") Category category,
            @JsonProperty("price") double price,
            @JsonProperty("quantity") int quantity
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id is required.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product category is required.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double p) {
        if (p < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = p;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        if (q < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = q;
    }

    public void increaseQuantity(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Increase amount cannot be negative.");
        }
        this.quantity += delta;
    }

    public void decreaseQuantity(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Decrease amount cannot be negative.");
        }
        if (delta > this.quantity) {
            throw new IllegalArgumentException("Insufficient stock.");
        }
        this.quantity -= delta;
    }

    @JsonIgnore
    public double getInventoryValue() {
        return price * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
