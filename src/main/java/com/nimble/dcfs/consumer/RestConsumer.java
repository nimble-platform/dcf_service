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
 
package com.nimble.dcfs.consumer;

import org.apache.log4j.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Application;
import com.google.gson.Gson;
import com.nimble.dcfs.datachannel.DataChannelManager;
import com.nimble.dcfs.datachannel.StreamManager;
import com.nimble.dcfs.datachannel.AclManager;
import com.nimble.dcfs.db.User;

import com.nimble.dcfs.filtering.KsqlGateway;

/**
 * Base service in order to login consumer, to have list of datachannel avaiable and metadata, to filter data on a channel
 * @author a.musumeci
 */

@Path("/consumer")
public class RestConsumer extends Application {
    private final static Logger logger = Logger.getLogger(RestConsumer.class);
    private KsqlGateway ksqlGateway = new KsqlGateway();
    private StreamManager  streamManager  = new StreamManager ();
    private DataChannelManager  dataChannelManager  = new DataChannelManager();
    private AclManager aclManager = new AclManager();
    
    private Response createResponse(Response.Status statusCode, String entity) {
        return Response.status(statusCode).entity(entity).build();
    }

    /**
     *
     * @param loginProducer
     * @param passwordProducer
     * @return
     */
    public final User getProducer( String loginProducer, String passwordProducer) {
        User user =  aclManager.getProducer( loginProducer, passwordProducer );
        return user;
    }

    /**
     *
     * @param login Consumer login
     * @param password Consumer password
     * @param idProducer id of the Subscribed Producer
     * @param streamName one of the avaiable data stream for this user
     * @return
     */
    @GET
    @Path("/dataChannelMetadata/{login}/{password}/{idProducer}/{streamName}")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getDataChannelMetadata(@PathParam("login") String login, @PathParam("password") String password, @PathParam("idProducer") Integer idProducer, @PathParam("streamName") String streamName ) { 
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer == null) {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
            
            String jsonResponse = new Gson().toJson( streamManager.getStreamMetadata( idProducer, userConsumer.getId(), streamName   ) );
            return createResponse(Response.Status.OK, jsonResponse);
    }

    /**
     *
     * @param login Consumer login
     * @param password Consumer password
     * @param idProducer user id of the data producer
     * @return list of all avaiable data stream
     */
    @GET
    @Path("/listAvaiableDataChannel/{login}/{password}/{idProducer}")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getListAvaiableDataChannel(@PathParam("login") String login, @PathParam("password") String password, @PathParam("idProducer") Integer idProducer) { 
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer == null) {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
            
            String jsonResponse = new Gson().toJson( streamManager.getAvaiableConsumerStream(idProducer, userConsumer.getId()) );    
            return createResponse(Response.Status.OK, jsonResponse);
    }
    
    /**
     *
     * @param login
     * @param password
     * @return user logged or a FORBIDDEN https Status response
     */
    @GET
    @Path("/loginConsumer/{login}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response loginConsumer(@PathParam("login") String login, @PathParam("password") String password) {
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer != null) {
                return createResponse(Response.Status.OK, new Gson().toJson(userConsumer)) ;
            } else {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
    }

    /**
     *
     * @param login Consumer login
     * @param password Consumer password
     * @param idProducer user id of the data producer
     * @param streamName one of the avaiable data stream for this user
     * @param filtervalue where condition; take a look to https://docs.confluent.io/current/ksql/docs/syntax-reference.html#select
     * @return
     */
    @GET
    @Path("/filterChannel/{login}/{password}/{idProducer}/{streamName}/{filtervalue}")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getFilteredMessageForSubscription(@PathParam("login") String login, @PathParam("password") String password,  @PathParam("idProducer") Integer idProducer, @PathParam("streamName") String streamName, @PathParam("filtervalue") String filtervalue) { 
        try {
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer == null) {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
            
            streamName = streamName.toUpperCase();
            if (streamManager.canReadConsumerFrom(idProducer, userConsumer.getId(), streamName)) {    
                logger.info("Retrieving data for value - " + filtervalue);
                String fieldList = streamManager.getFieldList(idProducer, userConsumer.getId(), streamName);
                String filterConditions=streamManager.getFilterList(idProducer, userConsumer.getId(), streamName);
                
                
                //not necessary to control sql injection because the readonly of the layer
                String ksqlQuery ="select ";
                
                if (fieldList!=null && !"".equals(fieldList) && !"null".equalsIgnoreCase(fieldList)  ) {
                    ksqlQuery += fieldList;
                } else {
                    ksqlQuery += " * ";
                }
                
                ksqlQuery += " from "+streamName;
                if ( (filterConditions!=null && !"".equals(filterConditions) && !"null".equalsIgnoreCase(filterConditions)) || (filtervalue!=null && !"".equals(filtervalue) && !"null".equalsIgnoreCase(filtervalue) ) ) {
                    ksqlQuery +=" where ";
                    if (filterConditions!=null && !"".equals(filterConditions) && !"null".equalsIgnoreCase(filterConditions) ) {
                        ksqlQuery += filterConditions+" AND ";
                    } 
                    if (filtervalue!=null && !"".equals(filtervalue) && !"null".equalsIgnoreCase(filtervalue) ) {
                        ksqlQuery += " ( "+filtervalue+" )";
                    } 
                    
                    
                } 
                
                ksqlQuery +=";";

                logger.info("ksqlQuery executing = "+ksqlQuery);

                String jsonResponse = ksqlGateway.getJsonResponse(ksqlQuery);
                return createResponse(Response.Status.OK, jsonResponse);
            } else throw new Exception ("you have not grant to read from this Stream");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to read channels for target - " + filtervalue, e);
            return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("Failed to get data for Stream - " + streamName +" ("+e.getMessage()+")") );
        }
    }

}
