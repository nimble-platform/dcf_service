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
import com.nimble.dcfs.filtering.KsqlGateway;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import javax.persistence.EntityManager;

/**
 *
 * @author a.musumeci
 */
public class StreamManager {
    
    DataChannelManager  dataChannelManager  = new DataChannelManager ();
    AclManager aclManager  = new AclManager();
    
    public MetadataChannel getStreamMetadata(Integer idProducer, Integer idConsumer, String streamName) {
        MetadataChannel metadataChannel = new MetadataChannel();
        ArrayList<MetadataRow> streamMetadata = new ArrayList();
        HashMap<String, MetadataRow> metadata = new HashMap();
        String dataChannelName = streamName.substring(streamName.lastIndexOf("_")+1);
        DataChannel dataChannel  = dataChannelManager.getDataChannel(idProducer, dataChannelName);

        Channel channel = dataChannelManager.getChannel( dataChannel.getIdChannel() );
        metadataChannel.setKey(channel.getKey());
        String fieldTypeList = channel.getFieldTypeList();
        String[] keyValuePairs = fieldTypeList.split(",");              //split the string to creat key-value pairs

        for(String pair : keyValuePairs) {
            String[] entry = pair.trim().split(" ");
            MetadataRow mdr = new MetadataRow(entry[0].trim(), entry[1].trim());
            metadata.put(entry[0].trim(), mdr);
            streamMetadata.add(mdr);
        }

        String fieldListDataChannel = dataChannel.getFieldList();
        String fieldListConsumer = this.getFieldList(idProducer, idConsumer, streamName);
        if (!"*".equals(fieldListConsumer)) {
            fieldListDataChannel = fieldListConsumer;
        }

        if (!"*".equalsIgnoreCase(dataChannel.getFieldList())) {
            ArrayList<MetadataRow> dataChannelStreamMetadata = new ArrayList();
            String[] fields = fieldListDataChannel.split(",");              //split the string to creat key-value pairs
            for(String field : fields) {
                dataChannelStreamMetadata.add (metadata.get(field.trim()));
            }
            streamMetadata = dataChannelStreamMetadata;
        }
        metadataChannel.setStreamMetadata(streamMetadata);
        
        return metadataChannel;
    }
    
    public ArrayList<String>  getAvaiableConsumerStream(Integer idProducer, Integer idConsumer) {
            EntityManager em = DcfsEntityManagerFactory.createEntityManager();
            ArrayList<String>  avaiableStreams = new ArrayList();

            List<FilteredSubscriptionChannel> filteredSubscriptionChannel = em.createNamedQuery("FilteredSubscriptionChannel.findByIdConsumer").setParameter("idConsumer", idConsumer).getResultList();
            Iterator<FilteredSubscriptionChannel> ifsc = filteredSubscriptionChannel.iterator();
            
            while (ifsc.hasNext()) {
                FilteredSubscriptionChannel fsc = ifsc.next();
                if (fsc.getIdGroup() <= 0 && fsc.getIdDataChannel()>0 ) {
                    DataChannel dataChannel = dataChannelManager.getDataChannel( fsc.getIdDataChannel() );
                    Channel channel = dataChannelManager.getChannel( dataChannel.getIdChannel() );
                    User producer = aclManager.getUser( channel.getIdProducer() );
                    if (producer.getId().equals(idProducer)) { //consumer could be subscribed to many producer
                         avaiableStreams.add(producer.getProducerNamespace().toUpperCase()+"_STREAMS_DC_"+dataChannel.getName().toUpperCase());
                     }
                 } else {
                    List<FilterGroupChannel> filterGroupChannels = em.createNamedQuery("FilterGroupChannel.findByIdGroupConsumer").setParameter("idGroupConsumer", fsc.getIdGroup()).getResultList();
                    Iterator<FilterGroupChannel> fgcs = filterGroupChannels.iterator();
                    while (fgcs.hasNext()) {
                        FilterGroupChannel filterGroupChannel = fgcs.next();
                        DataChannel dataChannel = dataChannelManager.getDataChannel( filterGroupChannel.getIdDataChannel() );
                        Channel channel = dataChannelManager.getChannel( dataChannel.getIdChannel() );
                        User producer = aclManager.getUser( channel.getIdProducer() );
                        if (producer.getId().equals(idProducer)) {
                            avaiableStreams.add(producer.getProducerNamespace().toUpperCase()+"_STREAMS_DC_"+dataChannel.getName().toUpperCase());
                        }
                    }
                
                }
            }

            //em.close();
            return avaiableStreams;
    }
        
        
    public int  executeDropStreamCommands() {
        ArrayList<String> dropStreams = getDropStreamCommands();
        executeStreamCommandList(dropStreams);
        return dropStreams.size();
    }
    
    public int  executeCreateStreamCommands() {
        ArrayList<String>  createStreams =  getCreateStreamCommands();
        executeStreamCommandList(createStreams);
        return createStreams.size();
    }
    
    
    private ArrayList<String> getDropStreamCommands() {
                    ArrayList<String> dropList = new ArrayList();
                    List<User> userList = aclManager.getProducerList( );
                    Iterator<User> iU = userList.iterator();
                    while (iU.hasNext()) {
                        User producer = iU.next();
                        String namespace = producer.getProducerNamespace().toUpperCase()+"_";
                        List<Channel> channelList = dataChannelManager.getProducerChannel( producer.getId() );
                        Iterator<Channel> iC = channelList.iterator();
                        while (iC.hasNext()) {
                            Channel channel = iC.next();
                            dropList.add("DROP STREAM "+namespace+"STREAMS_"+channel.getTopicname().toUpperCase()+";");
                            List<DataChannel> dataChannelList = dataChannelManager.getDataChannelList( channel.getId() );
                            Iterator<DataChannel> iDc = dataChannelList.iterator();
                            while (iDc.hasNext()) {
                                DataChannel dataChannel = iDc.next();
                                dropList.add("DROP STREAM "+namespace+"STREAMS_DC_"+dataChannel.getName().toUpperCase()+";");
                            }
                        }
                    }
                    
                    return dropList;
    
    }
        
        
    private ArrayList<String> getCreateStreamCommands(){
                    AclManager aclManager = new AclManager();
                    ArrayList<String> createList = new ArrayList();
                    List<User> userList = aclManager.getProducerList( );
                    Iterator<User> iU = userList.iterator();
                    while (iU.hasNext()) {
                        User producer = iU.next();
                        String namespace = producer.getProducerNamespace().toUpperCase()+"_";
                 List<Channel> channelList = dataChannelManager.getProducerChannel( producer.getId() );
                        Iterator<Channel> iC = channelList.iterator();
                        while (iC.hasNext()) {
                            Channel channel = iC.next();
                            DataFormat dataFormat = dataChannelManager.getDataFormat( channel.getIdDataFormat() );
                            String mainCreate = "CREATE STREAM "+namespace+"STREAMS_"+channel.getTopicname().toUpperCase()+"( "+channel.getFieldTypeList()+")  WITH (KAFKA_TOPIC='"+channel.getTopicname()+"', VALUE_FORMAT='"+dataFormat.getFormat().toUpperCase()+"'";
                            if (channel.getKey() != null && channel.getKey().length()>1) {
                                mainCreate+=", KEY = '"+channel.getKey().toUpperCase()+"'";
                            }
                            mainCreate+=");";
                            createList.add(mainCreate);
                            List<DataChannel> dataChannelList = dataChannelManager.getDataChannelList( channel.getId() );
                            Iterator<DataChannel> iDc = dataChannelList.iterator();
                            while (iDc.hasNext()) {
                                DataChannel dataChannel = iDc.next();
                                createList.add("CREATE STREAM "+namespace+"STREAMS_DC_"+dataChannel.getName().toUpperCase()+" AS SELECT "+dataChannel.getFieldList().toUpperCase()+" FROM "+namespace+"STREAMS_"+channel.getTopicname().toUpperCase()+";");
                            }
                        }
                    }
                    
                    return createList;
    
    }
    
    
    private void executeStreamCommandList (ArrayList<String> commandList) {
    
            KsqlGateway ksqlGateway = new KsqlGateway();
            for (int i=0; i< commandList.size(); i++ ) {
                try {
                   String jsonResponse = ksqlGateway.getJsonResponse(false, commandList.get(i));
                    if (jsonResponse==null || jsonResponse.indexOf("\\\"error\\\":")>1) {
                        System.out.println("WARNING "+jsonResponse);
                    } else {
                        System.out.println("SUCCESS "+jsonResponse);
                    }
                } catch (Exception ex) {
                    System.out.println("ERROR "+commandList.get(i));
                    ex.printStackTrace();
                }
                ksqlGateway.close();
                try {
                    System.out.println("wait for slow kafka");
                    Thread.sleep(100);
                } catch (Exception ex) {}
            }

    }

    
        
    public boolean canReadConsumerFrom(Integer idProducer, Integer idConsumer, String streamName) {
        return getAvaiableConsumerStream(idProducer, idConsumer).contains(streamName);
    }

    public String getFilterList(Integer idProducer, Integer idConsumer, String streamName) {
                    EntityManager em = DcfsEntityManagerFactory.createEntityManager();

        String filterList = "";
        streamName = streamName.substring(streamName.lastIndexOf("_")+1, streamName.length());
        DataChannel dataChannel = dataChannelManager.getDataChannel(idProducer, streamName);

            List<FilteredSubscriptionChannel> filteredSubscriptionChannel = em.createNamedQuery("FilteredSubscriptionChannel.findByIdDataChannelAndIdConsumer").setParameter("idDataChannel", dataChannel.getId()).setParameter("idConsumer", idConsumer).getResultList();
            Iterator<FilteredSubscriptionChannel> ifsc = filteredSubscriptionChannel.iterator();
            
            while (ifsc.hasNext()) {
                FilteredSubscriptionChannel fsc = ifsc.next();
                if ( fsc.getIdGroup() <= 0 ) {
                    filterList = fsc.getFilterValue();
                    if (filterList == null) {
                        filterList="";
                    }
                    break;
                }
            }

            filteredSubscriptionChannel = em.createNamedQuery("FilteredSubscriptionChannel.findByIdConsumer").setParameter("idConsumer", idConsumer).getResultList();
            ifsc = filteredSubscriptionChannel.iterator();

             while (ifsc.hasNext()) {
                 FilteredSubscriptionChannel fsc = ifsc.next();
                 if (fsc.getIdGroup() > 0 ) {
                     List<FilterGroupChannel> filterGroupChannels = em.createNamedQuery("FilterGroupChannel.findByIdGroupConsumerAndIdDataChannel").setParameter("idDataChannel", dataChannel.getId()).setParameter("idGroupConsumer", fsc.getIdGroup()).getResultList();
                     Iterator<FilterGroupChannel> fgcs = filterGroupChannels.iterator();
                     while (fgcs.hasNext()) {
                         FilterGroupChannel filterGroupChannel = fgcs.next();
                         if ( filterList.length()> 0 ) {
                             filterList += " AND " + filterGroupChannel.getFilterValue();
                         } else {
                            filterList = filterGroupChannel.getFilterValue();
                         }
                         break;
                     }

                 }
             }
        //em.close();
        return filterList;
    }
    
    public String getFieldList(Integer idProducer, Integer idConsumer, String streamName) {
         EntityManager em = DcfsEntityManagerFactory.createEntityManager();
        String fieldList = "";
        streamName = streamName.substring(streamName.lastIndexOf("_")+1, streamName.length());
        DataChannel dataChannel = dataChannelManager.getDataChannel(idProducer, streamName);

            List<FilteredSubscriptionChannel> filteredSubscriptionChannel = em.createNamedQuery("FilteredSubscriptionChannel.findByIdDataChannelAndIdConsumer").setParameter("idDataChannel", dataChannel.getId()).setParameter("idConsumer", idConsumer).getResultList();
            Iterator<FilteredSubscriptionChannel> ifsc = filteredSubscriptionChannel.iterator();
            
            while (ifsc.hasNext()) {
                FilteredSubscriptionChannel fsc = ifsc.next();
                if (fsc.getIdGroup() <= 0 && fsc.getIdDataChannel()>0) {
                    fieldList = fsc.getFieldList();
                    break;
                }
            }

            if (fieldList.length()==0) {
                    filteredSubscriptionChannel = em.createNamedQuery("FilteredSubscriptionChannel.findByIdConsumer").setParameter("idConsumer", idConsumer).getResultList();
                    ifsc = filteredSubscriptionChannel.iterator();

                     while (ifsc.hasNext()) {
                         FilteredSubscriptionChannel fsc = ifsc.next();
                         if (fsc.getIdGroup() > 0 ) {
                             List<FilterGroupChannel> filterGroupChannels = em.createNamedQuery("FilterGroupChannel.findByIdGroupConsumerAndIdDataChannel").setParameter("idDataChannel", dataChannel.getId()).setParameter("idGroupConsumer", fsc.getIdGroup()).getResultList();
                             Iterator<FilterGroupChannel> fgcs = filterGroupChannels.iterator();
                             while (fgcs.hasNext()) {
                                 FilterGroupChannel filterGroupChannel = fgcs.next();
                                 fieldList = filterGroupChannel.getFieldList();
                                 break;
                             }

                         }
                     }
            }


            if (fieldList.length()==0) {
                fieldList="*";
            }
            //em.close();
            return fieldList;
    }
    
    
    
      public boolean isJsonStream(Integer idProducer, String streamName) {
        String dataChannelName = streamName.substring(streamName.lastIndexOf("_")+1);
        DataChannel dataChannel  = dataChannelManager.getDataChannel(idProducer, dataChannelName);
        Channel channel = dataChannelManager.getChannel( dataChannel.getIdChannel() );
        return channel.getIdDataFormat() == 2;
      }
        
}