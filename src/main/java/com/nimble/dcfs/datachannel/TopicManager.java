/**
 *
 * @author a.musumeci
 */
package com.nimble.dcfs.datachannel;


import com.nimble.dcfs.db.Channel;
import com.nimble.dcfs.db.DataChannel;
import com.nimble.dcfs.db.User;
import com.nimble.dcfs.util.PropertiesLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author a.musumeci
 */
public class TopicManager implements AutoCloseable {
    
    private final static Logger logger = Logger.getLogger(TopicManager.class);
    Properties propConfiguration;

    int partitions;
    int replications;
    
    ZkClient zkClient;
    ZkUtils zkUtils;
    
    public TopicManager (Properties propConfiguration) {
        this.propConfiguration = propConfiguration;

        String connectionString = propConfiguration.getProperty("zookeeper.connect");
        int sessionTimeoutMs = Integer.parseInt(  propConfiguration.getProperty("zookeeper.sessionTimeoutMs") );
        int connectionTimeout = Integer.parseInt(  propConfiguration.getProperty("zookeeper.connectionTimeout") );

        partitions = Integer.parseInt(  propConfiguration.getProperty("zookeeper.partitions") );
        replications = Integer.parseInt(  propConfiguration.getProperty("zookeeper.replications") );

        zkClient = new ZkClient(connectionString, sessionTimeoutMs, connectionTimeout, ZKStringSerializer$.MODULE$);
        zkUtils = new ZkUtils(zkClient, new ZkConnection(connectionString), false);

    }
    
    private void createTopic(String topicName) {
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
        };
    }
    
    private void deleteTopic(String topicName) {
        topicName = topicName.toUpperCase();
        if (AdminUtils.topicExists(zkUtils, topicName)) {
                AdminUtils.deleteTopic(zkUtils, topicName);
        }
    }
    
    private boolean existTopic(String topicName) {
        topicName = topicName.toUpperCase();
        return (AdminUtils.topicExists(zkUtils, topicName));
    }
    
    private void recreateTopic(String topicName) {
        topicName = topicName.toUpperCase();
        deleteTopic(topicName);
        createTopic(topicName);
    }
    
    public void close() {
        zkUtils.close();
        zkClient.close();
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
                   if (!existTopic(topicName)) {
                       recreateTopic(topicName);
                    }
                    enabledTopics++;
                }
            }

        return enabledTopics;
    }
    
    
    
}
