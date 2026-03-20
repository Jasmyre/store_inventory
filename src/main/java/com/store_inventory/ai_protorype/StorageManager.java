package com.store_inventory.ai_protorype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private final String filePath;
    private final ObjectMapper mapper;

    public StorageManager(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path is required.");
        }
        this.filePath = filePath;
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void save(List<Product> products) {
        Path path = Paths.get(filePath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            mapper.writeValue(path.toFile(), products);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save inventory.", e);
        }
    }

    public List<Product> load() {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(path.toFile(), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load inventory.", e);
        }
    }
}
