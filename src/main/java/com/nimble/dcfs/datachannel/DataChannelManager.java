/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.datachannel;

import com.nimble.dcfs.db.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author a.musumeci
 */
public class DataChannelManager {

    
        public List<Channel>  getProducerChannel(Integer idProducer) {
            List<Channel> channels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("Channel.findByIdProducer").setParameter("idProducer", idProducer).getResultList();
            return channels;
        }

        public List<DataChannel>  getDataChannelList(Integer idChannel) {
            List<DataChannel> dataChannels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("DataChannel.findByIdChannel").setParameter("idChannel", idChannel).getResultList();
            return dataChannels;
        }

        public DataChannel  getDataChannel(Integer idDataChannel) {
            List<DataChannel> dataChannels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("DataChannel.findById").setParameter("id", idDataChannel).getResultList();
            if (dataChannels.size()>0)
            return dataChannels.get(0);
            else return null;
        }

        public DataChannel  getDataChannel(Integer idProducer, String dataChannelName) {
            List<DataChannel> dataChannels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("DataChannel.findByIdProducerAndDataChannelName").setParameter("idProducer", idProducer).setParameter("dataChannelName", dataChannelName).getResultList();
            if (dataChannels.size()>0)
            return dataChannels.get(0);
            else return null;
        }


        public Channel getChannel(Integer idChannel) {
            List<Channel> channels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("Channel.findById").setParameter("id", idChannel).getResultList();
            if (channels.size()>0)
            return channels.get(0);
            else return null;
        }

        public List<Channel>  getChannelList() {
            List<Channel> channels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("Channel.findAll").getResultList();
            return channels;
        }

        public DataFormat  getDataFormat(Integer idDataFormat) {
            List<DataFormat> dataFormats = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("DataFormat.findById").setParameter("id", idDataFormat).getResultList();
            if (dataFormats.size()>0)
            return dataFormats.get(0);
            else return null;
        }

        
    
}
