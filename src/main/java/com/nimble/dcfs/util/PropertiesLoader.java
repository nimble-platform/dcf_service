package com.nimble.dcfs.util;



import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    final private static Logger logger = Logger.getLogger(PropertiesLoader.class);
    final private static String propCfgFile = "Dcfs";

    public static Properties loadProperties() { 
        return loadProperties(propCfgFile);
    }
    public static Properties loadProperties(String clientConfig) {
        String file = String.format("%s.properties", clientConfig);
        
        
        InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(file);
        try {
            if (inputStream == null) {
                throw new FileNotFoundException("property file '" + file + "' not found in the classpath");
            }
            Properties prop = new Properties();
            prop.load(inputStream);

            return prop;
        } catch (Exception ex) {
            logger.info(String.format("Exception '%s' on loading properties file '%s'", ex.getMessage(), file));
            throw new RuntimeException("Unable to load properties");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
