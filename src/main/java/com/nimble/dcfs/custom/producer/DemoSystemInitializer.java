/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.custom.producer;

import com.nimble.dcfs.custom.CustomInitializer;
import com.nimble.dcfs.util.PropertiesLoader;
import com.nimble.dcfs.producer.DcfsProducer;

import java.util.Properties;

/**
 *
 * @author a.musumeci
 */
public class DemoSystemInitializer extends CustomInitializer {
    Properties propsDemo = PropertiesLoader.loadProperties( "DemoProducerNimbleDcfs" );

    
    public DemoSystemInitializer() {
        super();
    }
    
    public String getLoginProducer() {
        return (String) propsDemo.get("loginProducer");
    }
    
    public String getPasswordProducer(){
        return (String) propsDemo.get("passwordProducer");
    }
    
    public boolean afterStartTopic(DcfsProducer producer){
        System.out.println("Start DemoNimbleDcfs SystemInitializer");
        CsvProducer cvsProducer;
        int iSent;

        int idxDc=0;
        String channelName, channelKey, csvName;
        String csvPath = (String) propsDemo.get("dataCsvPath");
        while ( (channelName = (String) propsDemo.get("ChannelName"+idxDc)) != null) {
            try {
                channelKey = (String) propsDemo.get("ChannelKey"+idxDc);
                csvName = (String) propsDemo.get("csvName"+idxDc);
                cvsProducer = new CsvProducer(producer, propsDemo, channelName, channelKey );
                iSent = cvsProducer.workCsv(csvPath+""+csvName);
                System.out.println("Sent "+iSent+" messages of "+channelName);
            } catch (Exception ex) {
                System.out.println("ERROR "+ex.getMessage()+"");
                ex.printStackTrace();
            }
            idxDc++;
        }
        System.out.println("End DemoNimbleDcfsSystemInitializer");
        return true;
    }
    
    
}
