/*
 * Copyright 2018 a.musumeci.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.nimble.dcfs.util;



import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    final private static Logger logger = Logger.getLogger(PropertiesLoader.class);
    final private static String propCfgFile = "Dcfs";
    final private static String propCfgFileKafkaDcfs = "KafkaDcfs";

    public static Properties loadKafkaDcfsProperties() { 
        return loadProperties(propCfgFileKafkaDcfs);
    }
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
