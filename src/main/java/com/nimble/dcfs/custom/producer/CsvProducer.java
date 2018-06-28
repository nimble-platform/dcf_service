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
 
package com.nimble.dcfs.custom.producer;

import com.nimble.dcfs.producer.DcfsProducer;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

import java.io.StringWriter;
import java.io.FileReader;
import java.util.Properties;

/**
 * Example utility message Producer
 
 */
class CsvProducer {

    String topicName;
    String keyField;
    DcfsProducer producer;
    
    /**
     *
     * @param producer
     * @param props
     * @param topic
     * @param keyField
     */
    CsvProducer(DcfsProducer producer, Properties props, String topic, String keyField) {
        topicName = topic;
        this.keyField = keyField;
        this.producer = producer;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    int workCsv( String fileName) throws Exception {
        int lineCount = 0;
        
        try {
            FileReader fr = new FileReader(fileName);

            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(fr);
            for (CSVRecord record : records) {
                StringWriter recordStringWriter = new StringWriter();
                CSVPrinter csvMessagePrinter = new CSVPrinter(recordStringWriter, CSVFormat.RFC4180);
                String key = record.get(keyField);
                csvMessagePrinter.printRecord(record);
                sendMessage(key, recordStringWriter.toString() );
                lineCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineCount;
    }
  
    private void sendMessage(String key, String value) throws Exception {
            producer.sendMessage(topicName, key, value);
    }

}
