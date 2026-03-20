package com.store_inventory.ai_protorype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryManager {
    private final List<Product> products;
    private final StorageManager storage;

    public InventoryManager() {
        this(new StorageManager("inventory.json"));
    }

    public InventoryManager(StorageManager storage) {
        if (storage == null) {
            throw new IllegalArgumentException("Storage manager is required.");
        }
        this.storage = storage;
        this.products = new ArrayList<>();
    }

    public boolean addProduct(Product p) {
        if (p == null) {
            return false;
        }
        if (findProductById(p.getId()) != null) {
            return false;
        }
        return products.add(p);
    }

    public boolean updateProduct(Product p) {
        if (p == null) {
            return false;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(p.getId())) {
                products.set(i, p);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(String productId) {
        if (productId == null) {
            return false;
        }
        return products.removeIf(p -> p.getId().equalsIgnoreCase(productId));
    }

    public Product findProductById(String productId) {
        if (productId == null) {
            return null;
        }
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(productId)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> listProducts() {
        return Collections.unmodifiableList(products);
    }

    public boolean restockProduct(String productId, int amount) {
        Product product = findProductById(productId);
        if (product == null || amount < 0) {
            return false;
        }
        product.increaseQuantity(amount);
        return true;
    }

    public boolean recordSale(String productId, int amount) {
        Product product = findProductById(productId);
        if (product == null || amount < 0) {
            return false;
        }
        product.decreaseQuantity(amount);
        return true;
    }

    public double getTotalInventoryValue() {
        double total = 0;
        for (Product p : products) {
            total += p.getInventoryValue();
        }
        return total;
    }

    public void loadFromStorage() {
        products.clear();
        products.addAll(storage.load());
    }

    public void saveToStorage() {
        storage.save(products);
    }
}
