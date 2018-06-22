/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimble.dcfs.custom;

import com.nimble.dcfs.consumer.RestConsumer;
import com.nimble.dcfs.db.User;
import com.nimble.dcfs.datachannel.AclManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author a.musumeci
 */
public abstract class CustomRestService  {
    private RestConsumer restConsumer = new RestConsumer();
    private AclManager aclManager = new AclManager();
        
    public abstract String getLoginProducer();
    public abstract String getPasswordProducer();

    public final Integer getIdProducer() {
        User user =  aclManager.getProducer( getLoginProducer(), getPasswordProducer() );
        if ( user!=null ) {
            return user.getId();
        }
        else return new Integer( 0 );
    }
    
    
    public Response loginConsumer( String login,  String password) {
        return restConsumer.loginConsumer( login, password);
    }

    public Response getListAvaiableDataChannel( String login, String password) { 
        return restConsumer.getListAvaiableDataChannel(login, password, getIdProducer());
    }

    public Response getFilteredMessageForSubscription(String login, String password,  String streamName, String filtervalue) { 
        return restConsumer.getFilteredMessageForSubscription( login,  password,  getIdProducer(),  streamName, filtervalue);
    }
    

}
