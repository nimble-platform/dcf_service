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

import com.nimble.dcfs.DcfsMainRest;
import com.nimble.dcfs.datachannel.StreamManager;
import com.nimble.dcfs.util.PropertiesLoader;

import java.util.*;
import org.apache.log4j.Logger;
/**
 * Initializer of DCFS
 * @author a.musumeci
 */
class DcfsInitializer {
    private final static Logger logger = Logger.getLogger(DcfsMainRest.class);
    private Properties systemProps = PropertiesLoader.loadProperties();

    private int enabledTopics;
    private int enabledStreams;

    /**
     * @return the enabledTopics
     */
    int getEnabledTopics() {
        return enabledTopics;
    }

    /**
     * @return the enabledStreams
     */
    int getEnabledStreams() {
        return enabledStreams;
    }
 
    /**
     *In order this initialize the DCFS 
     * - if there are new topics to be created
     * - if there are new streams to be created
     * - launch in a separate Thread every Custom Data Producer listed in system property file as dcfs.custom.CustomSystemInitializer[0..n]
     */
    void restartDcfs() {

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

            boolean autostartCustomProducer = Boolean.parseBoolean(systemProps.getProperty("dcfs.custom.autostartCustomProducer"));
            
            if (autostartCustomProducer) {
                runCustomProducers();
            }
            
            System.out.println("DataChannel Filtering System initialized successfully");
            logger.debug("DataChannel Filtering System initialized successfully");
        }

    /**
     * launch in a separate Thread every Custom Data Producer listed in system property file as dcfs.custom.CustomSystemInitializer[0..n]
     */
    void runCustomProducers() {
            CustomProducerManager customProducerManager = new CustomProducerManager();
            customProducerManager.executeCustomProducer();
        }
        
}
