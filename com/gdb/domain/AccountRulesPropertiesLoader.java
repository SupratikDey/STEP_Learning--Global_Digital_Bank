package com.gdb.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class AccountRulesPropertiesLoader {
    // Folders searched (relative to the working directory) before falling back to the classpath.
    private static final String[] RULES_DIRECTORIES = {
            "src/main/resources/config/rules",  // running from the project root
            "main/resources/config/rules"       // running from inside the src folder
    };
    private static final String CLASSPATH_DIRECTORY = "/config/rules/";

    private final Properties properties = new Properties();
    private final String source;

    public AccountRulesPropertiesLoader(String fileName) {
        this.source = load(fileName);
        System.out.println("[Config] Loaded rules from " + source);
    }

    public String getSource() {
        return source;
    }

    public String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : value.trim();
    }

    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String load(String fileName) {
        // 1. Try the file system
        for (String directory : RULES_DIRECTORIES) {
            String path = directory + "/" + fileName;
            if (Files.isRegularFile(Paths.get(path))) {
                try (InputStream in = Files.newInputStream(Paths.get(path))) {
                    readInto(in);
                    return path;
                } catch (IOException e) {
                    throw new IllegalStateException("Could not read rules file: " + path, e);
                }
            }
        }

        // 2. Fall back to the classpath
        String resource = CLASSPATH_DIRECTORY + fileName;
        try (InputStream in = AccountRulesPropertiesLoader.class.getResourceAsStream(resource)) {
            if (in != null) {
                readInto(in);
                return "classpath:" + resource;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read rules file: " + resource, e);
        }

        throw new IllegalStateException("Rules file not found: " + fileName
                + " (looked in " + String.join(", ", RULES_DIRECTORIES) + " and on the classpath)");
    }

    private void readInto(InputStream in) throws IOException {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
    }
}
