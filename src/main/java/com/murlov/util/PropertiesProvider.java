package com.murlov.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {
    private final static String PATH = "simulation.properties";
    private final Properties properties;

    public PropertiesProvider(){
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from " + PATH);
        }
    }

    public String getString(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing property: " + key);
        }
        return value;
    }

    public int getInt(String key) {
        try {
            return Integer.parseInt(getString(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer for key: " + key + ", value: " + getString(key));
        }
    }

    public double getDouble(String key) {
        try {
            return Double.parseDouble(getString(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double for key: " + key + ", value: " + getString(key));
        }
    }
}
