/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 *
 * @author a.musumeci
 */
public class DcfsProducer {
    ArrayList<String> avaiableTopics;
    KafkaProducer kafkaProducer;
    
    public DcfsProducer(KafkaProducer kafkaProducer, ArrayList<String> avaiableTopics) {
        this.kafkaProducer = kafkaProducer;
        this.avaiableTopics = avaiableTopics;
    }
    
    private boolean canWriteIn(String topicName) {
        return avaiableTopics.contains(topicName);
    }
    
    public void sendMessage(String topicName, String key, Object value) throws Exception {
        if (canWriteIn(topicName)) {
            Future<RecordMetadata> data = kafkaProducer.send(new ProducerRecord<>(topicName, key, value));
        } else throw new Exception("No grants to write in this topic");
    }
    
    
}
