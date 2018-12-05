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

import com.nimble.dcfs.admin.topic.TopicManager;
import com.nimble.dcfs.util.PropertiesLoader;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author a.musumeci
 */
public class TopicManagerTest {
    
    public TopicManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of recreateDcfsTopics method, of class TopicManager.
     */
    @Test
    public void testRecreateDcfsTopics() {
        System.out.println("recreateDcfsTopics");
        /*Properties systemProps = PropertiesLoader.loadProperties();
        TopicManager instance = new TopicManager();
        instance.initTopicManager(systemProps);
        int expResult = 5;
        int result = instance.recreateDcfsTopics();
        assertEquals(expResult, result);*/
    }

    
}
