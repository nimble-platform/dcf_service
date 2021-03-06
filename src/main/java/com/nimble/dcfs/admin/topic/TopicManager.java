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
import java.util.Iterator;
import java.util.List;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 *
 * @author a.musumeci
 */
public class TopicManager implements iTopicManager {
    
    private final static Logger logger = Logger.getLogger(TopicManager.class);
    Properties propConfiguration;
    int partitions;
    int replications;
    ZkClient zkClient;
    ZkUtils zkUtils;

    
    public void initTopicManager(Properties propConfiguration) {
        this.propConfiguration = propConfiguration;
        partitions = Integer.parseInt(  propConfiguration.getProperty("zookeeper.partitions") );
        replications = Integer.parseInt(  propConfiguration.getProperty("zookeeper.replications") );
        initZkUtils();
    }

    private void initZkUtils() {
        if (zkUtils == null) {
            String connectionString = propConfiguration.getProperty("zookeeper.connect");
            int sessionTimeoutMs = Integer.parseInt(  propConfiguration.getProperty("zookeeper.sessionTimeoutMs") );
            int connectionTimeout = Integer.parseInt(  propConfiguration.getProperty("zookeeper.connectionTimeout") );

            zkClient = new ZkClient(connectionString, sessionTimeoutMs, connectionTimeout, ZKStringSerializer$.MODULE$);
            zkUtils = new ZkUtils(zkClient, new ZkConnection(connectionString), false);
        
        }

    }
            
    
    public void createTopic(String topicName) {
        topicName = topicName.toUpperCase();
        if (!AdminUtils.topicExists(zkUtils, topicName)) {
            try {
                AdminUtils.createTopic(zkUtils, topicName, partitions, replications, new Properties(), null);
                logger.trace(String.format("Topic '%s' created", topicName));
                System.out.println(String.format("Topic '%s' created", topicName));
            } catch (Exception e) {
                logger.error(String.format("Exception on creating topic '%s' ", topicName), e);
            }  
        } else {
          logger.trace(String.format("Topic '%s' yet exists", topicName));
        }
        
    }
    
    private void deleteTopic(String topicName) {
        topicName = topicName.toUpperCase();
        if (AdminUtils.topicExists(zkUtils, topicName)) {
                AdminUtils.deleteTopic(zkUtils, topicName);
        }
    }
    
    private boolean existTopic(String topicName) {
        topicName = topicName.toUpperCase();
        boolean exist = (AdminUtils.topicExists(zkUtils, topicName));
        return exist;
    }
    
    private void recreateTopic(String topicName) {
        topicName = topicName.toUpperCase();
        deleteTopic(topicName);
        createTopic(topicName);
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
                    String topicName = channel.getTopicname();
                   if (!existTopic(topicName)) {
                       recreateTopic(topicName);
                    }
                    enabledTopics++;
                }
            }

        return enabledTopics;
    }
    
    @Override
    public void close() {
        System.out.println("Topic Manager closing..."); 
        zkUtils.close();
        zkClient.close();
        System.out.println("Topic Manager closed..."); 
    }

    
    
}
