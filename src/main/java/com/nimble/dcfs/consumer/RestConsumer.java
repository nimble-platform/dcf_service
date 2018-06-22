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
import com.nimble.dcfs.db.DataChannel;

import com.nimble.dcfs.filtering.KsqlGateway;

/**
 *
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


    @GET
    @Path("/dataChannelMetadata/{login}/{password}/{idProducer}/{streamName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataChannelMetadata(@PathParam("login") String login, @PathParam("password") String password, @PathParam("idProducer") Integer idProducer, @PathParam("streamName") String streamName ) { 
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer == null) {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
            
            String dataChannelName = streamName.substring(streamName.indexOf("_DC_")+4);
            DataChannel dataChannel  = dataChannelManager.getDataChannel(idProducer, dataChannelName);
            
            String jsonResponse = new Gson().toJson( streamManager.getStreamMetadata( dataChannel.getId() ) );    
            return createResponse(Response.Status.OK, jsonResponse);
    }



    @GET
    @Path("/listAvaiableDataChannel/{login}/{password}/{idProducer}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListAvaiableDataChannel(@PathParam("login") String login, @PathParam("password") String password, @PathParam("idProducer") Integer idProducer) { 
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer == null) {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
            
            String jsonResponse = new Gson().toJson( streamManager.getAvaiableConsumerStream(idProducer, userConsumer.getId()) );    
            return createResponse(Response.Status.OK, jsonResponse);
    }
    
    @GET
    @Path("/loginConsumer/{login}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginConsumer(@PathParam("login") String login, @PathParam("password") String password) {
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer != null) {
                return createResponse(Response.Status.OK, new Gson().toJson(userConsumer)) ;
            } else {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
    }

    
    @GET
    @Path("/filterChannel/{login}/{password}/{idProducer}/{streamName}/{filtervalue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredMessageForSubscription(@PathParam("login") String login, @PathParam("password") String password,  @PathParam("idProducer") Integer idProducer, @PathParam("streamName") String streamName, @PathParam("filtervalue") String filtervalue) { 
        try {
            User userConsumer = aclManager.getConsumer( login, password);
            if (userConsumer == null) {
                return createResponse(Response.Status.FORBIDDEN, new Gson().toJson("User unknown") );
            }
            
            streamName = streamName.toUpperCase();
            if (streamManager.canReadConsumerFrom(idProducer, userConsumer.getId(), streamName)) {    
                logger.info("Retrieving data for value - " + filtervalue);
                String ksqlQuery ="select * from "+streamName+" where "+filtervalue+";";
                //TODO $$ Next version will consider group filter and subscription filter
                
                
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
