/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.datachannel;
import com.nimble.dcfs.db.*;
import com.nimble.dcfs.filtering.KsqlGateway;


import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Iterator;

/**
 *
 * @author a.musumeci
 */
public class StreamManager {
    
    DataChannelManager  dataChannelManager  = new DataChannelManager ();
    AclManager aclManager  = new AclManager();
    
    public LinkedHashMap  getStreamMetadata(Integer idDataChannel) {
        LinkedHashMap streamMetadata = new LinkedHashMap();
        DataChannel dataChannel = dataChannelManager.getDataChannel( idDataChannel );

        Channel channel = dataChannelManager.getChannel( dataChannel.getIdChannel() );
        String fieldTypeList = channel.getFieldTypeList();
        String[] keyValuePairs = fieldTypeList.split(",");              //split the string to creat key-value pairs

        for(String pair : keyValuePairs) {
            String[] entry = pair.trim().split(" ");                   
            streamMetadata.put(entry[0].trim(), entry[1].trim());
        }

        String fieldListDataChannel = dataChannel.getFieldList();
        if (!"*".equalsIgnoreCase(dataChannel.getFieldList())) {
            LinkedHashMap dataChannelStreamMetadata = new LinkedHashMap();
            String[] fields = fieldListDataChannel.split(",");              //split the string to creat key-value pairs
            for(String field : fields) {
                dataChannelStreamMetadata.put(field.trim(), streamMetadata.get(field));
            }
            streamMetadata = dataChannelStreamMetadata;
        }
        //TODO $$ fare il metadata anche sui fieldlist dei gruppi e del singolo utente in futuro
        return streamMetadata;
    }
    
    public ArrayList<String>  getAvaiableConsumerStream(Integer idProducer, Integer idConsumer) {
            ArrayList<String>  avaiableStreams = new ArrayList();

            List<FilteredSubscriptionChannel> filteredSubscriptionChannel = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("FilteredSubscriptionChannel.findByIdConsumer").setParameter("idConsumer", idConsumer).getResultList();
            Iterator<FilteredSubscriptionChannel> ifsc = filteredSubscriptionChannel.iterator();
            
            while (ifsc.hasNext()) {
                FilteredSubscriptionChannel fsc = ifsc.next();
                if (fsc.getIdGroup() <= 0 && fsc.getIdDataChannel()>0 ) {
                    DataChannel dataChannel = dataChannelManager.getDataChannel( fsc.getIdDataChannel() );
                    Channel channel = dataChannelManager.getChannel( dataChannel.getIdChannel() );
                    User producer = aclManager.getUser( channel.getIdProducer() );
                    if (producer.getId().equals(idProducer)) {
                         avaiableStreams.add(producer.getProducerNamespace().toUpperCase()+"_STREAMS_DC_"+dataChannel.getName().toUpperCase());
                     }
                 } else {
                    List<FilterGroupChannel> filterGroupChannels = DcfsEntityManagerFactory.createEntityManager().createNamedQuery("FilterGroupChannel.findByIdGroupConsumer").setParameter("idGroupConsumer", fsc.getIdGroup()).getResultList();
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
                  AclManager aclManager = new AclManager();
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
                            createList.add("CREATE STREAM "+namespace+"STREAMS_"+channel.getTopicname().toUpperCase()+"( "+channel.getFieldTypeList()+")  WITH (KAFKA_TOPIC='"+namespace+channel.getTopicname().toUpperCase()+"', VALUE_FORMAT='"+dataFormat.getFormat().toUpperCase()+"', KEY = '"+channel.getKey().toUpperCase()+"');");
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
    
    
    public void executeStreamCommandList (ArrayList<String> commandList) {
    
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

            }

    }

    
        
    public boolean canReadConsumerFrom(Integer idProducer, Integer idConsumer, String streamName) {
        return getAvaiableConsumerStream(idProducer, idConsumer).contains(streamName);
    }


        
}
