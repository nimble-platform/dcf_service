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
 
package com.nimble.dcfs.filtering;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import javax.ws.rs.core.Application;
import java.util.Properties;
import java.util.List;
import com.nimble.dcfs.util.PropertiesLoader;


/**
 *
 * @author a.musumeci
 */

public class KsqlGateway extends Application {
    public int MAXROWRESULTSET = 1000;
    public long TIMEOUTQUERY = 10000;
    

    private final static Logger logger = Logger.getLogger(KsqlGateway.class);
    Properties props = PropertiesLoader.loadConsumerProperties();
    String ksqlClientid;
    
    public KsqlGateway() {
        ksqlClientid = props.getProperty("client.id")+"."+System.currentTimeMillis();
        props.replace("client.id", ksqlClientid);
        MAXROWRESULTSET = Integer.parseInt(props.getProperty("dcfs.maxrowresultset"));
        TIMEOUTQUERY = Long.parseLong(props.getProperty("dcfs.timeoutquery"));
    }
    
    public String getJsonResponse(String ksqlQuery) throws Exception {
        return getJsonResponse(true, ksqlQuery);
    }
    
    public String getJsonResponse(boolean isQuery, String ksqlQuery) throws Exception {
        String offset = props.getProperty("ksql.streams.auto.offset.reset");
        return getJsonResponse(isQuery, ksqlQuery, offset);
    }
    
    public String getJsonResponse(boolean isQuery, String ksqlQuery, String offset) throws Exception {
        String ksqlUrl = props.getProperty("ksqlUrl");
        if (isQuery) {
            ksqlUrl = ksqlUrl+"/query";
        } else {
            ksqlUrl = ksqlUrl+"/ksql";
        }
        
        
        
        ksqlQuery = formatQueryForRest(ksqlQuery);
        
        String requestBody = "{\"ksql\": \""+ksqlQuery+"\",  \"streamsProperties\": {";
                  if (isQuery) {
                        requestBody+= "\"ksql.streams.auto.offset.reset\": \""+offset+"\"";
                  }
        requestBody+= "}}";
        
        
        //GROUP_ID_CONFIG = "NimbleDcfsConsumer+" + System.currentTimeMillis()
        
        URL obj = new URL(ksqlUrl);

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream outputStream = connection.getOutputStream();

        outputStream.write(requestBody.getBytes());
        System.out.println("request "+requestBody );
        outputStream.flush();
        outputStream.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        List<String> resultList = new ArrayList<String>();
        int nrRows = 0;
        boolean atleastonerow=false;
        long init = System.currentTimeMillis();
        while ( true ) {
            if ((inputLine = in.readLine()) != null && !inputLine.isEmpty() ) {
                resultList.add(inputLine.trim());
                if (atleastonerow) nrRows++;
                atleastonerow = true;
            } else {
                if (atleastonerow) break;
            }
            if (MAXROWRESULTSET < nrRows) {
                System.out.println("Query max row managed "+MAXROWRESULTSET);
                break;
            }
            if ((System.currentTimeMillis()-init) > TIMEOUTQUERY) {
                System.out.println("Query timeout "+TIMEOUTQUERY);
                break;
            }
        }
        

        in.close();
        connection.disconnect();
        connection = null;
        
        StringBuffer json = new StringBuffer();
        json.append("{\"nimble_dcfs_result\" : [");
        for (int i = 0; i<resultList.size(); i++ ) {
            json.append(""+resultList.get(i));
            if (i<resultList.size()-1) {
                json.append(",");
            }
        }
        json.append("] }");
        
        return json.toString();

    }

    private String formatQueryForRest(String query) {
        query = query.replaceAll("'", "\'");
        return query;
    }
    
    public void close(){
    
    }
    
    
}
