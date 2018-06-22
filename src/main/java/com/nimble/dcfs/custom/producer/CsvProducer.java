/**
 *
 * @author a.musumeci
 */
package com.nimble.dcfs.custom.producer;

import com.nimble.dcfs.producer.DcfsProducer;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

import java.io.StringWriter;
import java.io.FileReader;
import java.util.Properties;




public class CsvProducer {

    String topicName;
    String keyField;
    DcfsProducer producer;
    
    public CsvProducer(DcfsProducer producer, Properties props, String topic, String keyField) {
        topicName = topic;
        this.keyField = keyField;
        this.producer = producer;
    }

    public int workCsv( String fileName) throws Exception {
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
