package com.nimble.dcfs.filtering;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.core.Application;
import com.google.gson.Gson;
import com.nimble.dcfs.datachannel.DataChannelManager;
import com.nimble.dcfs.custom.producer.CsvProducer;
import com.nimble.dcfs.util.PropertiesLoader;
import java.util.Properties;


/**
 *
 * @author a.musumeci
 */

public class KsqlGateway extends Application {
    public static String OFFSET_EARLIEST = "earliest";
    public static String OFFSET_LATEST = "latest";

    private final static Logger logger = Logger.getLogger(KsqlGateway.class);
    Properties props = PropertiesLoader.loadProperties();
    String ksqlClientid;
    
    public KsqlGateway() {
        ksqlClientid = props.getProperty("client.id")+"."+System.currentTimeMillis();
        props.replace("client.id", ksqlClientid);
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
        
        String requestBody = "{\"ksql\": \""+ksqlQuery+"\",  \"streamsProperties\": {\"ksql.streams.auto.offset.reset\": \""+offset+"\"}}";
        
        //GROUP_ID_CONFIG = "NimbleDcfsConsumer+" + System.currentTimeMillis()
        
        URL obj = new URL(ksqlUrl);

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream outputStream = connection.getOutputStream(); // <-- I get an exception.

        outputStream.write(requestBody.getBytes());
        outputStream.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        List<String> resultList = new ArrayList<String>();

        StringBuffer response = new StringBuffer();

        while ( (inputLine = in.readLine()) != null && !inputLine.isEmpty()  ) {
            resultList.add(inputLine.trim());
        }

        in.close();
        String json = new Gson().toJson( resultList );     
        return json;

    }

    private String formatQueryForRest(String query) {
        query = query.replaceAll("'", "\'");
        //query = query.replaceAll("\"", "\\\"");
        return query;
    }
    
    public void close(){
    
    }
    
    
}
