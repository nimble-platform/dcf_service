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
 
package com.nimble.dcfs.custom.service;

import com.nimble.dcfs.consumer.RestConsumer;
import com.nimble.dcfs.db.User;
import javax.ws.rs.core.Response;

/**
 * Class Base in order to extends Rest Services to serve a single Nimble Producer Scenario
 * @author a.musumeci
 */
public abstract class CustomRestService  {
    private RestConsumer restConsumer = new RestConsumer();
        
    /**
     * 
     * @return
     */
    public abstract String getLoginProducer();

    /**
     * To be declared in order to let know to System which data producer you want to consume
     * @return
     */
    public abstract String getPasswordProducer();

    /**
     * To be declared in order to let know to System which data producer you want to consume
     * @return
     */
    public final Integer getIdProducer() {
        User user =  restConsumer.getProducer( getLoginProducer(), getPasswordProducer() );
        if ( user!=null ) {
            return user.getId();
        }
        else return new Integer( -1 );
    }
    
    /**
     *
     * @param login
     * @param password
     * @return
     */
    public Response loginConsumer( String login,  String password) {
        return restConsumer.loginConsumer( login, password);
    }

    /**
     *
     * @param login
     * @param password
     * @return the list of all avaiable message channel for this Consumer
     */
    public Response getListAvaiableDataChannel( String login, String password) { 
        return restConsumer.getListAvaiableDataChannel(login, password, getIdProducer());
    }

    /**
     *
     * @param login
     * @param password
     * @param streamName
     * @param filtervalue
     * @return all avaiable messages for this Consumer applying filter; for filter sintax take a look to https://docs.confluent.io/current/ksql/docs/syntax-reference.html#select
     */
    public Response getFilteredMessageForSubscription(String login, String password,  String streamName, String filtervalue) { 
        return restConsumer.getFilteredMessageForSubscription( login,  password,  getIdProducer(),  streamName, filtervalue);
    }
    

}
