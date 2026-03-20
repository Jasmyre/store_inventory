package com.store_inventory.ai_protorype;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        StorageManager storage = new StorageManager("inventory.json");
        InventoryManager inventory = new InventoryManager(storage);
        inventory.loadFromStorage();

        SwingUtilities.invokeLater(() -> {
            MainMenuUI ui = new MainMenuUI(inventory);
            ui.show();
        });
    }
}
