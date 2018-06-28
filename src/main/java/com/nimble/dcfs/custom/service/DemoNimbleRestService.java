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

import com.nimble.dcfs.util.PropertiesLoader;
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Example of customized consumer
 * @author a.musumeci
 */
@Path("/demonimble")
public class DemoNimbleRestService extends CustomRestService {
    
    Properties propsWhirpool = PropertiesLoader.loadProperties( "DemoNimbleDcfs" );

    /**
     *
     */
    public DemoNimbleRestService() {
        super();
    }
    
    public String getLoginProducer() {
        return (String) propsWhirpool.get("loginProducer");
    }
    
    public String getPasswordProducer(){
        return (String) propsWhirpool.get("passwordProducer");
    }
    
    @GET
    @Path("/listAvaiableDataChannel/{login}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListAvaiableDataChannel(@PathParam("login") String login, @PathParam("password") String password) { 
        return super.getListAvaiableDataChannel(login, password);
    }

    /**
     *
     * @param login
     * @param password
     * @param serialnumber
     * @return
     */
    @GET
    @Path("/productionData/{login}/{password}/{serialnumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductionData(@PathParam("login") String login, @PathParam("password") String password, @PathParam("serialnumber") String serialnumber) { 
        return super.getFilteredMessageForSubscription( login,  password,  "IT_NIMBLE_DCFSDEMO_STREAMS_DC_PRODUCTIONDATA", "serialnumber='"+serialnumber+"'");
    }

    /**
     *
     * @param login
     * @param password
     * @param serialnumber
     * @return
     */
    @GET
    @Path("/qualityData/{login}/{password}/{serialnumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQualityData(@PathParam("login") String login, @PathParam("password") String password, @PathParam("serialnumber") String serialnumber) { 
        return super.getFilteredMessageForSubscription( login,  password,  "IT_NIMBLE_DCFSDEMO_STREAMS_DC_QUALITYDATA", "serialnumber='"+serialnumber+"'");
    }

    /**
     *
     * @param login
     * @param password
     * @param serialnumber
     * @return
     */
    @GET
    @Path("/issueData/{login}/{password}/{serialnumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssueData(@PathParam("login") String login, @PathParam("password") String password, @PathParam("serialnumber") String serialnumber) { 
        return super.getFilteredMessageForSubscription( login,  password,  "IT_NIMBLE_DCFSDEMO_STREAMS_DC_ISSUEDATA", "serialnumber='"+serialnumber+"'");
    }

    /**
     *
     * @param login
     * @param password
     * @param serialnumber
     * @return
     */
    @GET
    @Path("/interventData/{login}/{password}/{serialnumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInterventData(@PathParam("login") String login, @PathParam("password") String password, @PathParam("serialnumber") String serialnumber) { 
        return super.getFilteredMessageForSubscription( login,  password,  "IT_NIMBLE_DCFSDEMO_STREAMS_DC_INTERVENTDATA", "serialnumber='"+serialnumber+"'");
    }

    /**
     *
     * @param login
     * @param password
     * @param serialnumber
     * @return
     */
    @GET
    @Path("/productData/{login}/{password}/{serialnumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductData(@PathParam("login") String login, @PathParam("password") String password, @PathParam("serialnumber") String serialnumber) { 
        return super.getFilteredMessageForSubscription( login,  password,  "IT_NIMBLE_DCFSDEMO_STREAMS_DC_PRODUCTDATA", "serialnumber='"+serialnumber+"'");
    }

}
