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
 
package com.nimble.dcfs.datachannel;

import com.nimble.dcfs.db.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author a.musumeci
 */
public class DataChannelManager {

    
        public List<Channel>  getProducerChannel(Integer idProducer) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<Channel> channels = em.createNamedQuery("Channel.findByIdProducer").setParameter("idProducer", idProducer).getResultList();
            //em.close();
            return channels;
        }

        public List<DataChannel>  getDataChannelList(Integer idChannel) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<DataChannel> dataChannels = em.createNamedQuery("DataChannel.findByIdChannel").setParameter("idChannel", idChannel).getResultList();
            //em.close();
            return dataChannels;
        }

        public DataChannel  getDataChannel(Integer idDataChannel) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<DataChannel> dataChannels = em.createNamedQuery("DataChannel.findById").setParameter("id", idDataChannel).getResultList();
            //em.close();
            if (dataChannels.size()>0)
            return dataChannels.get(0);
            else return null;
        }

        public DataChannel  getDataChannel(Integer idProducer, String dataChannelName) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<DataChannel> dataChannels = em.createNamedQuery("DataChannel.findByIdProducerAndDataChannelName").setParameter("idProducer", idProducer).setParameter("dataChannelName", dataChannelName).getResultList();
            //em.close();
            if (dataChannels.size()>0)
            return dataChannels.get(0);
            else return null;
        }


        public Channel getChannel(Integer idChannel) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<Channel> channels = em.createNamedQuery("Channel.findById").setParameter("id", idChannel).getResultList();
            //em.close();
            if (channels.size()>0)
            return channels.get(0);
            else return null;
        }

        public List<Channel>  getChannelList() {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<Channel> channels = em.createNamedQuery("Channel.findAll").getResultList();
            //em.close();
            return channels;
        }

        public DataFormat  getDataFormat(Integer idDataFormat) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            List<DataFormat> dataFormats = em.createNamedQuery("DataFormat.findById").setParameter("id", idDataFormat).getResultList();
            //em.close();
            if (dataFormats.size()>0)
            return dataFormats.get(0);
            else return null;
        }

    /**
     *
     * @param userIdProducer
     * @param namespace
     * @return
     */
    public ArrayList<String> getAvaiableTopics( Integer userIdProducer, String namespace ) {
            ArrayList<String> avaiableTopics = new ArrayList();
            List<Channel> channels = getProducerChannel(userIdProducer);
            Iterator<Channel> i = channels.iterator();
            while (i.hasNext()) {
                avaiableTopics.add(
                   namespace+"_"+i.next().getTopicname() //namespace.toUpperCase()+"_"+i.next().getTopicname()
                );
            }
            return avaiableTopics;
    }
        
    
}
