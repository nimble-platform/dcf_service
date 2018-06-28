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
public class DcfsProducer implements AutoCloseable {
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


    public void close() {
        kafkaProducer.close();
    }

}
