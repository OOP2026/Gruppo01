package util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("File config.properties non trovato in src/main/resources");
            }
            props.load(input);
        } catch (Exception ex) {
            throw new RuntimeException("Errore nel caricamento del file di configurazione", ex);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}