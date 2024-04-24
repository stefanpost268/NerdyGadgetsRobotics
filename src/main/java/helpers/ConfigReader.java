package helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static String getConfig(String attribute) {

        String propertiesFilename = "config.properties";
        Properties prop = new Properties();
        try (var inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(propertiesFilename)) {
            if (inputStream == null) {
                throw new FileNotFoundException(propertiesFilename);
            }
            prop.load(inputStream);

            //giving indication if a attribute does not exist
            if (prop.getProperty(attribute) == null) {
                System.out.println("could not find a attribute with the name: " + attribute);
            }

            return prop.getProperty(attribute);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read " + propertiesFilename + " resource file: " + e);
        }

    }
}

