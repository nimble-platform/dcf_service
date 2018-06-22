/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.datachannel;

import com.nimble.dcfs.custom.CustomInitializer;
import com.nimble.dcfs.custom.CustomInitializer;
import com.nimble.dcfs.datachannel.AclManager;
import com.nimble.dcfs.datachannel.DataChannelManager;
import com.nimble.dcfs.db.User;
import com.nimble.dcfs.util.PropertiesLoader;

import com.nimble.dcfs.producer.DcfsProducer;
import java.util.*;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 *
 * @author a.musumeci
 */
public class CustomInizializerManager {
    private Properties systemProps = PropertiesLoader.loadProperties();
    
    public int executeCustomInizializer() {

            AclManager aclManager = new AclManager();

            KafkaProducer<String, String> kProducer = new KafkaProducer<String, String>(systemProps);
            
            String customSystemInitializerName;
            int idxCs=0;
            while ( (customSystemInitializerName = (String) systemProps.get("dcfs.custom.CustomSystemInitializer"+idxCs)) != null) {
                try {
                    CustomInitializer customSystemInitializer = (CustomInitializer) Class.forName(customSystemInitializerName).newInstance();
                    User user =  aclManager.getProducer( customSystemInitializer.getLoginProducer(), customSystemInitializer.getPasswordProducer());
                    if ( user!=null ) {
                       
                        ArrayList<String> avaiableTopics = aclManager.getAvaiableTopics( user.getId() );
                        
                        DcfsProducer producer = new DcfsProducer(kProducer, avaiableTopics );
                           Thread threadCustomizer = new Thread(){
                                public void run(){
                                  System.out.println("Executing customSystemInitializer in new Thread");
                                  customSystemInitializer.afterStartTopic(producer);

                                }
                            };
                        threadCustomizer.start();
                    }
                } catch (Exception ex) {
                    System.out.println("FAILED "+customSystemInitializerName);
                    ex.printStackTrace();
                }

                idxCs++;
            }
    
            return idxCs;
    
    }
    
    
}
