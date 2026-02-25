package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private Properties properties;

    public PropertiesReader(String fileName) throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new IOException("Fichero de propiedades '" + fileName + "' no encontrado en el classpath");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}