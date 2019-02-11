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
package com.nimble.dcfs.admin.topic;


import com.nimble.dcfs.datachannel.AclManager;
import com.nimble.dcfs.datachannel.DataChannelManager;
import com.nimble.dcfs.db.Channel;
import com.nimble.dcfs.db.User;
import com.nimble.dcfs.admin.topic.*;
import java.util.Iterator;
import java.util.List;

import kafka.admin.AdminUtils;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 *
 * @author a.musumeci
 */
public class TopicManagerRest implements iTopicManager {
    
    private final static Logger logger = Logger.getLogger(TopicManagerRest.class);
    Properties propConfiguration;

    
    public void initTopicManager(Properties propConfiguration) {
        this.propConfiguration = propConfiguration;
    }

    public void createTopic(String topicName) {
        topicName = topicName.toUpperCase();
        try {
            String restResponse = KafkaAdminRestClient.createTopic(propConfiguration.getProperty("kafka_admin_url"), propConfiguration.getProperty("apiKey"), topicName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int recreateDcfsTopics() {
        int enabledTopics = 0;
            DataChannelManager  dataChannelManager  = new DataChannelManager ();
            AclManager aclManager = new AclManager();
            List<User> userList = aclManager.getProducerList( );
            Iterator<User> iU = userList.iterator();
            while (iU.hasNext()) {
                User producer = iU.next();
                String namespace = producer.getProducerNamespace().toUpperCase()+"_";
                List<Channel> channelList = dataChannelManager.getProducerChannel( producer.getId() );
                Iterator<Channel> iC = channelList.iterator();
                while (iC.hasNext()) {
                    Channel channel = iC.next();
                    String topicName = namespace+channel.getTopicname().toUpperCase();
                    createTopic(topicName);
                    enabledTopics++;
                }
            }

        return enabledTopics;
    }
    
    @Override
    public void close() {
    }

    
    
}
