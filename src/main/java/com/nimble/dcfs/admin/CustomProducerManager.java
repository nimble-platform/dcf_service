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
 
package com.nimble.dcfs.admin;

import com.nimble.dcfs.datachannel.AclManager;
import com.nimble.dcfs.datachannel.DataChannelManager;
import com.nimble.dcfs.db.User;
import com.nimble.dcfs.producer.CustomProducer;
import com.nimble.dcfs.util.PropertiesLoader;

import com.nimble.dcfs.producer.DcfsProducer;
import java.util.*;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 *
 * @author a.musumeci
 */
class CustomProducerManager {
    private Properties systemProps = PropertiesLoader.loadProperties();
    
    int executeCustomProducer() {

            AclManager aclManager = new AclManager();
            DataChannelManager dataChannelManager = new DataChannelManager();

            String customSystemInitializerName;
            int idxCs=0;
            while ( (customSystemInitializerName = (String) systemProps.get("dcfs.custom.CustomProducer"+idxCs)) != null) {
                try {
                    CustomProducer customSystemInitializer = (CustomProducer) Class.forName(customSystemInitializerName).newInstance();
                    User user =  aclManager.getProducer( customSystemInitializer.getLoginProducer(), customSystemInitializer.getPasswordProducer());
                    if ( user!=null && user.getIsProducer()==1) {
                       
                        ArrayList<String> avaiableTopics = dataChannelManager.getAvaiableTopics( user.getId(), user.getProducerNamespace() );
                        
                        KafkaProducer<String, String> kProducer = new KafkaProducer<String, String>(systemProps);
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
