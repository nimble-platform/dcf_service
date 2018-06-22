/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs;

import com.nimble.dcfs.datachannel.CustomInizializerManager;
import com.nimble.dcfs.datachannel.TopicManager;
import com.nimble.dcfs.datachannel.StreamManager;
import com.nimble.dcfs.util.PropertiesLoader;

import java.util.*;
import org.apache.log4j.Logger;
/**
 *
 * @author a.musumeci
 */
public class DcfsInitializer {
    private final static Logger logger = Logger.getLogger(DcfsMainRest.class);
    private Properties systemProps = PropertiesLoader.loadProperties();

    private int enabledTopics;
    private int enabledStreams;

    /**
     * @return the enabledTopics
     */
    public int getEnabledTopics() {
        return enabledTopics;
    }

    /**
     * @return the enabledStreams
     */
    public int getEnabledStreams() {
        return enabledStreams;
    }
 
    

        public void restartDcfs() {

            boolean recreateTopics = Boolean.parseBoolean(systemProps.getProperty("dcfs.topics.recreate"));
            if (recreateTopics) {
                TopicManager topicManager = new TopicManager(systemProps);
                this.enabledTopics =  topicManager.recreateDcfsTopics();
            }
            

            StreamManager streamManager = new StreamManager();
            boolean recreateStreams = Boolean.parseBoolean(systemProps.getProperty("dcfs.streams.recreate"));
            if (recreateStreams) {
                streamManager.executeDropStreamCommands();
            }
            enabledStreams = streamManager.executeCreateStreamCommands();

            boolean autostartCustomInitializer = Boolean.parseBoolean(systemProps.getProperty("dcfs.custom.autostartCustomInitializer"));
            
            if (autostartCustomInitializer) {
                runCustomInitializer();
            }
            
            System.out.println("DataChannel Filtering System initialized successfully");
            logger.debug("DataChannel Filtering System initialized successfully");
        }

        
        public void runCustomInitializer() {
            CustomInizializerManager customInizializerManager = new CustomInizializerManager();
            customInizializerManager.executeCustomInizializer();
        }
        
}
