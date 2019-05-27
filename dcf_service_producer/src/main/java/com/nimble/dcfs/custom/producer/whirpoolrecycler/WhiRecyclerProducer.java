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
 
package com.nimble.dcfs.custom.producer.whirpoolrecycler;

import com.nimble.dcfs.custom.producer.demo.*;
import com.google.gson.Gson;
import com.nimble.dcfs.producer.CustomProducer;
import com.nimble.dcfs.util.PropertiesLoader;
import com.nimble.dcfs.producer.DcfsProducer;
import java.util.ArrayList;

import java.util.Properties;

/**
 *  Example message producer over file Csv; this can be extended in order to read from database, from twitter topics, to listen remote events and to write them and so on
 * @author a.musumeci
 */
public class WhiRecyclerProducer implements CustomProducer {
    Properties propsDemo = PropertiesLoader.loadProperties( "WhiRecyclerProducer" );

    /**
     *
     */
    public WhiRecyclerProducer() {
        super();
    }
    
    public String getLoginProducer() {
        return (String) propsDemo.get("loginProducer");
    }
    
    public String getPasswordProducer(){
        return (String) propsDemo.get("passwordProducer");
    }
    
    public boolean afterStartTopic(Properties producerProps){
        DcfsProducer producer = new DcfsProducer( producerProps );

        System.out.println("Start WhiRecyclerProducer");
        int iSent;

        int idxDc=0;
        String channelName = "IT_WHIRPOOL_RECYCLERDATABAL";

        for ( int i=0; i<10; i++ ) {
            try {
                
                       RecyclerData recyclerData = new RecyclerData();

                        String productGroup = "DishWasher";
                        String productType= "Whirlpool WFO 3T123 PF"+"-"+i;
                        String serialNumber= "SN1547896314567"+"-"+i;
                        String photo="https://static2.unieuro.it/medias/sys_master/root/h72/h8d/11050882793502/-img-gallery-32875934-9127627193.jpg";
                        String description="lavastoviglie Whirlpool: color inox. Classe energetica A++, per consumi ridotti di energia elettrica. Un utile timer digitale che segnala la fine del ciclo di lavaggio. Tecnologia innovativa che garantisce un funzionamento super silezioso, per un elettrodomestico senza rumori. Eccellente capacità di pulizia per risultati di lavaggio ideali.";

                        recyclerData.setProductGroup(productGroup);
                        recyclerData.setProductType(productType);
                        recyclerData.setSerialNumber(serialNumber);
                        recyclerData.setPhoto(photo);
                        recyclerData.setDescription(description);

                        recyclerData.setProcessDuration ( new LccData("Hours","0,3") );
                        recyclerData.setProcessCosts(  new LccData("€","15") );
                        recyclerData.setTransportCost ( new LccData("€","10") );
                        recyclerData.setEnergyConsumption( new LccData("kWh/kg","0,08") );
                        recyclerData.setDisposalCost( new LccData("€/kg","3") );
                        recyclerData.setQuantityIron ( new LccData("Kg","21,991") );
                        recyclerData.setQuantityPlastic(new LccData("Kg","14,186") );
                        recyclerData.setQuantityCopper ( new LccData("Kg","1,098") );
                        recyclerData.setQuantitySilver( new LccData("Kg","0,035") );
                        recyclerData.setQuantityGold( new LccData("Kg","0,0001") );
                        recyclerData.setQuantityPalladium ( new LccData("Kg","0,0099") );
                        recyclerData.setQuantityInox( new LccData("Kg","2,875") );
                        recyclerData.setQuantityAluminium( new LccData("Kg","1,053") );
                        recyclerData.setQuantityConcreteDisposed( new LccData("Kg","6,935") );
                        recyclerData.setQuantityWasteDisposed ( new LccData("Kg","2,355") );

                        
                    System.out.println("producing "+recyclerData);
                    //producer.sendJsonMessage(channelName, System.currentTimeMillis()+"", new Gson().toJson(recyclerData));
                    
                    LCCInputData inputData = new LCCInputData();
                    inputData.setSerialNumberProduct(serialNumber);
                    inputData.setJsonProduct( new Gson().toJson(recyclerData) );
                    producer.sendGenericMessage(channelName, serialNumber, new Gson().toJson(inputData) );
                    
                    Thread.sleep(500*(long) Math.random());
            } catch (Exception ex) {
                System.out.println("ERROR "+ex.getMessage()+"");
                ex.printStackTrace();
            }
            idxDc++;
        }
        producer.close();
        System.out.println("End WhiRecyclerProducer");
        return true;
    }
    
    
}
