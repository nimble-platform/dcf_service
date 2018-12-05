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

/**
 *
 * @author a.musumeci
 */

import com.nimble.dcfs.datachannel.StreamManager;
import com.nimble.dcfs.datachannel.AclManager;
import com.nimble.dcfs.datachannel.*;
import com.nimble.dcfs.db.*;
import com.nimble.dcfs.db.DcfsEntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

public class TestUtilityClass {
    
    public void testGetMetadataChannelList(Integer idProducer, Integer idConsumer, String streamName) {

        StreamManager streamManager = new StreamManager();

        ArrayList<MetadataRow> mapMetadata = streamManager.getStreamMetadata(idProducer, idConsumer, streamName).getStreamMetadata();
        
        Iterator<MetadataRow> i = mapMetadata.iterator();
        while (i.hasNext()) {
            MetadataRow mdr = i.next();
            System.out.println(  mdr.getField() + " " +  mdr.getValue());
        }
        
        
        
        

    }


    void testGetFieldFilterList(Integer idProducer, Integer idConsumer, String streamName) {
        StreamManager streamManager = new StreamManager();
        String fieldList = streamManager.getFieldList(idProducer, idConsumer, streamName);
        System.out.println(""+fieldList);
        String filterList = streamManager.getFilterList(idProducer, idConsumer, streamName);
        System.out.println(""+filterList);
    }

    void testLoginConsumer() {
        AclManager aclManager = new AclManager();
        User userConsumer = aclManager.getConsumer("CS", "pwd");
        userConsumer.getGroupConsumer();
    }

    public static void main (String argc[]) {
        TestUtilityClass testObj = new TestUtilityClass();
        //testObj.testGetFieldFilterList(new Integer(1), new Integer(7), "IT_WHIRPOOL_STREAMS_DC_PRODUCTDATA");
        //testObj.testGetMetadataChannelList(new Integer(1), new Integer(7), "IT_WHIRPOOL_STREAMS_DC_PRODUCTDATA");
        //testObj.testLoginConsumer();
    }
    
    
}
