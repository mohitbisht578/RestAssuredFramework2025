package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
        private static Properties properties;

        static {
            try (InputStream input = new FileInputStream("./src/test/java/resources/config.properties")) {
                properties = new Properties();
                properties.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        public static String get(String key) {
            return properties.getProperty(key);
        }
    }
