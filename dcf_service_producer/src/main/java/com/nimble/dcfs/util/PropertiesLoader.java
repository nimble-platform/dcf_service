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
import javax.json.*;
import com.google.gson.Gson;

public class PropertiesLoader {
    final private static Logger logger = Logger.getLogger(PropertiesLoader.class);
    final private static String propCfgFile = "Dcfs";
    final private static String propCfgFileKafkaDcfs = "KafkaDcfs";

    public static Properties loadConsumerProperties() { 
        Properties consumerProps = new Properties();
                String consumerPropertiesfile = loadProperties().getProperty("dcfs.topic.consumer.propertiesfile");
                if ( consumerPropertiesfile!= null && !"".equalsIgnoreCase(consumerPropertiesfile) ) {
                        consumerProps = PropertiesLoader.loadProperties(consumerPropertiesfile);
                }
        return consumerProps;
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
            
            prop = envVarFill(prop);
            
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
    
    private static Properties envVarFill(Properties prop){
            boolean useSsl = Boolean.parseBoolean(prop.getProperty("dcfs.kafkaUseSSL"));
            if (useSsl) {
                String jsonEnvVariableName = prop.getProperty("dcfs.jsonEnvVariableName");
                if (jsonEnvVariableName != null && !"".equalsIgnoreCase(jsonEnvVariableName)) {
                    String json=System.getenv(jsonEnvVariableName);
                    JsonObject jsonObject = (new Gson()).fromJson( json, JsonObject.class);
                    String kafka_admin_url=jsonObject.getString("kafka_admin_url");
                    prop.setProperty("kafka_admin_url", kafka_admin_url);
                    
                    String kafka_brokers_sasl=jsonObject.getString("kafka_brokers_sasl");
                    JsonArray arrBrokers=jsonObject.getJsonArray("kafka_brokers_sasl");
                    String sbrokers = "";

                    for(int i=0;i<arrBrokers.size();i++) sbrokers += arrBrokers.getString(i)+",";
                    prop.setProperty("kafka_brokers_sasl", sbrokers);

                    
                    String user=jsonObject.getString("user");
                    String password=jsonObject.getString("password");

                    String sasl_jaas_config = prop.getProperty("sasl_jaas_config");
                    sasl_jaas_config = sasl_jaas_config.replaceAll("\\$user", user);
                    sasl_jaas_config = sasl_jaas_config.replaceAll("\\$password", password);
                    prop.setProperty("sasl.jaas.config", sasl_jaas_config);                    
                    
                    
                
                
                } else {
                    String kafka_admin_url=System.getenv("kafka_admin_url");
                    prop.setProperty("adminRestURL", kafka_admin_url);
                    
                    String kafka_brokers_sasl=System.getenv("kafka_brokers_sasl");
                    prop.setProperty("bootstrap.servers", kafka_brokers_sasl);

                    String user=System.getenv("user");
                    String password=System.getenv("password");

                    String sasl_jaas_config = prop.getProperty("sasl_jaas_config");
                    sasl_jaas_config = sasl_jaas_config.replaceAll("\\$user", user);
                    sasl_jaas_config = sasl_jaas_config.replaceAll("\\$password", password);
                    prop.setProperty("sasl.jaas.config", sasl_jaas_config);
                }

                System.out.println("overwriting envVariables in prop file");

            }
        return prop;
    }
    
    
}
