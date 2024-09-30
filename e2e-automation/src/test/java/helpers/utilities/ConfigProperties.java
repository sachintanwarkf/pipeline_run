package helpers.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    private Properties properties = new Properties();
    public Properties getProperties() {
        String filePath=System.getProperty("user.dir")+"/src/test/resources/testDataResources/config.properties";
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception as needed
        }
        return properties;
    }

    public String getProperty(String key) {
        return getProperties().getProperty(key);
    }
}